package b.attack.simulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class LoggerUtil {
    private static final DateTimeFormatter LOG_TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ConcurrentHashMap<String, HostWriter> HOST_WRITERS = new ConcurrentHashMap<>();
    private static final AtomicBoolean CLOSED = new AtomicBoolean(false);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(LoggerUtil::closeAll, "logger-util-shutdown"));
    }

    private LoggerUtil() {
    }

    public static void logToFile(String host, String message) {
        if (CLOSED.get()) {
            return;
        }

        String filename = "log_" + host.replace(".", "_") + ".txt";
        HostWriter hostWriter = HOST_WRITERS.computeIfAbsent(filename, LoggerUtil::createWriter);
        if (hostWriter == null) {
            return;
        }

        String timestamp = LocalDateTime.now().format(LOG_TIMESTAMP_FORMAT);
        hostWriter.lock.lock();
        try {
            hostWriter.writer.write("[" + timestamp + "] " + message);
            hostWriter.writer.newLine();
            hostWriter.writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao gravar log: " + e.getMessage());
        } finally {
            hostWriter.lock.unlock();
        }
    }

    public static void closeAll() {
        if (!CLOSED.compareAndSet(false, true)) {
            return;
        }

        for (Map.Entry<String, HostWriter> entry : HOST_WRITERS.entrySet()) {
            HostWriter hostWriter = entry.getValue();
            hostWriter.lock.lock();
            try {
                hostWriter.writer.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar log " + entry.getKey() + ": " + e.getMessage());
            } finally {
                hostWriter.lock.unlock();
            }
        }

        HOST_WRITERS.clear();
    }

    private static HostWriter createWriter(String filename) {
        try {
            return new HostWriter(new BufferedWriter(new FileWriter(filename, true)));
        } catch (IOException e) {
            System.err.println("Erro ao abrir arquivo de log " + filename + ": " + e.getMessage());
            return null;
        }
    }

    private static final class HostWriter {
        private final BufferedWriter writer;
        private final ReentrantLock lock = new ReentrantLock();

        private HostWriter(BufferedWriter writer) {
            this.writer = writer;
        }
    }
}

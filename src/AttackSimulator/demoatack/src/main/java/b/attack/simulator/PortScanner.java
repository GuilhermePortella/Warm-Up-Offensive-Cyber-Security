package b.attack.simulator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortScanner {

    private static final Set<Integer> COMMON_SYSTEM_PORTS = Set.of(20, 21, 22, 23, 25, 53, 67, 68, 69, 80, 110, 111,
            123, 135, 137, 138, 139, 143, 161, 162,
            389, 443, 445, 465, 514, 587, 631, 636, 993, 995, 1433, 1521, 3306, 3389, 5432, 5900);

    private static final Set<Integer> COMMON_APP_PORTS = Set.of(3000, 3001, 3030, 4200, 5000, 5173, 8000, 8080, 8181,
            8888);

    public enum Priority {
        HIGH,
        MEDIUM,
        LOW
    }

    public record PortFinding(int port, Priority priority, String reason) {
    }

    public static List<Integer> scanOpenPorts(
            String host, int startPort, int endPort, int timeoutMs, int threads) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        List<Integer> openPorts = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(endPort - startPort + 1);

        for (int p = startPort; p <= endPort; p++) {
            final int port = p;
            pool.submit(() -> {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(host, port), timeoutMs);
                    openPorts.add(port);
                } catch (IOException ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        pool.shutdown();
        openPorts.sort(Integer::compareTo);
        return openPorts;
    }

    public static List<Integer> scanAllTcpPorts(String host, int timeoutMs, int threads) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        List<Integer> openPorts = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(65535);

        for (int port = 1; port <= 65535; port++) {
            final int p = port;
            pool.submit(() -> {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(host, p), timeoutMs);
                    openPorts.add(p);
                } catch (IOException ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        pool.shutdown();
        openPorts.sort(Integer::compareTo);
        return openPorts;
    }

    public static List<PortFinding> rankPorts(List<Integer> openPorts, Set<Integer> preferredPorts) {
        Set<Integer> preferred = preferredPorts == null ? Set.of() : preferredPorts;
        List<PortFinding> ranked = new ArrayList<>();

        for (int port : openPorts) {
            if (preferred.contains(port)) {
                ranked.add(new PortFinding(port, Priority.HIGH, "Porta preferida da aplicacao"));
                continue;
            }
            if (COMMON_APP_PORTS.contains(port) && !COMMON_SYSTEM_PORTS.contains(port)) {
                ranked.add(new PortFinding(port, Priority.HIGH, "Porta comum de aplicacao web"));
                continue;
            }
            if (port < 1024 || COMMON_SYSTEM_PORTS.contains(port)) {
                ranked.add(new PortFinding(port, Priority.LOW, "Porta comum de sistema/servico conhecido"));
                continue;
            }
            ranked.add(new PortFinding(port, Priority.MEDIUM, "Porta aberta nao classificada (potencial app)"));
        }

        ranked.sort(Comparator
                .comparingInt((PortFinding finding) -> priorityWeight(finding.priority()))
                .thenComparingInt(PortFinding::port));
        return ranked;
    }

    public static void writeCsvReport(String host, List<PortFinding> rankedPorts, Path outputPath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("host,rank,port,priority,potential,reason");

        int rank = 1;
        for (PortFinding finding : rankedPorts) {
            String potential = finding.priority() == Priority.LOW ? "NO" : "YES";
            String reason = finding.reason().replace(",", ";");
            lines.add(host + "," + rank + "," + finding.port() + "," + finding.priority() + "," + potential + ","
                    + reason);
            rank++;
        }

        if (outputPath.getParent() != null) {
            Files.createDirectories(outputPath.getParent());
        }
        Files.write(outputPath, lines, StandardCharsets.UTF_8);
    }

    public static Set<Integer> parsePreferredPorts(String csvPorts) {
        if (csvPorts == null || csvPorts.isBlank()) {
            return Set.of(3030);
        }

        Set<Integer> ports = new HashSet<>();
        String[] split = csvPorts.split(",");
        for (String value : split) {
            String trimmed = value.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            try {
                int port = Integer.parseInt(trimmed);
                if (port >= 1 && port <= 65535) {
                    ports.add(port);
                }
            } catch (NumberFormatException ignored) {
                // ignora valores invalidos
            }
        }

        if (ports.isEmpty()) {
            ports.add(3030);
        }
        return ports;
    }

    private static int priorityWeight(Priority priority) {
        return switch (priority) {
            case HIGH -> 0;
            case MEDIUM -> 1;
            case LOW -> 2;
        };
    }

    public static void main(String[] args) throws InterruptedException {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int timeoutMs = args.length > 1 ? Integer.parseInt(args[1]) : 120;
        int threads = args.length > 2 ? Integer.parseInt(args[2]) : 300;
        String outputFile = args.length > 3 ? args[3] : "port-scan-report.csv";
        Set<Integer> preferredPorts = parsePreferredPorts(args.length > 4 ? args[4] : "3030");

        long start = System.currentTimeMillis();
        List<Integer> open = scanAllTcpPorts(host, timeoutMs, threads);
        List<PortFinding> ranked = rankPorts(open, preferredPorts);
        long elapsed = System.currentTimeMillis() - start;

        try {
            writeCsvReport(host, ranked, Path.of(outputFile));
        } catch (IOException e) {
            System.out.println("Falha ao gravar relatorio: " + e.getMessage());
        }

        System.out.println("Portas TCP abertas: " + open.size());
        System.out.println("Top portas potenciais:");
        int shown = 0;
        for (PortFinding finding : ranked) {
            if (finding.priority() == Priority.LOW) {
                continue;
            }
            System.out.println(" - " + finding.port() + " [" + finding.priority() + "] " + finding.reason());
            shown++;
            if (shown == 10) {
                break;
            }
        }
        System.out.println("Arquivo gerado: " + Path.of(outputFile).toAbsolutePath());
        System.out.println("Tempo: " + elapsed + " ms");
    }
}

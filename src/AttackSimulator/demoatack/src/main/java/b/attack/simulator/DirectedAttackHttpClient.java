package b.attack.simulator;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;

public class DirectedAttackHttpClient {
    private static final String TARGET_URL = "http://localhost:8080";
    private static final int THREAD_COUNT = 200;

    private static final AtomicInteger postCount = new AtomicInteger(0);
    private static final AtomicInteger deleteCount = new AtomicInteger(0);
    private static final AtomicInteger getCount = new AtomicInteger(0);

    public static void main(String[] args) {
        AtomicBoolean running = new AtomicBoolean(true);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> shutdownExecutor(executor, running),
                "directed-http-shutdown"));

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(THREAD_COUNT * 2);
        connManager.setDefaultMaxPerRoute(THREAD_COUNT);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(2000))
                .setResponseTimeout(Timeout.ofMilliseconds(2000))
                .build();

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build()) {

            int postThreads = THREAD_COUNT / 2;
            int deleteThreads = THREAD_COUNT / 4;
            int getThreads = THREAD_COUNT - postThreads - deleteThreads;

            submitWorkers(executor, postThreads, running, () -> {
                sendPostTransaction(httpClient);
                int count = postCount.incrementAndGet();
                if (count % 100 == 0) {
                    System.out.println("POST /transactions enviados: " + count);
                }
            }, 5, "POST");

            submitWorkers(executor, deleteThreads, running, () -> {
                sendDeleteTransactions(httpClient);
                int count = deleteCount.incrementAndGet();
                if (count % 50 == 0) {
                    System.out.println("DELETE /transactions enviados: " + count);
                }
            }, 50, "DELETE");

            submitWorkers(executor, getThreads, running, () -> {
                sendGetStatistics(httpClient);
                int count = getCount.incrementAndGet();
                if (count % 100 == 0) {
                    System.out.println("GET /statistics enviados: " + count);
                }
            }, 20, "GET");

            executor.shutdown();
            while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                if (!running.get()) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.err.println("Erro ao fechar HttpClient: " + e.getMessage());
        } finally {
            shutdownExecutor(executor, running);
            connManager.close();
        }
    }

    private static void submitWorkers(
            ExecutorService executor,
            int workerCount,
            AtomicBoolean running,
            ThrowingTask task,
            long pauseMillis,
            String operation) {

        for (int i = 0; i < workerCount; i++) {
            executor.submit(() -> runWorkerLoop(running, task, pauseMillis, operation));
        }
    }

    private static void runWorkerLoop(AtomicBoolean running, ThrowingTask task, long pauseMillis, String operation) {
        while (running.get() && !Thread.currentThread().isInterrupted()) {
            try {
                task.run();
                if (pauseMillis > 0) {
                    TimeUnit.MILLISECONDS.sleep(pauseMillis);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.out.println("Erro " + operation + ": " + e.getMessage());
            }
        }
    }

    private static void shutdownExecutor(ExecutorService executor, AtomicBoolean running) {
        if (!running.compareAndSet(true, false)) {
            return;
        }

        executor.shutdownNow();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("Nem todas as tasks HTTP finalizaram apos o timeout.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void sendPostTransaction(CloseableHttpClient httpClient) throws Exception {
        HttpPost post = new HttpPost(TARGET_URL + "/transactions");
        double valor = ThreadLocalRandom.current().nextDouble(10.0, 1000.0);
        String dataHora = "2025-06-03T10:15:30Z";
        String json = String.format(Locale.US, "{\"valor\": %.2f, \"dataHora\": \"%s\"}", valor, dataHora);
        post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        try (var response = httpClient.execute(post)) {
            int status = response.getCode();
            if (status != 201) {
                System.out.println("POST falhou com codigo: " + status);
            }
        }
    }

    private static void sendDeleteTransactions(CloseableHttpClient httpClient) throws Exception {
        HttpDelete delete = new HttpDelete(TARGET_URL + "/transactions");
        try (var response = httpClient.execute(delete)) {
            int status = response.getCode();
            if (status != 204) {
                System.out.println("DELETE falhou com codigo: " + status);
            }
        }
    }

    private static void sendGetStatistics(CloseableHttpClient httpClient) throws Exception {
        HttpGet get = new HttpGet(TARGET_URL + "/statistics");
        try (var response = httpClient.execute(get)) {
            int status = response.getCode();
            if (status != 200) {
                System.out.println("GET falhou com codigo: " + status);
            }
        }
    }

    @FunctionalInterface
    private interface ThrowingTask {
        void run() throws Exception;
    }
}

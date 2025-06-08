package b.attack.simulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.Random;

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
    private static final Random random = new Random();

    public static void main(String[] args) {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(THREAD_COUNT * 2);
        connManager.setDefaultMaxPerRoute(THREAD_COUNT);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(2000))
                .setResponseTimeout(Timeout.ofMilliseconds(2000))
                .build();


        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        int postThreads = THREAD_COUNT / 2;
        int deleteThreads = THREAD_COUNT / 4;
        int getThreads = THREAD_COUNT - postThreads - deleteThreads;

        for (int i = 0; i < postThreads; i++) {
            executor.submit(() -> {
                while (true) {
                    try {
                        sendPostTransaction(httpClient);
                        int count = postCount.incrementAndGet();
                        if (count % 100 == 0) {
                            System.out.println("POST /transactions enviados: " + count);
                        }
                        Thread.sleep(5);
                    } catch (Exception e) {
                        System.out.println("Erro POST: " + e.getMessage());
                    }
                }
            });
        }

        for (int i = 0; i < deleteThreads; i++) {
            executor.submit(() -> {
                while (true) {
                    try {
                        sendDeleteTransactions(httpClient);
                        int count = deleteCount.incrementAndGet();
                        if (count % 50 == 0) {
                            System.out.println("DELETE /transactions enviados: " + count);
                        }
                        Thread.sleep(50);
                    } catch (Exception e) {
                        System.out.println("Erro DELETE: " + e.getMessage());
                    }
                }
            });
        }

        for (int i = 0; i < getThreads; i++) {
            executor.submit(() -> {
                while (true) {
                    try {
                        sendGetStatistics(httpClient);
                        int count = getCount.incrementAndGet();
                        if (count % 100 == 0) {
                            System.out.println("GET /statistics enviados: " + count);
                        }
                        Thread.sleep(20);
                    } catch (Exception e) {
                        System.out.println("Erro GET: " + e.getMessage());
                    }
                }
            });
        }
    }

    private static void sendPostTransaction(CloseableHttpClient httpClient) throws Exception {
        HttpPost post = new HttpPost(TARGET_URL + "/transactions");
        double valor = 10 + (1000 - 10) * random.nextDouble();
        String dataHora = "2025-06-03T10:15:30Z";
        String json = String.format("{\"valor\": %.2f, \"dataHora\": \"%s\"}", valor, dataHora);
        post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        try (var response = httpClient.execute(post)) {
            int status = response.getCode();
            if (status != 201) {
                System.out.println("POST falhou com código: " + status);
            }
        }
    }

    private static void sendDeleteTransactions(CloseableHttpClient httpClient) throws Exception {
        HttpDelete delete = new HttpDelete(TARGET_URL + "/transactions");
        try (var response = httpClient.execute(delete)) {
            int status = response.getCode();
            if (status != 204) {
                System.out.println("DELETE falhou com código: " + status);
            }
        }
    }

    private static void sendGetStatistics(CloseableHttpClient httpClient) throws Exception {
        HttpGet get = new HttpGet(TARGET_URL + "/statistics");
        try (var response = httpClient.execute(get)) {
            int status = response.getCode();
            if (status != 200) {
                System.out.println("GET falhou com código: " + status);
            }
        }
    }
}

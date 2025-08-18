package br.com.matheuskaiky.gupyfy.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class is responsible for all HTTP communication with the Gupy external API.
 */
@Component
public class GupyClient {

    private static final Logger log = LoggerFactory.getLogger(GupyClient.class);

    private final OkHttpClient httpClient = new OkHttpClient();

    private static final String GUPY_API_URL = "https://api.gupy.io/api/v1/";

    /**
     * Fetches the raw job listings from the Gupy API.
     * @return The raw JSON response as a String, or null if an error occurs.
     */
    public String fetchJobs() {
        log.info("Attempting to fetch jobs from Gupy API...");

        Request request = new Request.Builder()
                .url(GUPY_API_URL)
                .header("User-Agent", "Gupyfy Job Hunter")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Failed to fetch jobs from Gupy. Status code: {}", response.code());
                throw new IOException("Unexpected code " + response);
            }

            log.info("Successfully fetched jobs from Gupy.");
            return response.body().string();

        } catch (IOException e) {
            log.error("An error occurred while trying to fetch jobs from Gupy.", e);
            return null;
        }
    }
}
package br.com.matheuskaiky.gupyfy.client;

import br.com.matheuskaiky.gupyfy.client.dto.GupyApiResponseDto;
import br.com.matheuskaiky.gupyfy.client.dto.GupyJobDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GupyClient {

    // Define a limit for the number of fetched jobs of each request.
    // At the time of writing this code, the Gupy API has a maximum limit of 100 jobs per request without error.
    private static final int LIMIT = 100;
    private static final String GUPY_API_URL = "https://employability-portal.gupy.io/api/v1/" +
            "jobs?sortBy=publishedDate&limit=" + LIMIT;

    private static final Logger log = LoggerFactory.getLogger(GupyClient.class);
    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Fetches all job listings from the Gupy API by handling pagination.
     *
     * @param request Query parameters to append to the API request.
     * @return A list of GupyJobDto representing the jobs found.
     */
    public List<GupyJobDto> fetchJobs(String request) {
        String url = GUPY_API_URL + request;
        int page = 0;
        List<GupyJobDto> allJobs = new ArrayList<>(); // Use a mutable list to add jobs from each page

        while (true) {
            String offset = "&offset=" + (page * LIMIT);
            String urlRequest = url + offset;
            log.info("Fetching jobs from: {}", urlRequest);

            Request httpRequest = new Request.Builder().url(urlRequest).build();

            try (Response response = httpClient.newCall(httpRequest).execute()) {
                if (!response.isSuccessful()) {
                    log.error("Failed to fetch jobs from Gupy API: {}", response.message());
                    break;
                }

                String responseBody = response.body().string();
                GupyApiResponseDto apiResponse = objectMapper.readValue(responseBody, GupyApiResponseDto.class);

                if (apiResponse == null || apiResponse.data() == null || apiResponse.data().isEmpty()) {
                    log.info("No more jobs to fetch, ending search.");
                    break;
                }

                allJobs.addAll(apiResponse.data());
                page++;

                // Being a good neighbour by delaying the request
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.warn("Thread sleep was interrupted.", e);
                    Thread.currentThread().interrupt();
                }

            } catch (IOException e) {
                log.error("An IO error occurred while fetching jobs from Gupy API", e);
                break;
            }
        }
        return allJobs;
    }
}
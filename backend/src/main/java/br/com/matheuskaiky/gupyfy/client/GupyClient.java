package br.com.matheuskaiky.gupyfy.client;

import br.com.matheuskaiky.gupyfy.client.dto.GupyApiResponseDto;
import br.com.matheuskaiky.gupyfy.domain.Job;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GupyClient {

    // Define a limit for the number of fetched jobs of each request
    private static final int LIMIT = 100;
    private static final String GUPY_API_URL = "https://employability-portal.gupy.io/api/v1/" +
            "jobs?sortBy=publishedDate&limit=" + LIMIT;

    private static final Logger log = LoggerFactory.getLogger(GupyClient.class);
    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Job> fetchJobs() {
        return null;
    }

    // This method fetches jobs from the Gupy from the given request URL, that will be
    // used with the GUPY_API_URL constant.
    public List<Job> fetchJobs(String request) {
        String url = GUPY_API_URL + request;
        int page = 0; // Page count starts at 0
        boolean jobFetched = false;
        while (true) {
            String offset = "&offset=" + (page * LIMIT);
            String urlRequest = url + offset;

            Request httpRequest = new Request.Builder()
                    .url(urlRequest)
                    .build();
            try (Response response = httpClient.newCall(httpRequest).execute()) {
                if (jobFetched) {
                    log.info("Search has ended");
                }
                else if (!response.isSuccessful()) {
                    log.error("Failed to fetch jobs from Gupy API: {}", response.message());
                    return Collections.emptyList();
                }
                jobFetched = true;

                try {
                    String responseBody = response.body().string();
                } catch (IOException e) {
                    System.out.println("Failed to fetch jobs from Gupy API: " + e.getMessage());
                }
                GupyApiResponseDto apiResponse = objectMapper.readValue(responseBody, GupyApiResponseDto.class);
                return apiResponse.jobs().stream()
                        .map(Job::fromDto)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                log.error("Error fetching jobs from Gupy API", e);
                return Collections.emptyList();
            }
            page++;
        }
    }
}
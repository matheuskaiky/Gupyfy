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
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class GupyClient {

    // Define a limit for the number of fetched jobs of each request.
    // At the time of writing this code, the Gupy API has a maximum limit of 100 jobs per request without error.
    private static final int LIMIT = 100;
    private static final String GUPY_API_URL = "https://employability-portal.gupy.io/api/v1/" +
            "jobs?sortBy=publishedDate&limit=" + LIMIT;
    private static final int TIMEOUT = 30;
    private static final Logger log = LoggerFactory.getLogger(GupyClient.class);

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GupyClient() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Fetches all job listings from the Gupy API by handling pagination automatically.
     * It is designed to be resilient to common API issues, such as timeouts on high page numbers.
     *
     * @param request A string containing query parameters to be appended to the base API URL.
     * @return A {@link List} of {@link GupyJobDto} objects representing the jobs found.
     */
    public List<GupyJobDto> fetchJobs(String request) {
        String url = GUPY_API_URL + request;
        int page = 0;
        List<GupyJobDto> allJobs = new ArrayList<>();

        while (true) {
            String urlRequest = url + "&offset=" + (page * LIMIT);
            Request httpRequest = new Request.Builder().url(urlRequest).build();

            try (Response response = httpClient.newCall(httpRequest).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    GupyApiResponseDto apiResponse = objectMapper.readValue(responseBody, GupyApiResponseDto.class);

                    if (apiResponse == null || apiResponse.data() == null || apiResponse.data().isEmpty()) {
                        log.info("Search finished normally. The API returned no more data.");
                        break;
                    }
                    allJobs.addAll(apiResponse.data());
                    page++;
                    continue;
                }

                log.warn("Received non-successful HTTP status {} for offset {}. Message: {}",
                        response.code(), (page * LIMIT), response.message());

                if (page == 0) {
                    log.error("The first page of the job search failed. Halting the process. This may indicate an API or network issue.");
                    break;
                }

                if (response.code() == 408 || response.code() == 400) {
                    log.info("Potential end of search detected. Verifying by checking the previous page (page {})...", page - 1);
                    if (isPreviousPageStillValid(url, page - 1)) {
                        log.info("Verification successful: Previous page has data. The search has correctly finished.");
                    } else {
                        log.warn("Verification failed: Previous page also failed or returned no data. Halting due to a likely API issue.");
                    }
                    break;
                }

                log.error("Stopping search due to an unrecoverable HTTP error: {}", response.code());
                break;

            } catch (IOException e) {
                log.error("A network error occurred while fetching jobs. Stopping the process.", e);
                break;
            }
        }
        return allJobs;
    }

    /**
     * Helper method to verify if the job search has ended by checking the last known valid page.
     *
     * @param baseUrl The base URL without the offset.
     * @param lastKnownValidPage The index of the last page that should have contained data.
     * @return {@code true} if the last page successfully returns data, confirming the end of the search; {@code false} otherwise.
     */
    private boolean isPreviousPageStillValid(String baseUrl, int lastKnownValidPage) {
        String urlRequest = baseUrl + "&offset=" + (lastKnownValidPage * LIMIT);
        Request httpRequest = new Request.Builder().url(urlRequest).build();

        log.info("Executing verification call to: {}", urlRequest);
        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                GupyApiResponseDto apiResponse = objectMapper.readValue(responseBody, GupyApiResponseDto.class);
                return apiResponse != null && apiResponse.data() != null && !apiResponse.data().isEmpty();
            }
        } catch (IOException e) {
            log.error("Verification check failed due to a network error.", e);
        }
        return false;
    }
}
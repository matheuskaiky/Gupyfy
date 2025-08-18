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

    private static final Logger log = LoggerFactory.getLogger(GupyClient.class);
    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String GUPY_API_URL = "https://api.gupy.io/api/";

    /**
     * Fetches and parses job listings from the Gupy API.
     * @return A list of Job domain objects.
     */
    public List<Job> fetchJobs() {
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

            String jsonBody = response.body().string();
            log.info("Successfully fetched jobs JSON from Gupy.");

            GupyApiResponseDto apiResponse = objectMapper.readValue(jsonBody, GupyApiResponseDto.class);

            if (apiResponse == null || apiResponse.data() == null) {
                return Collections.emptyList();
            }

            return apiResponse.data().stream()
                    .map(dto -> new Job(dto.title(), dto.company().name(), dto.url()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("An error occurred while trying to fetch or parse jobs from Gupy.", e);
            return Collections.emptyList();
        }
    }
}
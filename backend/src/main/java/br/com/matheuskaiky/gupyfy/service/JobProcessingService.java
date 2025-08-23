package br.com.matheuskaiky.gupyfy.service;

import br.com.matheuskaiky.gupyfy.client.GupyClient;
import br.com.matheuskaiky.gupyfy.client.dto.GupyJobDto;
import br.com.matheuskaiky.gupyfy.domain.Company;
import br.com.matheuskaiky.gupyfy.domain.Job;
import br.com.matheuskaiky.gupyfy.mapper.JobMapper;
import br.com.matheuskaiky.gupyfy.repository.JobRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobProcessingService {

    private static final Logger log = LoggerFactory.getLogger(JobProcessingService.class);

    private final GupyClient gupyClient;
    private final JobRepository jobRepository;
    private final CompanyProcessingService companyProcessingService;
    private final JobMapper jobMapper;

    public JobProcessingService(GupyClient gupyClient, JobRepository jobRepository, JobMapper jobMapper,
                                CompanyProcessingService companyProcessingService) {
        this.gupyClient = gupyClient;
        this.jobRepository = jobRepository;
        this.companyProcessingService = companyProcessingService;
        this.jobMapper = jobMapper;
    }

    @Scheduled(fixedRateString = "PT1H")
    public void processNewJobs() {
        processNewJobs("");
    }

    /**
     * A scheduled task that runs periodically to fetch, process, and save new jobs.
     * This method orchestrates the entire workflow:
     * 1. Fetches raw job data from the Gupy API.
     * 2. Checks for duplicates to avoid reprocessing existing jobs.
     * 3. Processes company information, creating or updating as needed.
     * 4. Maps the job data to a database entity.
     * 5. Saves the new job to the database.
     */
    public void processNewJobs(String request) {
        log.info("Starting job processing task...");

        request = ""; // In the future, implement filters here, but for now, ignore the request parameter
        List<GupyJobDto> fetchedJobDtos = gupyClient.fetchJobs(request);
        int newJobsSaved = 0;

        if (fetchedJobDtos.isEmpty()) {
            log.info("No jobs fetched from Gupy API.");
            return;
        }

        for (GupyJobDto dto : fetchedJobDtos) {
            if (jobRepository.findByUrl(dto.jobUrl()).isEmpty()) {

                Company companyEntity = companyProcessingService.processCompany(
                        dto.companyId(),
                        dto.companyName(),
                        dto.logoUrl()
                );

                Job job = jobMapper.toEntity(dto, companyEntity);

                jobRepository.save(job);
                newJobsSaved++;
                log.info("New job saved: {}", job.getTitle());
            }
        }

        log.info("Job processing task finished. Found and saved {} new jobs.", newJobsSaved);
    }
}
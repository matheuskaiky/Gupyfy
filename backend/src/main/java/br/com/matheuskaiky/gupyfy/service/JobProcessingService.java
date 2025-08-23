package br.com.matheuskaiky.gupyfy.service;

import br.com.matheuskaiky.gupyfy.client.GupyClient;
import br.com.matheuskaiky.gupyfy.client.dto.GupyJobDto;
import br.com.matheuskaiky.gupyfy.domain.Company;
import br.com.matheuskaiky.gupyfy.domain.Job;
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

    public JobProcessingService(GupyClient gupyClient, JobRepository jobRepository,
                                CompanyProcessingService companyProcessingService) {
        this.gupyClient = gupyClient;
        this.jobRepository = jobRepository;
        this.companyProcessingService = companyProcessingService;
    }

    @NotNull
    private static Job getJob(GupyJobDto dto, Company companyEntity) {
        Job job = new Job();
        job.setGupyId(dto.gupyId());
        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setJobLevel(null);
        job.setWorkPlace(dto.workPlace());
        job.setPublishedDate(dto.publishedDate());
        job.setDeadlineDate(dto.deadlineDate());
        job.setJobOfferType(dto.jobOfferType());
        job.setJobUrl(dto.jobUrl());

        job.setCompany(companyEntity);
        return job;
    }

    @Scheduled(fixedRateString = "PT1H")
    public void processNewJobs() {
        processNewJobs("");
    }

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

                Job job = getJob(dto, companyEntity);

                jobRepository.save(job);
                newJobsSaved++;
                log.info("New job saved: {}", job.getTitle());
            }
        }

        log.info("Job processing task finished. Found and saved {} new jobs.", newJobsSaved);
    }
}
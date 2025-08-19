package br.com.matheuskaiky.gupyfy.service;

import br.com.matheuskaiky.gupyfy.client.GupyClient;
import br.com.matheuskaiky.gupyfy.domain.Job;
import br.com.matheuskaiky.gupyfy.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobProcessingService {

    private static final Logger log = LoggerFactory.getLogger(JobProcessingService.class);

    private final GupyClient gupyClient;
    private final JobRepository jobRepository;

    public JobProcessingService(GupyClient gupyClient, JobRepository jobRepository) {
        this.gupyClient = gupyClient;
        this.jobRepository = jobRepository;
    }

    public void processNewJobs() {
        log.info("Starting job processing task...");

        List<Job> fetchedJobs = gupyClient.fetchJobs();
        int newJobsSaved = 0;

        for (Job job : fetchedJobs) {
            if (jobRepository.findByUrl(job.getUrl()).isEmpty()) {
                jobRepository.save(job);
                newJobsSaved++;
                log.info("New job saved: {}", job.getTitle());
            }
        }

        log.info("Job processing task finished. Found and saved {} new jobs.", newJobsSaved);
    }
}
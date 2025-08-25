package br.com.matheuskaiky.gupyfy.service;

import br.com.matheuskaiky.gupyfy.client.GupyClient;
import br.com.matheuskaiky.gupyfy.client.dto.GupyJobDto;
import br.com.matheuskaiky.gupyfy.domain.Company;
import br.com.matheuskaiky.gupyfy.domain.Job;
import br.com.matheuskaiky.gupyfy.mapper.JobMapper;
import br.com.matheuskaiky.gupyfy.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        try {
            Thread.sleep(10);
        }  catch (InterruptedException e) {
            log.error("", e);
        }
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

        Optional<Job> latestJobOptional = jobRepository.findTopByOrderByPublishedDateDesc();
        if (latestJobOptional.isPresent()) {
            log.info("Latest job in DB is '{}' (Gupy ID: {}). This will be used as the stop marker.",
                    latestJobOptional.get().getTitle(), latestJobOptional.get().getGupyId());
        } else {
            log.info("No jobs found in the database. Starting a full scan.");
        }

        List<GupyJobDto> fetchedJobDtos = gupyClient.fetchJobs(request);
        int newJobsSaved = 0;

        if (fetchedJobDtos.isEmpty()) {
            log.info("No jobs fetched from Gupy API.");
            return;
        }

        for (GupyJobDto dto : fetchedJobDtos) {
            if (latestJobOptional.isPresent() && dto.gupyId().equals(latestJobOptional.get().getGupyId())) {
                log.info("Stop marker reached. All newer jobs have been processed.");
                break;
            }

            if (jobRepository.findByJobUrl(dto.jobUrl()).isEmpty()) {
                Job job = jobMapper.toEntity(dto);
                jobRepository.save(job);
                newJobsSaved++;
                log.info("New job saved: {}", job.getTitle());
            }
        }

        log.info("Job processing task finished. Found and saved {} new jobs.", newJobsSaved);
    }

    /**
     * A scheduled task that classifies the seniority levels of all jobs in the database.
     * This method iterates through each job, infers its seniority level based on its title,
     * and updates the job record accordingly.
     * It uses the JobClassifierService to perform the classification.
     */
    public void classifySeniorityLevels() {
        log.info("Starting job seniority classification task...");
        JobClassifierService jobClassifierService = new JobClassifierService();
        List<Job> allJobs = jobRepository.findAll();

        int totalJobs = allJobs.size();
        int analysedJobs = 0;

        int noTitleJobs = 0;
        int updatedJobs = 0;
        int notApplicableJobs = 0;

        for (Job job : allJobs) {
            String jobTitle = job.getTitle();
            if (jobTitle == null || jobTitle.isBlank()) {
                log.info("{}/{} ({}%) | Job ID {} has no title. Skipping seniority classification and setting null.",
                        analysedJobs, totalJobs, (100.0 * analysedJobs/totalJobs), job.getId());
                job.setJobLevel(null);
                noTitleJobs++;
                continue;
            }
            Optional<String> seniorityLevel = jobClassifierService.inferJobLevelFromTitle(jobTitle);
            if (seniorityLevel.isPresent()) {
                job.setJobLevel(seniorityLevel.get());
                log.info("{}/{} ({}%) | Job {} classified as level '{}'", analysedJobs, totalJobs,
                         (100.0 * analysedJobs/totalJobs),job.getTitle(), seniorityLevel.get());
                updatedJobs++;
            } else {
                log.info("{}/{} ({}%) | Job {} could not be classified. Setting level to not applicable.",
                         analysedJobs, totalJobs, (100.0 * analysedJobs/totalJobs), job.getTitle());
                job.setJobLevel("not_applicable");
                notApplicableJobs++;
            }
            jobRepository.save(job);
            analysedJobs++;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.error("Error during sleep between classifications", e);
            }
        }
        log.info("Job seniority classification task finished.");

        log.info("Seniority classification summary: {} jobs without title, {} jobs with seniority level, {} jobs not updated.",
                noTitleJobs, updatedJobs, notApplicableJobs);
    }

    // This method update city and state of jobs with null city or state
    public void updateJobLocations() {
        log.info("Starting job location update task...");
        List<Job> jobsToUpdate = jobRepository.findByCityIsNull();

        int totalJobs = jobsToUpdate.size();
        int updatedJobs = 0;

        for (int i = 0; i < totalJobs; i++) {
            Job job = jobsToUpdate.get(i);
            String location = job.getLocation();
            if (location != null && !location.isBlank()) {
                String[] parts = location.split(" - ");
                if (parts.length == 2) {
                    job.setCity(parts[0].trim());
                    job.setState(parts[1].trim());
                    jobRepository.save(job);
                    updatedJobs++;
                    log.info("{}/{} ({}%) | Updated job ID {} with city '{}' and state '{}'",
                            i + 1, totalJobs, (100.0 * (i + 1) / totalJobs), job.getId(), job.getCity(), job.getState());
                } else {
                    log.warn("{}/{} ({}%) | Job ID {} has an unexpected location format: '{}'. Skipping.",
                            i + 1, totalJobs, (100.0 * (i + 1) / totalJobs), job.getId(), location);
                }
            } else {
                log.warn("{}/{} ({}%) | Job ID {} has no location information. Skipping.",
                        i + 1, totalJobs, (100.0 * (i + 1) / totalJobs), job.getId());
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.error("Error during sleep between location updates", e);
            }
        }
        log.info("Job location update task finished. Updated {} jobs.", updatedJobs);
    }
}
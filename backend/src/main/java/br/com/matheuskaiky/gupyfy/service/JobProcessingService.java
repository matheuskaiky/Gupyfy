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

import java.util.ArrayList;
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
        } catch (InterruptedException e) {
            log.error("", e);
        }
        processNewJobs("");
    }

    /**
     * A scheduled task that runs periodically to identify and mark inactive jobs.
     * This method checks each job in the database against the Gupy API to see if it is still active.
     * If a job is not found in the API results, it is marked as inactive in the database.
     */
    //@Scheduled(fixedRateString = "PT6H")
    public void searchInactiveJobs() {
        log.info("Starting inactive job search task...");

        List<Job> allJobs = jobRepository.findAll();
        int inactiveJobsCount = 0;

        for (Job job : allJobs) {
            String request = "jobName=" + job.getTitle();

            List<GupyJobDto> searchedJobs = gupyClient.fetchJobs(request);

            boolean jobFound = false;
            for (GupyJobDto dto : searchedJobs) {
                if (dto.jobUrl().equals(job.getJobUrl())) {
                    jobFound = true;
                    break;
                }
            }

            if (!jobFound) {
                job.setIsActive(false);
                jobRepository.save(job);
                log.info("Job '{}' marked as inactive because it was not found in the Gupy API.", job.getTitle());
                inactiveJobsCount++;
            }
        }

        log.info("Inactive job search task finished. Found and marked {} jobs as inactive.", inactiveJobsCount);
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
                        analysedJobs, totalJobs, (100.0 * analysedJobs / totalJobs), job.getId());
                job.setJobLevel(null);
                noTitleJobs++;
                continue;
            }
            Optional<String> seniorityLevel = jobClassifierService.inferJobLevelFromTitle(jobTitle);
            if (seniorityLevel.isPresent()) {
                job.setJobLevel(seniorityLevel.get());
                log.info("{}/{} ({}%) | Job {} classified as level '{}'", analysedJobs, totalJobs,
                        (100.0 * analysedJobs / totalJobs), job.getTitle(), seniorityLevel.get());
                updatedJobs++;
            } else {
                log.info("{}/{} ({}%) | Job {} could not be classified. Setting level to not applicable.",
                        analysedJobs, totalJobs, (100.0 * analysedJobs / totalJobs), job.getTitle());
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
    // Its temporary, because the Jobs that are actually in the DB don't have city and state
    // and it needs to be updated and fixed.
    // TODO: Erase after use.
    public void updateJobLocations() {
        log.info("Starting job location update task...");
        List<Job> allNullCityJobs = jobRepository.findByCityIsNull();

        int totalJobs = allNullCityJobs.size();
        int updatedJobs = 0;

        for (Job job : allNullCityJobs) {
            if (job.getWorkPlace().equals("remote")) {
                continue;
            }

            if (job.getCity() != null) {
                continue;
            }

            String request = "&jobName=" + job.getTitle();

            List<GupyJobDto> searchedJobs = gupyClient.fetchJobs(request);

            if (searchedJobs.isEmpty()) {
                log.info("No matching job found in Gupy API for job '{}'. Skipping it in location update.", job.getTitle());
                log.warn("Job '{}' will be marked as inactive because it was not found in the Gupy API.", job.getTitle());
                job.setIsActive(false);
                continue;
            }

            for (GupyJobDto dto : searchedJobs) {
                if (dto.jobUrl().equals(job.getJobUrl())) {
                    job.setCity(jobMapper.toEntity(dto).getCity()); 
                    updatedJobs++;
                    log.info("{}/{} ({}%) | Job '{}' location updated to {}",
                            updatedJobs, totalJobs, String.format("%.2f", (100.0 * updatedJobs / totalJobs)), job.getTitle(),
                            job.getCity() != null ? job.getCity().getName() + " - " + job.getCity().getState().getAcronym() : "null");
                    jobRepository.save(job);
                    break;
                } else {
                    if (jobRepository.findByJobUrl(dto.jobUrl()).isEmpty()) {
                        jobRepository.save(jobMapper.toEntity(dto));
                        log.info("While updating location, new job '{}' found and added to the database.",
                                dto.title());
                    }
                }
            }
        }
    }
}
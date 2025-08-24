// In backend/src/main/java/br/com/matheuskaiky/gupyfy/controller/JobController.java
package br.com.matheuskaiky.gupyfy.controller;

import br.com.matheuskaiky.gupyfy.domain.Job;
import br.com.matheuskaiky.gupyfy.repository.JobRepository; // Import repository
import br.com.matheuskaiky.gupyfy.service.JobProcessingService; // Import service
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Import PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing job-related HTTP requests.
 */
@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173") // Allows your frontend to connect
public class JobController {

    private final JobRepository jobRepository;
    private final JobProcessingService jobProcessingService;

    public JobController(JobRepository jobRepository, JobProcessingService jobProcessingService) {
        this.jobRepository = jobRepository;
        this.jobProcessingService = jobProcessingService;
    }

    /**
     * Handles GET requests to /api/jobs.
     * Fetches and returns a list of all jobs currently stored in the database.
     *
     * @return A {@link List} of {@link Job} objects.
     */
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll(); // Now uses real data
    }

    /**
     * Handles POST requests to /api/jobs/fetch.
     * Manually triggers the process of fetching and saving new jobs from the Gupy API.
     *
     * @return A response entity with a success message.
     */
    @PostMapping("/fetch") // Use POST for actions that change state
    public ResponseEntity<String> fetchNewJobs() {
        // Run the processing in a new thread so the HTTP request can return immediately
        new Thread(() -> jobProcessingService.processNewJobs("")).start();
        return ResponseEntity.ok("Job fetching process started in the background.");
    }

    /**
     * Handles POST requests to /api/jobs/refresh.
     * Deletes all existing jobs and fetches fresh data from the Gupy API.
     *
     * @return A response entity with a success message.
     */
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAllJobs() {
        new Thread(() -> {
            jobRepository.deleteAll();
            jobProcessingService.processNewJobs();
        }).start();
        return ResponseEntity.ok("Job refresh process started in the background.");
    }
}
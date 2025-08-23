package br.com.matheuskaiky.gupyfy.controller;

import br.com.matheuskaiky.gupyfy.domain.Job;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing job-related HTTP requests.
 * This class exposes endpoints for clients (like the frontend application) to interact with job data.
 */
@RestController // Informs Spring that this class will handle HTTP requests and return JSON.
@RequestMapping("/api/jobs") // Defines that all methods in this class will be under the /api/jobs path.
public class JobController {

    /**
     * Handles GET requests to /api/jobs.
     * Fetches and returns a list of all jobs currently stored in the database.
     *
     * @return A {@link List} of {@link Job} objects.
     */
    /*
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
     */

    /**
     * This method handles GET requests to /api/jobs.
     * For Phase 1, it returns a hardcoded list of mock jobs.
     *
     * @return A list of Job objects.
     */
    @GetMapping
    public List<Job> getAllJobs() {
        // This is our mock data. Later, this will come from the database. Use the Job class as base to
        // create mock data: long gupyId, String title, String description, String jobLevel, Company company,
        //               String workPlace, Date publishedDate, Date deadlineDate, String jobOfferType, String url
        return List.of(
                new Job(1L, "Software Engineer", "Develop and maintain software applications.", "Mid-Level",
                        null, "Remote", new java.util.Date(), null, "vacancy_type_effective",
                        "https://example.com/job/1"),
                new Job(2L, "Data Scientist", "Analyze and interpret complex data.", "Senior",
                        null, "On-site", new java.util.Date(), null, "vacancy_type_talent_pool",
                        "https://example.com/job/2"),
                new Job(3L, "Product Manager", "Oversee product development from conception to launch.", "Lead",
                        null, "Hybrid", new java.util.Date(), null, "vacancy_type_effective",
                        "https://example.com/job/3")
        );
    }
}
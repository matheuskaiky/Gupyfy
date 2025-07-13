package br.com.matheuskaiky.gupyfy.controller;

import br.com.matheuskaiky.gupyfy.domain.Job;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class is the REST Controller responsible for handling all HTTP requests related to jobs.
 */
@RestController // Informs Spring that this class will handle HTTP requests and return JSON.
@RequestMapping("/api/jobs") // Defines that all methods in this class will be under the /api/jobs path.
public class JobController {

    /**
     * This method handles GET requests to /api/jobs.
     * For Phase 1, it returns a hardcoded list of mock jobs.
     *
     * @return A list of Job objects.
     */
    @GetMapping
    public List<Job> getAllJobs() {
        // This is our mock data. Later, this will come from the database.
        return List.of(
                new Job("1", "Senior Java Developer", "Senior","Tech Solutions Inc.", "Hybrid","https://example.com/job/1"),
                new Job("2", "React Front-end Engineer", "None","Creative Minds LLC", "Presential", "https://example.com/job/2"),
                new Job("3", "Cloud DevOps Specialist", "None","InfraCloud Co.", "Hybrid","https://example.com/job/3")
        );
    }
}
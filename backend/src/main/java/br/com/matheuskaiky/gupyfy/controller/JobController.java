package br.com.matheuskaiky.gupyfy.controller;

import br.com.matheuskaiky.gupyfy.domain.Job;
import br.com.matheuskaiky.gupyfy.repository.JobRepository;
import br.com.matheuskaiky.gupyfy.service.JobClassifierService;
import br.com.matheuskaiky.gupyfy.service.JobProcessingService;
import br.com.matheuskaiky.gupyfy.service.LocationProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing job-related HTTP requests.
 */
@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {

    private final JobRepository jobRepository;
    private final JobProcessingService jobProcessingService;
    private final JobClassifierService jobClassifierService;
    private final LocationProcessingService locationProcessingService;

    public JobController(JobRepository jobRepository, JobProcessingService jobProcessingService, JobClassifierService jobClassifierService, LocationProcessingService locationProcessingService) {
        this.jobRepository = jobRepository;
        this.jobProcessingService = jobProcessingService;
        this.jobClassifierService = jobClassifierService;
        this.locationProcessingService = locationProcessingService;
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
    @PostMapping("/fetch")
    public ResponseEntity<String> fetchNewJobs() {
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

    /**
     * Handles POST requests to /api/jobs/classify.
     * Initiates the classification of all jobs in the database to determine their seniority levels.
     * Use curl -X POST http://localhost:8080/api/jobs/classify
     *
     * @return A response entity with a success message.
     */
    @PostMapping("/classify")
    public ResponseEntity<String> classifyAllJobs() {
        new Thread(jobProcessingService::classifySeniorityLevels).start();
        return ResponseEntity.ok("Job classification process started in the background.");
    }

    // This method update city and state of jobs with null city or state
    @PostMapping("/update-location")
    public ResponseEntity<String> updateJobLocations() {
        new Thread(jobProcessingService::updateJobLocations).start();
        return ResponseEntity.ok("Job location update process started in the background.");
    }

    // This method will add all brazillian states (not included cities) direct in JobController, because it is a one-time operation
    // Just to add states to the database
    // TODO: Erase after use.
    @PostMapping("/add-brazilian-states")
    public ResponseEntity<String> addBrazilianStates() {
        String[][] estados = {
            {"Acre", "AC"},
            {"Alagoas", "AL"},
            {"Amapá", "AP"},
            {"Amazonas", "AM"},
            {"Bahia", "BA"},
            {"Ceará", "CE"},
            {"Distrito Federal", "DF"},
            {"Espírito Santo", "ES"},
            {"Goiás", "GO"},
            {"Maranhão", "MA"},
            {"Mato Grosso", "MT"},
            {"Mato Grosso do Sul", "MS"},
            {"Minas Gerais", "MG"},
            {"Pará", "PA"},
            {"Paraíba", "PB"},
            {"Paraná", "PR"},
            {"Pernambuco", "PE"},
            {"Piauí", "PI"},
            {"Rio de Janeiro", "RJ"},
            {"Rio Grande do Norte", "RN"},
            {"Rio Grande do Sul", "RS"},
            {"Rondônia", "RO"},
            {"Roraima", "RR"},
            {"Santa Catarina", "SC"},
            {"São Paulo", "SP"},
            {"Sergipe", "SE"},
            {"Tocantins", "TO"}
        };
        new Thread(() -> {
            for (String[] estado : estados) {
                locationProcessingService.addState(estado[0], estado[1]);
            }
        }).start();
        return ResponseEntity.ok("Brazilian states addition process started in the background.");
    }
}
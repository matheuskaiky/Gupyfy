package br.com.matheuskaiky.gupyfy.mapper;

import br.com.matheuskaiky.gupyfy.client.dto.GupyJobDto;
import br.com.matheuskaiky.gupyfy.domain.Job;
import br.com.matheuskaiky.gupyfy.service.CompanyProcessingService;
import br.com.matheuskaiky.gupyfy.service.LocationProcessingService;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    private final CompanyProcessingService companyProcessingService;
    private final LocationProcessingService locationProcessingService;

    public JobMapper(CompanyProcessingService companyProcessingService, LocationProcessingService locationProcessingService) {
        this.companyProcessingService = companyProcessingService;
        this.locationProcessingService = locationProcessingService;
    }

    /**
     * Converts a GupyJobDto and a Company entity into a Job entity.
     *
     * @param dto The Data Transfer Object received from the Gupy API.
     * @return A new Job entity, ready to be saved.
     */
    public Job toEntity(GupyJobDto dto) {
        if (dto == null) {
            return null;
        }

        Job job = new Job();
        job.setGupyId(dto.gupyId());
        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setJobUrl(dto.jobUrl());
        job.setWorkPlace(dto.workPlace());
        job.setPublishedDate(dto.publishedDate());
        job.setDeadlineDate(dto.deadlineDate());
        job.setJobOfferType(dto.jobOfferType());
        job.setIsActive(true);

        // job.setJobLevel(...);
        // TODO: Map this field when available in the DTO
        // This field will be assigned for an LLM or another service
        // to analyze the job description and determine the level.

        job.setCompany(companyProcessingService.processCompany(
                dto.companyId(),
                dto.companyName(),
                dto.logoUrl()
        ));

        if (dto.workPlace() != null) {
            if (dto.workPlace().equals("remote")) {
                job.setCity(null);
                return job;
            }
        }

        job.setCity(locationProcessingService.processCity(
                dto.jobCity(),
                dto.jobState()
        ));

        return job;
    }
}
// Create a new file in a 'mapper' package: backend/src/main/java/br/com/matheuskaiky/gupyfy/mapper/JobMapper.java
package br.com.matheuskaiky.gupyfy.mapper;

import br.com.matheuskaiky.gupyfy.client.dto.GupyJobDto;
import br.com.matheuskaiky.gupyfy.domain.Company;
import br.com.matheuskaiky.gupyfy.domain.Job;
import br.com.matheuskaiky.gupyfy.service.CompanyProcessingService;
import org.springframework.stereotype.Component;

@Component // Register this class as a Spring Bean so we can inject it
public class JobMapper {

    private final CompanyProcessingService companyProcessingService;

    public JobMapper(CompanyProcessingService companyProcessingService) {
        this.companyProcessingService = companyProcessingService;
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

        // job.setJobLevel(...);
        // TODO: Map this field when available in the DTO
        // This field will be assigned for an LLM or another service
        // to analyze the job description and determine the level.

        job.setCompany(companyProcessingService.processCompany(
                dto.companyId(),
                dto.companyName(),
                dto.logoUrl()
        ));

        job.setCity();

        return job;
    }
}
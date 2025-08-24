package br.com.matheuskaiky.gupyfy.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GupyJobDto(
        @JsonProperty("id") Long gupyId,
        @JsonProperty("name") String title,
        @JsonProperty("description") String description,

        @JsonProperty("companyId")  Long companyId,
        @JsonProperty("careerPageName") String companyName,
        @JsonProperty("careerPageUrl") String logoUrl,

        @JsonProperty("workplaceType") String workPlace,
        @JsonProperty("publishedDate") Date publishedDate,
        @JsonProperty("applicationDeadline") Date deadlineDate,
        @JsonProperty("type") String jobOfferType,
        @JsonProperty("jobUrl") String jobUrl
) {}
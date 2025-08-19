package br.com.matheuskaiky.gupyfy.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GupyJobDto(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String title,
        @JsonProperty("jobLevel") String jobLevel,
        @JsonProperty("company") CompanyDto company,
        @JsonProperty("workMode") String workMode,
        @JsonProperty("jobUrl") String url
) {}
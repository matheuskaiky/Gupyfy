package br.com.matheuskaiky.gupyfy.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompanyDto(String name) {}
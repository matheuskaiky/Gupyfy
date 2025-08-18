package br.com.matheuskaiky.gupyfy.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GupyApiResponseDto(List<GupyJobDto> data) {}
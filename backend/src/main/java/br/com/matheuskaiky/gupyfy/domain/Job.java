package br.com.matheuskaiky.gupyfy.domain;

/**
 * Represents a job opening.
 * This record is a concise way to create an immutable data carrier class.
 * For Phase 1, we only need the basic fields for our mock data.
 */

public record Job(
    String id,
    String title,
    String jobLevel,
    String companyName,
    String employmentType,
    String url
    ) {}
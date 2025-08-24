package br.com.matheuskaiky.gupyfy.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.matheuskaiky.gupyfy.domain.Company;
import br.com.matheuskaiky.gupyfy.repository.CompanyRepository;
import jakarta.transaction.Transactional;

@Service
public class CompanyProcessingService {

    private final CompanyRepository companyRepository;

    public CompanyProcessingService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Finds a company by its Gupy ID. If it exists, it updates its data only if changed.
     * If it does not exist, it creates a new one.
     * This is the "get or create" approach, optimized to avoid unnecessary database writes.
     *
     * @param gupyId The company ID from the Gupy platform.
     * @param companyName The current name of the company.
     * @param logoUrl The current logo URL of the company (can be null).
     * @return The persisted Company entity, whether it's new or updated.
     */
    @Transactional
    public Company processCompany(long gupyId, String companyName, String logoUrl) {
        return companyRepository.findByGupyId(gupyId)
                .map(existingCompany -> {
                    boolean needsUpdate = !Objects.equals(existingCompany.getCompanyName(), companyName) ||
                                          !Objects.equals(existingCompany.getLogoUrl(), logoUrl);

                    if (needsUpdate) {
                        existingCompany.setCompanyName(companyName);
                        existingCompany.setLogoUrl(logoUrl);
                        return companyRepository.save(existingCompany);
                    }
                    
                    return existingCompany;
                })
                .orElseGet(() -> {
                    Company newCompany = new Company(gupyId, companyName, logoUrl);
                    return companyRepository.save(newCompany);
                });
    }
}
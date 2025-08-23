package br.com.matheuskaiky.gupyfy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.matheuskaiky.gupyfy.domain.Company;
import br.com.matheuskaiky.gupyfy.repository.CompanyRepository;

@Service
public class CompanyProcessingService {

    private static final Logger log = LoggerFactory.getLogger(CompanyProcessingService.class);

    private final CompanyRepository companyRepository;

    public CompanyProcessingService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    private void updateCompanyInfo (Company company, String companyName, String logoUrl) {
        if (!company.getCompanyName().equals(companyName)) {
            company.setCompanyName(companyName);
        }
        if (!company.getLogoUrl().equals(logoUrl)) {
            company.setLogoUrl(logoUrl);
        }
    }

    private Company findCompany (long gupyId, String companyName, String logoUrl) {
        Company company = companyRepository.findByGupyId(gupyId).orElse(null);
        if (company != null) {
            updateCompanyInfo(company, companyName, logoUrl);
        }
        return company;
    }

    private Company createCompany (long gupyId, String companyName, String logoUrl) {
        return new Company(gupyId, companyName, logoUrl);
    }

    public Company processCompany (long gupyId, String companyName, String logoUrl) {
        Company company = findCompany(gupyId, companyName, logoUrl);
        if (company == null) {
            company = createCompany(gupyId, companyName, logoUrl);
        }
        return company;
    }
}

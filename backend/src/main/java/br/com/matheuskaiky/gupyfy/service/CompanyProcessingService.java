package br.com.matheuskaiky.gupyfy.service;

import br.com.matheuskaiky.gupyfy.domain.Company;
import br.com.matheuskaiky.gupyfy.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    private Company findCompany (long id, String companyName, String logoUrl) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            updateCompanyInfo(company, companyName, logoUrl);
        }
        return company;
    }

    private Company createCompany (long id, String companyName, String logoUrl) {
        return new Company(id, companyName, logoUrl);
    }

    public Company processCompany (long id, String companyName, String logoUrl) {
        Company company = findCompany(id, companyName, logoUrl);
        if (company == null) {
            company = createCompany(id, companyName, logoUrl);
        }
        return company;
    }
}

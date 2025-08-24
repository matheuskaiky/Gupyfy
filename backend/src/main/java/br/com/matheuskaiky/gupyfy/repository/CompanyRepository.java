package br.com.matheuskaiky.gupyfy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.matheuskaiky.gupyfy.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyName(String companyName);

    Optional<Company> findByGupyId(Long gupyId);
}

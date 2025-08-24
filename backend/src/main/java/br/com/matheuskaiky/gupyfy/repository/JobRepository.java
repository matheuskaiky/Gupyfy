package br.com.matheuskaiky.gupyfy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.matheuskaiky.gupyfy.domain.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByJobUrl(String jobUrl);

    Optional<Job> findByGupyId(long gupyId);
}
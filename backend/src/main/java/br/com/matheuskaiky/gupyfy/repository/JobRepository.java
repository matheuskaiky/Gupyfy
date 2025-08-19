package br.com.matheuskaiky.gupyfy.repository;

import br.com.matheuskaiky.gupyfy.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByUrl(String url);
}
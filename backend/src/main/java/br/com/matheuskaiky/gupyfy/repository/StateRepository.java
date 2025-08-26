package br.com.matheuskaiky.gupyfy.repository;

import br.com.matheuskaiky.gupyfy.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State,Integer> {

    Optional<State> findByName(String name);

    Optional<State> findByAbbreviation(String abbreviation);
}

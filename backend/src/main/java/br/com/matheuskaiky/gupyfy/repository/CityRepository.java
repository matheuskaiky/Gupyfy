package br.com.matheuskaiky.gupyfy.repository;

import java.util.List;
import java.util.Optional;

import br.com.matheuskaiky.gupyfy.domain.City;
import br.com.matheuskaiky.gupyfy.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    List<City> findAllByState(State state);

    Optional<City> findByNameAndState(String name, State state);
}

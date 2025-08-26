package br.com.matheuskaiky.gupyfy.service;

import br.com.matheuskaiky.gupyfy.domain.City;
import br.com.matheuskaiky.gupyfy.domain.State;
import br.com.matheuskaiky.gupyfy.repository.CityRepository;
import br.com.matheuskaiky.gupyfy.repository.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationProcessingService {

    private static final Logger log = LoggerFactory.getLogger(LocationProcessingService.class);
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public  LocationProcessingService(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    // Just to add states to the database
    // TODO: Erase after use.
    public void addState(String stateName, String stateAcronym) {
        if (stateName == null || stateName.isBlank() || stateAcronym == null || stateAcronym.isBlank()) {
            log.warn("Invalid state information at Job processing");
            return;
        }
        State state = new State(stateName, stateAcronym);
        stateRepository.save(state);
    }

    /**
     * Process a city and its associated state.
     * If the state does not exist in the database, logs a warning and returns null.
     * If the city already exists in the database, returns the existing city.
     * If the city does not exist, creates a new city, saves it to the database, and returns it.
     *
     * @param cityName  The name of the city to process.
     * @param stateName The name or acronym of the state associated with the city.
     * @return The existing or newly created City object, or null if the state is not found or input is invalid.
     */
    public City processCity(String cityName, String stateName) {
        if (cityName == null || cityName.isBlank() || stateName == null || stateName.isBlank()) {
            log.warn("Invalid city or state information at Job processing");
            return null;
        }

        List<State> states = stateRepository.findAll();

        for (State state : states) {
            if (state.getName().equalsIgnoreCase(stateName) || state.getAcronym().equalsIgnoreCase(stateName)) {
                City city = cityRepository.findByNameAndState(cityName, state).orElse(null);
                if (city != null) {
                    return city;
                }
                City newCity = new City(cityName, state);
                cityRepository.save(newCity);
                log.info("New city added to the database: {} - {}", cityName, state.getAcronym());
                return newCity;
            }
        }
        log.warn("State not found in database: '{}'. Possible error with state name or acronym.", stateName);
        return null;
    }
}

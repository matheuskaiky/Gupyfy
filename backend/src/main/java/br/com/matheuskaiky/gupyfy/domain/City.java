package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int ibgeCityId;
    private String cityName;

    @ManyToOne
    private State state;

    public City() {}

    public City(int ibgeCityId, String cityName, State state) {
        this.ibgeCityId = ibgeCityId;
        this.cityName = cityName;
        this.state = state;
    }

    public int getId() { return id; }

    public int getIbgeCityId() { return ibgeCityId; }
    public void setIbgeCityId(int ibgeCityId) { this.ibgeCityId = ibgeCityId; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }
}

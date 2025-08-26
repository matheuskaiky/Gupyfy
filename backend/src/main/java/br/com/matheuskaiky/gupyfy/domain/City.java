package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    private State state;

    public City() {}

    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String cityName) { this.name = cityName; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }
}

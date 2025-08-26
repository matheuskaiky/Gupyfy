package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "states")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String acronym;

    public State() {}

    public State(String name, String acronym) {
        this.name = name;
        this.acronym = acronym;
    }

    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String stateName) { this.name = stateName; }

    public String getAcronym() { return acronym; }
    public void setAcronym(String stateAcronym) { this.acronym = stateAcronym; }
}

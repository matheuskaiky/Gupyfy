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

    private int ibgeStateId;
    private String stateName;
    private String stateAcronym;

    public State() {}

    public State(int ibgeStateId, String stateName, String stateAcronym) {
        this.ibgeStateId = ibgeStateId;
        this.stateName = stateName;
        this.stateAcronym = stateAcronym;
    }

    public int getId() { return id; }

    public int getIbgeStateId() { return ibgeStateId; }
    public void setIbgeStateId(int ibgeStateId) { this.ibgeStateId = ibgeStateId; }

    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }

    public String getStateAcronym() { return stateAcronym; }
    public void setStateAcronym(String stateAcronym) { this.stateAcronym = stateAcronym; }
}

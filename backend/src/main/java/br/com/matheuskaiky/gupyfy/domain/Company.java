package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    private int id;
    private String companyName;
    private String logoUrl;

    public Company() {}

    public Company (int id, String companyName, String logoUrl) {
        this.id = id;
        this.companyName = companyName;
        this.logoUrl = logoUrl;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getCompanyName() {return companyName;}

    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public String getLogoUrl() {return logoUrl;}

    public void setLogoUrl(String logoUrl) {this.logoUrl = logoUrl;}
}

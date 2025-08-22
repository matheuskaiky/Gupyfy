package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.*;

/* Gupy API for Company searching
   https://employability-portal.gupy.io/api/v1/jobs/companies?limit=1000&sortBy=company&sortOrder=asc&workplaceType=hybrid
*/

@Entity
@Table(name = "companies")
public class Company {

    @Id
    private long id;
    private String companyName;
    private String logoUrl;

    public Company() {}

    public Company (long id, String companyName, String logoUrl) {
        this.id = id;
        this.companyName = companyName;
        this.logoUrl = logoUrl;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getCompanyName() {return companyName;}

    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public String getLogoUrl() {return logoUrl;}

    public void setLogoUrl(String logoUrl) {this.logoUrl = logoUrl;}
}

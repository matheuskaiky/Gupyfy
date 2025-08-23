package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.*;

/* Gupy API for Company searching
   https://employability-portal.gupy.io/api/v1/jobs/companies?limit=1000&sortBy=company&sortOrder=asc&workplaceType=hybrid
*/

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long gupyId;
    private String companyName;
    private String logoUrl;

    public Company() {}

    public Company (long gupyId, String companyName, String logoUrl) {
        this.gupyId = gupyId;
        this.companyName = companyName;
        this.logoUrl = logoUrl;
    }

    public long getId() {return id;}

    public long getGupyId() {return gupyId;}
    public void setGupyId(long gupyId) {this.gupyId = gupyId;}

    public String getCompanyName() {return companyName;}
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public String getLogoUrl() {return logoUrl;}
    public void setLogoUrl(String logoUrl) {this.logoUrl = logoUrl;}
}

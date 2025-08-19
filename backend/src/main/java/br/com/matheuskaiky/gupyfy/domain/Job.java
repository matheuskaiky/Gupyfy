package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.*;

/**
 * Represents a Job entity that will be stored in the database.
 * Each instance of this class corresponds to a row in the 'jobs' table.
 */
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String companyName;

    @Column(unique = true, length = 512)
    private String url;

    public Job() {
    }

    public Job(String title, String companyName, String url) {
        this.title = title;
        this.companyName = companyName;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
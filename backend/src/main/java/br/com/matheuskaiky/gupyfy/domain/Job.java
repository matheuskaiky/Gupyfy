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
    private String jobLevel;
    private String companyName;
    private String workMode;

    @Column(unique = true, length = 512)
    private String url;

    public Job() {
    }

    public Job(long id, String title, String jobLevel,String companyName, String workMode, String url) {
        this.id = id;
        this.title = title;
        this.jobLevel = jobLevel;
        this.companyName = companyName;
        this.workMode = workMode;
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

    public String getJobLevel() {return jobLevel;}

    public void setJobLevel(String jobLevel) {this.jobLevel = jobLevel;}

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
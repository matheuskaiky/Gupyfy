package br.com.matheuskaiky.gupyfy.domain;

import jakarta.persistence.*;

import java.util.Date;

/**
 * Represents a Job entity that will be stored in the database.
 * Each instance of this class corresponds to a row in the 'jobs' table.
 */
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    private Long id;

    private String title;
    private String jobLevel;

    @ManyToOne
    private Company company;
    private String workMode; // "vacancy_type_talent_pool" or "vacancy_type_effective"

    @Temporal(TemporalType.DATE)
    private Date publishedDate;

    @Temporal(TemporalType.DATE)
    private Date deadlineDate;

    @Column(unique = true, length = 512)
    private String url;

    public Job() {
    }

    public Job(long id, String title, String jobLevel,Company company, String workMode,
               Date publishedDate, Date deadlineDate, String url) {
        this.id = id;
        this.title = title;
        this.jobLevel = jobLevel;
        this.company = company;
        this.workMode = workMode;
        this.publishedDate = publishedDate;
        this.deadlineDate = deadlineDate;
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

    public Company getCompanyName() {
        return company;
    }
    public void setCompanyName(Company company) {
        this.company = company;
    }

    public String getWorkMode() {return workMode;}
    public void setWorkMode(String workMode) {this.workMode = workMode;}

    public Date getPublishedDate() {return publishedDate;}
    public void setPublishedDate(Date publishedDate) {this.publishedDate = publishedDate;}

    public Date getDeadlineDate() {return deadlineDate;}
    public void setDeadlineDate(Date deadlineDate) {this.deadlineDate = deadlineDate;}

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
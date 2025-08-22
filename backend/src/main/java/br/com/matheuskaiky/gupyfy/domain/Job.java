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
    private String description;
    private String jobLevel;

    @ManyToOne
    private Company company;
    private String workPlace;

    @Temporal(TemporalType.DATE)
    private Date publishedDate;

    @Temporal(TemporalType.DATE)
    private Date deadlineDate;

    private String jobOfferType; // "vacancy_type_talent_pool" or "vacancy_type_effective"

    @Column(unique = true, length = 512)
    private String url;

    public Job() {
    }

    public Job(long id, String title, String description, String jobLevel, Company company,
               String workPlace, Date publishedDate, Date deadlineDate, String jobOfferType, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.jobLevel = jobLevel;
        this.company = company;
        this.workPlace = workPlace;
        this.publishedDate = publishedDate;
        this.deadlineDate = deadlineDate;
        this.jobOfferType = jobOfferType;
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

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getJobLevel() {return jobLevel;}
    public void setJobLevel(String jobLevel) {this.jobLevel = jobLevel;}

    public Company getCompanyName() {
        return company;
    }
    public void setCompanyName(Company company) {
        this.company = company;
    }

    public String getWorkPlace() {return workPlace;}
    public void setWorkPlace(String workPlace) {this.workPlace = workPlace;}

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
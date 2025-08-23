package br.com.matheuskaiky.gupyfy.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Represents a job entity as stored in the application's database.
 * Each instance of this class corresponds to a single row in the 'jobs' table
 * and contains data aggregated from the Gupy platform.
 */
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gupy_id", nullable = false, unique = true)
    private Long gupyId;
    
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
    private String jobUrl;

    public Job() {
    }

    public Job(long gupyId, String title, String description, String jobLevel, Company company,
               String workPlace, Date publishedDate, Date deadlineDate, String jobOfferType, String jobUrl) {
        this.gupyId = gupyId;
        this.title = title;
        this.description = description;
        this.jobLevel = jobLevel;
        this.company = company;
        this.workPlace = workPlace;
        this.publishedDate = publishedDate;
        this.deadlineDate = deadlineDate;
        this.jobOfferType = jobOfferType;
        this.jobUrl = jobUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getGupyId() {return gupyId;}
    public void setGupyId(long gupyId) {this.gupyId = gupyId;}

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

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    public String getWorkPlace() {return workPlace;}
    public void setWorkPlace(String workPlace) {this.workPlace = workPlace;}

    public Date getPublishedDate() {return publishedDate;}
    public void setPublishedDate(Date publishedDate) {this.publishedDate = publishedDate;}

    public Date getDeadlineDate() {return deadlineDate;}
    public void setDeadlineDate(Date deadlineDate) {this.deadlineDate = deadlineDate;}

    public  String getJobOfferType() {return jobOfferType;}
    public void setJobOfferType(String jobOfferType) {this.jobOfferType = jobOfferType;}

    public String getJobUrl() {
        return jobUrl;
    }
    public void setJobUrl(String url) {
        this.jobUrl = url;
    }
}
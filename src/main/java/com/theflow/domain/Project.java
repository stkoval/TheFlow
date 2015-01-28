package com.theflow.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Type;

/**
 *
 * @author Stas
 */
@Entity
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private int projectId;

    @Column(name = "name")
    private String projName;

    @Column(name = "description")
    private String projDescription;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "start_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;

    @Column(name = "release_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date releaseDate;

    @Column(name = "active", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;

    public Project() {
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjDescription() {
        return projDescription;
    }

    public void setProjDescription(String projDescription) {
        this.projDescription = projDescription;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.projectId;
        hash = 29 * hash + Objects.hashCode(this.projName);
        hash = 29 * hash + Objects.hashCode(this.company);
        return hash;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;
        if (this.projectId != other.projectId) {
            return false;
        }
        if (!Objects.equals(this.projName, other.projName)) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Project{" + "projectId=" + projectId + ", projName=" + projName + ", company=" + company + ", startDate=" + startDate + ", releaseDate=" + releaseDate + ", active=" + active + '}';
    }
}

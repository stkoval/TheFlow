package com.theflow.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @ManyToMany
    @JoinTable(name = "projects_users",
            joinColumns
            = {
                @JoinColumn(name = "project_id")
            },
            inverseJoinColumns
            = {
                @JoinColumn(name = "user_id")
            })
    @JsonBackReference
    private Set<User> addedUsers;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    
    

    public Set<User> getAddedUsers() {
        return addedUsers;
    }

    public void setAddedUsers(Set<User> addedUsers) {
        this.addedUsers = addedUsers;
    }
    
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
}

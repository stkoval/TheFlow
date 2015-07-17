package com.theflow.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Formula;

/**
 *
 * @author Stas
 */
@Entity
@Table(name = "issues")
public class Issue implements Serializable {

    public static enum IssueType {

        TASK("Task"),
        BUG("Bug");

        private final String name;

        private IssueType(String s) {
            name = s;
        }

        public boolean equalsString(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        public static IssueType getEnum(String value) {
            for (IssueType v : values()) {
                if ((v.name).equalsIgnoreCase(value)) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static enum IssueStatus {

        NEW("New"),
        INPROGRESS("In progress"),
        TESTINGREADY("Test ready"),
        TESTING("Testing"),
        REVIEW("Review"),
        CLOSED("Closed"),
        ONHOLD("On hold");

        private final String name;

        private IssueStatus(String s) {
            name = s;
        }

        public boolean equalsString(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }
        
        public static IssueStatus getEnum(String value) {
            for (IssueStatus v : values()) {
                if ((v.name).equalsIgnoreCase(value)) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static enum IssuePriority {

        HIGH("High"),
        MEDIUM("Medium"),
        LOW("Low");

        private final String name;

        private IssuePriority(String s) {
            name = s;
        }

        public boolean equalsString(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }
        
        public static IssuePriority getEnum(String value) {
            for (IssuePriority v : values()) {
                if ((v.name).equalsIgnoreCase(value)) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Id
    @GeneratedValue
    @Column(name = "issue_id")
    private int issueId;
    
    @Column(name = "external_id")
    private String issueExtId;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private IssueType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issue", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<IssueAttachment> attachment;
    
    @JoinColumn(name = "creator_id")
    @ManyToOne
    private User creator;

    @ManyToOne(cascade=CascadeType.ALL)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "estimated_time")
    private String estimatedTime;

    @Column(name = "logged_time")
    private String loggedTime;

    @Column(name = "creation_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;

    @Column(name = "modification_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastModificationDate;
    
    @Column(name = "company_id")
    private int companyId;
    
    public Issue() {
    }

    public String getIssueExtId() {
        return issueExtId;
    }

    public void setIssueExtId(String issueExtId) {
        this.issueExtId = issueExtId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    
    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String IssueDescription) {
        this.description = IssueDescription;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(String loggedTime) {
        this.loggedTime = loggedTime;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Set<IssueAttachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(Set<IssueAttachment> attachment) {
        this.attachment = attachment;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.issueId;
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
        final Issue other = (Issue) obj;
        if (this.issueId != other.issueId) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "Issue{" + "issueId=" + issueId + ", title=" + title + ", type=" + type + ", status=" + status + ", priority=" + priority + ", companyId=" + companyId + '}';
    }
}

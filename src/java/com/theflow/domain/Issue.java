/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Stas
 */
@Entity
@Table(name = "issues")
public class Issue implements Serializable {

    public static enum IssueType {

        TASK, BUG
    }

    public static enum IssueStatus {

        NEW, INPROGRESS, REVIEW, CLOSED
    }

    public static enum IssuePriority {

        HIGH, MEDIUM, LOW
    }

    @Id
    @GeneratedValue
    @Column(name = "issue_id")
    private int issueId;

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
    @JoinColumn(name = "user_id")
    @JoinTable(name = "issues_users",
            joinColumns = {
                @JoinColumn(name = "issue_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "user_id")})
    private User assignee;

    @Column(name = "creator_id")
    private int creatorId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "estimated_time")
    private String estimatedTime;

    @Column(name = "logged_time")
    private String loggedTime;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "modification_date")
    private Date lastModificationDate;

    public Issue() {
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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

}

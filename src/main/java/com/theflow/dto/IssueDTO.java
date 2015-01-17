package com.theflow.dto;

import java.util.Date;

/**
 *
 * @author Stas
 * DTO for Issue object
 */
public class IssueDTO {
    private int issueId;
    private String title;
    private String description;
    private String type;
    private String status;
    private String priority;
    private String assignee_id;
    private String creator_id;
    private String project_id;
    private String estimated_time;
    private String loggedTime;
    private String assigneeFullName;
    private Date creationDate;
    private Date modificationDate;
    
    public IssueDTO() {}

    public IssueDTO(String title, String description, String type, String priority, String assignee_id, String creator_id, String project_id, String estimatedTime) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.assignee_id = assignee_id;
        this.creator_id = creator_id;
        this.project_id = project_id;
        this.estimated_time = estimatedTime;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(String assignee_id) {
        this.assignee_id = assignee_id;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(String loggedTime) {
        this.loggedTime = loggedTime;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationTime() {
        return modificationDate;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationDate = modificationTime;
    }

    public String getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(String estimated_time) {
        this.estimated_time = estimated_time;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getAssigneeFullName() {
        return assigneeFullName;
    }

    public void setAssigneeFullName(String assigneeFullName) {
        this.assigneeFullName = assigneeFullName;
    }
}

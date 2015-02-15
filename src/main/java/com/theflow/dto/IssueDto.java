package com.theflow.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Stas
 * Dto for Issue object
 */
public class IssueDto {
    private Integer issueId;
    
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String title;
    
    @Size(max = 1000)
    private String description;
    private String type;
    private String status;
    private String priority;
    private Integer assigneeId;
    private Integer creatorId;
    
    @NotNull
    private Integer projectId;
    private String estimatedTime;
    private String loggedTime;
    private String assigneeFullName;
    private Date creationDate;
    private Date modificationDate;
    
    public IssueDto() {}

    public IssueDto(String title, String description, String type, String priority, Integer assignee_id, Integer creator_id, Integer project_id, String estimatedTime) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.assigneeId = assignee_id;
        this.creatorId = creator_id;
        this.projectId = project_id;
        this.estimatedTime = estimatedTime;
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

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
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

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}

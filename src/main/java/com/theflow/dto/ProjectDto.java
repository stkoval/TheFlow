package com.theflow.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import validation.ValidProjectAlias;

/**
 *
 * @author Stas
 */
public class ProjectDto {
    
    private int projectId;
    
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30)
    private String projName;
    
    @NotNull
    @NotEmpty
    @Size(max = 6)
    @ValidProjectAlias
    private String projectAlias;
    
    @Size(max = 1000)
    private String projDescription;

    private String startDate;
    
    private String releaseDate;
    
    private boolean active;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    public String getProjectAlias() {
        return projectAlias;
    }

    public void setProjectAlias(String projectAlias) {
        this.projectAlias = projectAlias;
    }

    @Override
    public String toString() {
        return "ProjectDto{" + "projectId=" + projectId + ", projName=" + projName + '}';
    }
}

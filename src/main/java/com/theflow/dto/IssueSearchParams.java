/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dto;

/**
 *
 * @author Stas
 */
public class IssueSearchParams {

    private boolean statusNew;
    private boolean toMe;
    private boolean high;
    private boolean task;
    private boolean bug;
    private boolean all;
    private int projectId;

    public IssueSearchParams(Boolean statusNew, Boolean toMe, Boolean high, Boolean task, Boolean bug, Boolean all, Integer projectId) {
        if (statusNew != null) {
            this.statusNew = statusNew;
        }
        if (toMe != null) {
            this.toMe = toMe;
        }
        if (statusNew != null) {
        }
        if (high != null) {
            this.high = high;
        }
        if (task != null) {
            this.task = task;
        }
        if (bug != null) {
            this.bug = bug;
        }
        if (all != null) {
            this.all = all;
        }
        if (projectId != null) {
            this.projectId = projectId;
        }
    }

    public boolean isStatusNew() {
        return statusNew;
    }

    public void setStatusNew(boolean statusNew) {
        this.statusNew = statusNew;
    }

    public boolean isToMe() {
        return toMe;
    }

    public void setToMe(boolean toMe) {
        this.toMe = toMe;
    }

    public boolean isHigh() {
        return high;
    }

    public void setHigh(boolean high) {
        this.high = high;
    }

    public boolean isTask() {
        return task;
    }

    public void setTask(boolean task) {
        this.task = task;
    }

    public boolean isBug() {
        return bug;
    }

    public void setBug(boolean bug) {
        this.bug = bug;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}

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
public class IssueSearchCriteria {
    private boolean statusNew;
    private boolean toMe;
    private boolean high;
    private boolean task;
    private boolean bug;
    private boolean all;

    public IssueSearchCriteria(boolean statusNew, boolean toMe, boolean high, boolean task, boolean bug, boolean all) {
        this.statusNew = statusNew;
        this.toMe = toMe;
        this.high = high;
        this.task = task;
        this.bug = bug;
        this.all = all;
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
}

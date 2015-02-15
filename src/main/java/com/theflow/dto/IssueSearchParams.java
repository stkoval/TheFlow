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

    public IssueSearchParams(String[] filter, Integer projectId) {
        if (filter != null && filter.length != 0) {
            for (String f : filter) {
                switch (f) {
                    case "new":
                        statusNew = true;
                        break;
                    case "to_me":
                        toMe = true;
                        break;
                    case "high":
                        high = true;
                        break;
                    case "task":
                        task = true;
                        break;
                    case "bug":
                        bug = true;
                        break;
                    case "all":
                        all = true;
                        break;
                }
            }
        }
        if (projectId != null) {
            this.projectId = projectId;
        }
    }

    public boolean isStatusNew() {
        return statusNew;
    }

    public boolean isToMe() {
        return toMe;
    }

    public boolean isHigh() {
        return high;
    }

    public boolean isTask() {
        return task;
    }

    public boolean isBug() {
        return bug;
    }

    public boolean isAll() {
        return all;
    }

    public int getProjectId() {
        return projectId;
    }
}

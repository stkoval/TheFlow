/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Issue;
import com.theflow.dto.IssueSearchParams;
import java.util.List;

/**
 *
 * @author Stas
 */
public interface IssueDao {
    public int saveIssue(Issue issue);
    public void updateIssue(Issue issue);
    public void removeIssue(int id);
    public Issue getIssueById(int issueId);
    public List<Issue> getAllIssues();
    public List<Issue> searchIssues(IssueSearchParams criteria);

    public List<Issue> getIssueByProjectId(int projectId);
}

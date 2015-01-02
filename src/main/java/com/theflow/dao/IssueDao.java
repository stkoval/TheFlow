/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Issue;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.theflow.utils.NVPair;

/**
 *
 * @author Stas
 */
public interface IssueDao {
    public int saveIssue(Issue issue);
    public void updateIssue(Issue issue);
    public void removeIssue(int id);
    public List<Issue> getIssues(List<NVPair> pairs);
    public Issue getIssueById(int issueId);
    public List<Issue> getAllIssues();
}

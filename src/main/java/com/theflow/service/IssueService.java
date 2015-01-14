/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.Issue;
import com.theflow.dto.IssueDTO;
import com.theflow.dto.IssueSearchCriteria;
import java.util.List;

/**
 *
 * @author Stas
 */
public interface IssueService {

    public List<Issue> searchIssues(IssueSearchCriteria criteria);

    public void saveIssue(IssueDTO issueDTO);
    
    public void editIssue(int issue_id, IssueDTO issueDTO);

    public void removeIssue(int id);
    
    public List<Issue> getAllIssues();
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.Issue;
import com.theflow.dto.IssueDto;
import com.theflow.dto.IssueSearchParams;
import java.util.List;

/**
 *
 * @author Stas
 */
public interface IssueService {

    public List<IssueDto> searchIssues(IssueSearchParams criteria);

    public void saveIssue(IssueDto issueDto);
    
    public void removeIssue(int id);
    
    public Issue getIssueById(int id);
    
    public void updateIssue(IssueDto issueDto);
    
    public List<Issue> getAllIssues();
    
    public List<String> getIssueTypes();
    
    public List<String> getIssueStatuses();
    
    public List<String> getIssuePriorities();
    
    public IssueDto populateIssueDtoFildsFromIssue(Issue issue);
    
}

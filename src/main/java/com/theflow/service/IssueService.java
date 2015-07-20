/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.Issue;
import com.theflow.domain.IssueAttachment;
import com.theflow.dto.IssueDto;
import com.theflow.dto.IssueSearchParams;
import java.util.List;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import validation.IssueAttachmentConstraintViolationException;
import validation.ProjectRequiredException;

/**
 *
 * @author Stas
 */
public interface IssueService {

    public List<IssueDto> searchIssues(IssueSearchParams criteria);

    public void saveIssue(IssueDto issueDto) throws ProjectRequiredException;
    
    public void removeIssue(int id);
    
    public Issue getIssueById(int id);
    
    public void updateIssue(IssueDto issueDto);
    
    public void updateIssue(Issue issue);
    
    public List<Issue> getAllIssues();
    
    public List<String> getIssueTypes();
    
    public List<String> getIssueStatuses();
    
    public List<String> getIssuePriorities();
    
    public IssueDto populateIssueDtoFildsFromIssue(Issue issue, int id);

    public void assignToCurrentUser(int sueId);
    
    public List<Issue> getIssuesByProjectId(int projectId);

    public void changeIssueStatus(String status, int id);

    public void changeIssueType(String type, int id);

    public void changeIssueAssignee(int userId, int id);

    public void changeIssuePriority(String priority, int sueId);

    public void uploadAttachment(CommonsMultipartFile[] fileUpload, int issueId) throws IssueAttachmentConstraintViolationException;
}

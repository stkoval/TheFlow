/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.dao.IssueDao;
import com.theflow.dao.ProjectDao;
import com.theflow.dao.UserDao;
import com.theflow.domain.Issue;
import com.theflow.domain.Issue.IssuePriority;
import com.theflow.domain.Issue.IssueStatus;
import com.theflow.domain.Issue.IssueType;
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.dto.IssueDto;
import com.theflow.dto.IssueSearchParams;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validation.ProjectRequiredException;

/**
 *
 * @author Stas
 */
@Service
public class IssueServiceImpl implements IssueService {

    static final Logger logger = Logger.getLogger(IssueService.class.getName());

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Transactional
    @Override
    public List<IssueDto> searchIssues(IssueSearchParams criteria) {
        List<Issue> issues = issueDao.searchIssues(criteria);
        List<IssueDto> issuesDto = new ArrayList<>();

        IssueDto issueDto;
        for (Issue issue : issues) {
            issueDto = new IssueDto();
            issueDto.setIssueId(issue.getIssueId());
            issueDto.setTitle(issue.getTitle());
            issueDto.setType(issue.getType().toString());
            issueDto.setStatus(issue.getStatus().toString());
            issueDto.setPriority(issue.getPriority().toString());
            issueDto.setAssigneeFullName(issue.getAssignee().getFirstName() + " " + issue.getAssignee().getLastName());
            issuesDto.add(issueDto);
        }
        return issuesDto;
    }

    @Transactional
    @Override
    public void saveIssue(IssueDto issueDto) throws ProjectRequiredException{
        if (issueDto.getProjectId() == null || issueDto.getProjectId() == 0) {
            throw new ProjectRequiredException("Please add project first");
        }

        Issue issue = new Issue();
        populateIssueFildsFromDto(issue, issueDto);
        User creator = userDao.getCurrentUser();
        issue.setCreator(creator);
        issue.setStatus(Issue.IssueStatus.NEW);
        FlowUserDetailsService.User principal
                        = (FlowUserDetailsService.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        int companyId = principal.getCompanyId();
        issue.setCompanyId(companyId);

        issueDao.saveIssue(issue);
    }

    @Transactional
    @Override
    public void removeIssue(int id) {
        issueDao.removeIssue(id);
    }

    @Override
    @Transactional
    public List<Issue> getAllIssues() {
        List<Issue> issues = issueDao.getAllIssues();
        return issues;
    }

    private void populateIssueFildsFromDto(Issue issue, IssueDto issueDto) {

        Project project;
        if (issueDto.getProjectId() != null) {
            project = projectDao.getProjectById(issueDto.getProjectId());
            issue.setProject(project);
        }

        //populating issue fields
        Integer assigneeId = issueDto.getAssigneeId();
        if (assigneeId != null) {
            User assignee = userDao.getUserById(assigneeId);
            issue.setAssignee(assignee);
        }
        issue.setDescription(issueDto.getDescription());
        issue.setEstimatedTime(issueDto.getEstimatedTime());
        issue.setLoggedTime(issueDto.getLoggedTime());
        if (issueDto.getPriority() != null && !issueDto.getPriority().isEmpty()) {
            issue.setPriority(Issue.IssuePriority.getEnum(issueDto.getPriority()));
        }
        issue.setTitle(issueDto.getTitle());
        if (issueDto.getType() != null && !issueDto.getType().isEmpty()) {
            issue.setType(Issue.IssueType.getEnum(issueDto.getType()));
        }
        if (issueDto.getStatus() != null && !issueDto.getStatus().isEmpty()) {
            issue.setStatus(Issue.IssueStatus.getEnum(issueDto.getStatus()));
        }
        if (issueDto.getCreatorId() != null) {
            User creator = userDao.getUserById(issueDto.getCreatorId());
            issue.setCreator(creator);
        }
    }

    @Override
    public IssueDto populateIssueDtoFildsFromIssue(Issue issue, int issueId) {
        
        IssueDto issueDto = new IssueDto();
        
        issueDto.setIssueId(issueId);

        issueDto.setProjectId(issue.getProject().getProjectId());

        //populating issueDto fields
        Integer assigneeId = issueDto.getAssigneeId();
        if (issue.getAssignee() != null) {
            issueDto.setAssigneeId(issue.getAssignee().getUserId());
        }
        issueDto.setDescription(issue.getDescription());
        issueDto.setEstimatedTime(issue.getEstimatedTime());
        issueDto.setLoggedTime(issue.getLoggedTime());
        if (issue.getPriority() != null) {
            issueDto.setPriority(issue.getPriority().toString());
        }
        issueDto.setTitle(issue.getTitle());
        if (issue.getType() != null) {
            issueDto.setType(issue.getType().toString());
        }
        if (issue.getStatus() != null) {
            issueDto.setStatus(issue.getStatus().toString());
        }
        issueDto.setCreationDate(issue.getCreationDate());
        issueDto.setModificationDate(issue.getLastModificationDate());

        return issueDto;
    }
    
    //get issue type list for selectitems
    @Override
    public List<String> getIssueTypes() {
        List<String> types = new ArrayList<>();
        for (IssueType type : IssueType.values()) {
            types.add(type.toString());
        }
        return types;
    }

    //get issue statuses list for selectitems
    @Override
    public List<String> getIssueStatuses() {
        List<String> statuses = new ArrayList<>();
        for (IssueStatus status : IssueStatus.values()) {
            statuses.add(status.toString());
        }
        return statuses;
    }

    //get issue priorities list for selectitems
    @Override
    public List<String> getIssuePriorities() {
        List<String> priorities = new ArrayList<>();
        for (IssuePriority priority : IssuePriority.values()) {
            priorities.add(priority.toString());
        }
        return priorities;
    }

    @Override
    @Transactional
    public Issue getIssueById(int id) {
        return issueDao.getIssueById(id);
    }

    @Override
    @Transactional
    public void updateIssue(IssueDto issueDto) {
        Issue issue = issueDao.getIssueById(issueDto.getIssueId());
        populateIssueFildsFromDto(issue, issueDto);
        issue.setIssueId(issueDto.getIssueId());

        issueDao.updateIssue(issue);
    }

    @Override
    @Transactional
    public void assignToCurrentUser(int issueId) {
        Issue issue = issueDao.getIssueById(issueId);
        User current = userDao.getCurrentUser();
        issue.setAssignee(current);
        issueDao.saveIssue(issue);
    }

    @Override
    @Transactional
    public List<Issue> getIssuesByProjectId(int projectId) {
        List<Issue> issues = issueDao.getIssueByProjectId(projectId);
        return issues;
    }

    @Override
    @Transactional
    public void changeIssueStatus(String status, int id) {
        Issue issue = issueDao.getIssueById(id);
        IssueStatus issueStatus = IssueStatus.getEnum(status);
        issue.setStatus(issueStatus);
        issueDao.updateIssue(issue);
    }

    @Override
    @Transactional
    public void changeIssueType(String type, int id) {
        Issue issue = issueDao.getIssueById(id);
        IssueType issueType = IssueType.getEnum(type);
        issue.setType(issueType);
        issueDao.updateIssue(issue);
    }

    @Override
    @Transactional
    public void changeIssueAssignee(int userId, int id) {
        Issue issue = issueDao.getIssueById(id);
        User assignee = userDao.getUserById(userId);
        issue.setAssignee(assignee);
        issueDao.updateIssue(issue);
    }

    @Override
    @Transactional
    public void changeIssuePriority(String priority, int issueId) {
        Issue issue = issueDao.getIssueById(issueId);
        IssuePriority issuePriority = IssuePriority.getEnum(priority);
        issue.setPriority(issuePriority);
        issueDao.updateIssue(issue);
    }

    @Override
    @Transactional
    public void updateIssue(Issue issue) {
        issueDao.updateIssue(issue);
    }
}

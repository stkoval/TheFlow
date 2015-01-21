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
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.dto.IssueDTO;
import com.theflow.dto.IssueSearchParams;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<IssueDTO> searchIssues(IssueSearchParams criteria) {
        List<Issue> issues = issueDao.searchIssues(criteria);
        List<IssueDTO> issuesDTO = new ArrayList<>();

        IssueDTO issueDTO;
        for (Issue issue : issues) {
            issueDTO = new IssueDTO();
            issueDTO.setIssueId(issue.getIssueId());
            issueDTO.setTitle(issue.getTitle());
            issueDTO.setType(issue.getType().name());
            issueDTO.setStatus(issue.getStatus().name());
            issueDTO.setPriority(issue.getPriority().name());
            issueDTO.setAssigneeFullName(issue.getAssignee().getFirstName() + " " + issue.getAssignee().getLastName());
            issuesDTO.add(issueDTO);
        }
        return issuesDTO;
    }

    @Transactional
    @Override
    public void saveIssue(IssueDTO issueDTO) {

        Issue issue = populateIssueFildsFromDTO(issueDTO);

        issueDao.saveIssue(issue);
    }

    @Override
    public void editIssue(int issue_id, IssueDTO issueDTO) {
    }

    @Override
    public void removeIssue(int id) {
    }

    @Override
    @Transactional
    public List<Issue> getAllIssues() {
        List<Issue> issues = issueDao.getAllIssues();
        return issues;
    }

    private Issue populateIssueFildsFromDTO(IssueDTO issueDTO) {

        Issue issue = new Issue();

        Project project = projectDao.getProject(Integer.parseInt(issueDTO.getProject_id()));

        //populating issue fields
        String assigneeIdStr = issueDTO.getAssignee_id();
        if (assigneeIdStr != null && !assigneeIdStr.isEmpty()) {
            User assignee = userDao.getUserById(Integer.parseInt(assigneeIdStr));
            issue.setAssignee(assignee);
        }
        User creator = userDao.getCurrentUser();
        issue.setCreator(creator);
        issue.setDescription(issueDTO.getDescription());
        issue.setEstimatedTime(issueDTO.getEstimated_time());
        issue.setLoggedTime(issueDTO.getLoggedTime());
        if (issueDTO.getPriority() != null && !issueDTO.getPriority().isEmpty()) {
            issue.setPriority(Issue.IssuePriority.valueOf(issueDTO.getPriority()));
        }
        issue.setProject(project);
        issue.setTitle(issueDTO.getTitle());
        if (!issueDTO.getType().equals("")) {
            issue.setType(Issue.IssueType.valueOf(issueDTO.getType()));
        }
        issue.setStatus(Issue.IssueStatus.NEW);

        return issue;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theflow.dao.IssueDao;
import com.theflow.dao.ProjectDao;
import com.theflow.dao.UserDao;
import com.theflow.domain.Issue;
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.dto.IssueDTO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.theflow.utils.IssueCriteriaParser;
import com.theflow.utils.NVPair;
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
    public String searchIssues(String criteria) throws JsonProcessingException {
        List<NVPair> params = IssueCriteriaParser.parse(criteria);
        List<Issue> issues = issueDao.getIssues(params);
        ObjectMapper mapper = new ObjectMapper();
        String jsonIssue = mapper.writeValueAsString(issues);
        return jsonIssue;
    }

    @Override
    public void saveIssue(IssueDTO issueDTO) {
        logger.debug("***********inside Issue Service***********");
        Issue issue = new Issue();
        Project project = projectDao.getProject(Integer.parseInt(issueDTO.getProject_id()));
        logger.debug("***********inside Issue Service***********project name: " + project.getProjName());

        //populating issue fields
        String assigneeIdStr = issueDTO.getAssignee_id();
        if (!assigneeIdStr.equals("")) {
            User assignee = userDao.getUser(Integer.parseInt(assigneeIdStr));
            issue.setAssignee(assignee);
        }
        issue.setCreatorId(Integer.parseInt(issueDTO.getCreator_id()));
        issue.setDescription(issueDTO.getDescription());
        issue.setEstimatedTime(issueDTO.getEstimatedTime());
        issue.setLoggedTime(issueDTO.getLoggedTime());
        if (!issueDTO.getPriority().equals("")) {
            issue.setPriority(Issue.IssuePriority.valueOf(issueDTO.getPriority()));
        }
        issue.setProject(project);
        issue.setTitle(issueDTO.getTitle());
        if (!issueDTO.getType().equals("")) {
            issue.setType(Issue.IssueType.valueOf(issueDTO.getType()));
        }
        issue.setStatus(Issue.IssueStatus.NEW);

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
//        List<IssueDTO> issuesDTO = new ArrayList<>();

//        IssueDTO issueDTO;
//        for (Issue issue : issues) {
//            issueDTO = new IssueDTO();
//            issueDTO.setTitle(issue.getTitle());
//            issueDTO.setIssueId(issue.getIssueId());
//            issuesDTO.add(issueDTO);
//        }
        
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonIssue = mapper.writeValueAsString(issuesDTO);
        return issues;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.theflow.domain.Issue;
import com.theflow.dto.IssueDTO;
import com.theflow.service.IssueService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Stas
 */
@Controller
public class IssueController {

    //initializing the logger
    static final Logger logger = Logger.getLogger(IssueController.class.getName());

    @Autowired
    private IssueService issueService;

    //searching issue by string pair:value criteria
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "issues/search")
    public String searchIssues(@RequestParam(value = "issue_criteria") String criteria) throws JsonProcessingException {
        String jsonIssue = issueService.searchIssues(criteria);
        return jsonIssue;
    }

    //creating new issue
    @RequestMapping(method = RequestMethod.POST, value = "/issues/create")
    public void saveIssue(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("type") String type,
            @RequestParam("status") String status,
            @RequestParam("priority") String priority,
            @RequestParam("assignee_id") String assigneeId,
            @RequestParam("creator_id") String creatorId,
            @RequestParam("project_id") String projectId,
            @RequestParam("estimated_time") String estimatedTime) {

        logger.debug("************ inside controller*********priority: " + priority + "*************");
        IssueDTO issueDTO = new IssueDTO(title, description, type, priority, assigneeId, creatorId, projectId, estimatedTime);

        issueService.saveIssue(issueDTO);
        System.out.println("successfully added issue");
    }

    //show issue creation page
    @RequestMapping("/issues/add")
    public String addIssue() {
        return "redirect:/pages/addissue.html";
    }

    //removes issue from database
    @RequestMapping("/issues/remove")
    public void removeIssue(int id) {
        issueService.removeIssue(id);
    }

    @RequestMapping(value = "/issues/all", method = RequestMethod.GET)
    public ModelAndView getAllIssues() {
        List<Issue> issues = issueService.getAllIssues();
        logger.debug("*************issues*********" + issues.size());
        ModelAndView model = new ModelAndView("issue/table");
        model.addObject("issues", issues);
        return model;
    }

}

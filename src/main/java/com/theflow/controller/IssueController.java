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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @RequestMapping(value = "issue/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute(value = "issue") IssueDTO issueDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "issue/addissue";
        }
        
        issueService.saveIssue(issueDTO);
        
        return"home/home";
    }

    //show issue creation page
    @RequestMapping("issue/add")
    public ModelAndView addIssueForm() {
        return new ModelAndView("issue/addissue", "issue", new IssueDTO());
    }

    //removes issue from database
    @RequestMapping("issue/remove/{id}")
    public void removeIssue(@RequestParam int id) {
        issueService.removeIssue(id);
    }

    //get all issues related to Company
    @RequestMapping(value = "issue/all", method = RequestMethod.GET)
    public ModelAndView getAllIssues() {
        ModelAndView model = new ModelAndView("issue/table");
        List<Issue> issues = issueService.getAllIssues();
        if (issues == null) {
            String message = "There are no issues created";
            model.addObject("message", message);
            return model;
        }
        logger.debug("*************issues*********" + issues.size());
        model.addObject("issues", issues);
        return model;
    }

}

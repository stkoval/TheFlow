/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.dao.IssueDao;
import com.theflow.dao.ProjectDao;
import com.theflow.domain.Issue;
import com.theflow.domain.Project;
import com.theflow.dto.UserDto;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Stas
 */

@Controller
public class LoginController {
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private IssueDao issueDao;
    
    static final Logger logger = Logger.getLogger(LoginController.class.getName());
    
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView redirect() {
        ModelAndView model = new ModelAndView("home/home");

        //populating project list in the left sidebar
        List<Project> projects = projectDao.getProjectList();
        model.addObject("projects", projects);
        
        //populating issue table
        List<Issue> issues = issueDao.getAllIssues();
        model.addObject("issues", issues);
        
        return model;
    }
    
    @RequestMapping(value = "/403", method = RequestMethod.POST)
    public String denied() {

        return "403";
    }
    
    
    @RequestMapping(value = "user/registration", method = RequestMethod.GET)
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("user/add", "user", new UserDto());
    }
}

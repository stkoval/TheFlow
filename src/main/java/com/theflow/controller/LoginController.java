/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import static com.theflow.controller.IssueController.logger;
import com.theflow.domain.Issue;
import com.theflow.domain.Project;
import com.theflow.service.IssueService;
import com.theflow.service.ProjectService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    private ProjectService projectService;
    
    @Autowired
    private IssueService issueService;
    
    static final Logger logger = Logger.getLogger(LoginController.class.getName());
    
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView redirect() {
        ModelAndView model = new ModelAndView("home/home");

        //populating project list in the left sidebar
        List<Project> projects = projectService.getProjectList();
        model.addObject("projects", projects);
        
        //populating issue table
        List<Issue> issues = issueService.getAllIssues();
        model.addObject("issues", issues);
        
        return model;
    }
    
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String denied() {

        return "/signin/403";
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " exception " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error/error");
        return mav;
    }
}

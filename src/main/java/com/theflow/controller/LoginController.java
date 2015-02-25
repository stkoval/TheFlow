/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.domain.Issue;
import com.theflow.domain.Project;
import com.theflow.service.IssueService;
import com.theflow.service.ProjectService;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    
    @Autowired
    MessageSource messageSource;
    
    static final Logger logger = Logger.getLogger(LoginController.class.getName());
    
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView showHomePage() {
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
    public ModelAndView accessDenied(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/signin/403");
        model.addObject("url", request.getRequestURL());

        return model;
    }
    
    @RequestMapping(value = "/login*", method = RequestMethod.GET)
    public ModelAndView showLoginPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("signin/login");
        
        //This section destroys issue serch cookie
        Cookie filterFlow = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("filterFlow")) {
                cookie.setMaxAge(0);
                filterFlow = cookie;
                break;
            }
        }
        if (filterFlow != null) {
            response.addCookie(filterFlow);
            model.addObject("message", messageSource.getMessage("message.sessionExpired", null, Locale.ENGLISH));
        }
        
        return model;
        
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.configuration.SubdomainMapping;
import com.theflow.domain.Issue;
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.service.CompanyService;
import com.theflow.service.IssueService;
import com.theflow.service.ProjectService;
import com.theflow.service.UserService;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    private UserService userService;
    
    @Autowired
    private CompanyService companyService;

    @Autowired
    MessageSource messageSource;

    static final Logger logger = Logger.getLogger(LoginController.class.getName());

//    @SubdomainMapping(value = "demo")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        ModelAndView model = new ModelAndView("/home/home");

        //populating project list in the left sidebar
        List<Project> projects = projectService.getProjectList();
        model.addObject("projects", projects);

        //populating issue table
        List<Issue> issues = issueService.getAllIssues();
        model.addObject("issues", issues);

        List<User> users = userService.getAllUsers();
        model.addObject("users", users);

        return model;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/signin/403");
        model.addObject("url", request.getRequestURL());

        return model;
    }

    @SubdomainMapping(value = "demo", tld = "")
    @RequestMapping(value = "/{company}/login*", method = RequestMethod.GET)
    public ModelAndView showLoginPage(@PathVariable(value = "company") String companyName,
            HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("signin/login");

        int companyId = companyService.getCompanyById().getCompanyId();
        //This section destroys issue serch cookie
        Cookie filterFlow = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("filterFlow")) {
                    cookie.setMaxAge(0);
                    filterFlow = cookie;
                }
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

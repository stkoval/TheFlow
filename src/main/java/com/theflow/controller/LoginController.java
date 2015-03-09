/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.domain.Issue;
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.service.CompanyService;
import com.theflow.service.IssueService;
import com.theflow.service.ProjectService;
import com.theflow.service.UserService;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize(value = "isAuthenticated()")
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

    @RequestMapping(value = "/{subdomain}/login", method = RequestMethod.GET)
    public ModelAndView showLoginPage(@PathVariable(value = "subdomain") String companyAlias,
            HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        logger.debug("**********inside login controller********company: " + companyAlias);

        boolean isSubdomain = companyService.checkIfPartOfPathSubdomain(companyAlias);

        if (!isSubdomain) {
            ModelAndView m = new ModelAndView("redirect:/index");
            m.addObject("message", "invalid url");
            return m;
        }

        //This section destroys issue serch cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length
                != 0) {

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("filterFlow")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                if (cookie.getName().equals("subdomain")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        ModelAndView model = new ModelAndView("signin/login");

        session.setAttribute(
                "subdomain", companyAlias);

        model.addObject(
                "companyAlias", "democompany1");
        logger.debug(
                "**********inside login controller********companyAlias: " + companyAlias);
        return model;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView showLandingPage() {
        ModelAndView model = new ModelAndView("/home/landing");
        return model;
    }

//    @RequestMapping(value = "/{path}", method = RequestMethod.GET)
//    public ModelAndView redirectToLogin1(@PathVariable(value = "path") String path) {
//        boolean isSubdomain = companyService.checkIfPartOfPathSubdomain(path);
//        if (isSubdomain) {
//            ModelAndView model = new ModelAndView("redirect:/" + path + "/login");
//            return model;
//        } else {
//            return new ModelAndView("redirect:/path");
//        }
//    }
//
//    @RequestMapping(value = "/{company}/", method = RequestMethod.GET)
//    public ModelAndView redirectToLogin2(@PathVariable(value = "company") String companyName) {
//        ModelAndView model = new ModelAndView("redirect:/" + companyName + "/login");
//        return model;
//    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, HibernateException exception) {
        logger.error("Request: " + req.getRequestURL() + " exception " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("/error/error");
        return mav;
    }
}

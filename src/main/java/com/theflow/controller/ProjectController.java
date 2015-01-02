/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.theflow.service.ProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Stas
 */
@Controller
public class ProjectController {
    
    static final Logger logger = Logger.getLogger(ProjectController.class.getName());
    
    @Autowired
    private ProjectService projectService;
    
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value="/projects/getAddedUsers")
    public String getAssigneeList(@RequestParam(value = "issue_criteria")int projectId) throws JsonProcessingException {
        logger.debug("***********in project controller********id " + projectId);
        return projectService.getAssigneeList(projectId);
    } 
}

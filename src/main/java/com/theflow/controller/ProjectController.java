/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.domain.Project;
import com.theflow.service.ProjectService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Stas
 */
@Controller
public class ProjectController {
    
    static final Logger logger = Logger.getLogger(ProjectController.class.getName());
    
    @Autowired
    private ProjectService projectService;
    
    @RequestMapping(value = "project/list")
    public ModelAndView getProjectList() {
        List<Project> projects = projectService.getProjectList();
        ModelAndView model = new ModelAndView("project/list");
        model.addObject("projects", projects);
        return model;
    }
}

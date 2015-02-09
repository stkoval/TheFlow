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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "projects/manage", method = RequestMethod.GET)
    public ModelAndView showManageProjectsPage() {
        ModelAndView model = new ModelAndView("project/manage");
        List<Project> projects = projectService.getProjectList();
        
        model.addObject("projects", projects);
        
        return model;
    }
    
    @RequestMapping("project/add")
    public ModelAndView addProjectForm() {
        ModelAndView model = new ModelAndView("issue/addissue", "project", new Project());

        return model;
    }
    
    @RequestMapping(value = "project/save", method = RequestMethod.POST)
    public ModelAndView saveProject(@ModelAttribute(value = "project") Project project, BindingResult result) {

        projectService.saveProject(project);

        return new ModelAndView("redirect: manage");
    }
    
     @RequestMapping(value = "project/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeProject(@PathVariable int id) {
        projectService.removeProject(id);
        return new ModelAndView("redirect:../manage");
    }
    
    @RequestMapping(value = "project/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editProject(@PathVariable int id) {

        ModelAndView model = new ModelAndView("project/edit");
        Project project = projectService.getProjectById(id);
        
        return model;
    }
    
    @RequestMapping(value = "project/update", method = RequestMethod.POST)
    public ModelAndView updateProject(@ModelAttribute(value = "project") Project project, BindingResult result) {
        
        projectService.updateProject(project);
        return new ModelAndView("redirect: manage");
    }
}

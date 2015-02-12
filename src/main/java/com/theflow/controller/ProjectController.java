/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.domain.Project;
import com.theflow.dto.ProjectDto;
import com.theflow.service.ProjectService;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import validation.ProjectNameExistsException;

/**
 *
 * @author Stas
 */
@Controller
public class ProjectController {

    static final Logger logger = Logger.getLogger(ProjectController.class.getName());

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "project/list")
    public ModelAndView getProjectList() {
        List<Project> projects = projectService.getProjectList();
        ModelAndView model = new ModelAndView("project/list");
        model.addObject("projects", projects);
        return model;
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "projects/manage", method = RequestMethod.GET)
    public ModelAndView showManageProjectsPage() {
        ModelAndView model = new ModelAndView("project/manage");
        List<Project> projects = projectService.getProjectList();

        model.addObject("projects", projects);

        return model;
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping("project/add")
    public ModelAndView addProjectForm() {
        ModelAndView model = new ModelAndView("project/addproject", "project", new Project());

        return model;
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "project/save", method = RequestMethod.POST)
    public ModelAndView saveProject(@ModelAttribute(value = "project") @Valid ProjectDto projectDto, BindingResult result) {
        
        logger.debug("Adding new project with information: {}" + projectDto);
        if (result.hasErrors()) {
            return new ModelAndView("project/addproject", "project", projectDto);
        }

        logger.debug("No validation errors found. Continuing adding new project.");
        
        String projectSavingStatus = saveNewProjectStatus(projectDto);

        if (projectSavingStatus.equals("nameExsists")) {
            result.rejectValue("projName", "message.projectNameError");
            return new ModelAndView("/project/addproject", "project", projectDto);
        }
        
        ModelAndView model = new ModelAndView("/projects/manage");
        model.addObject("message", messageSource.getMessage("label.successAddedProject.title", null, Locale.ENGLISH) + projectDto.getProjName());
        return new ModelAndView("redirect:/projects/manage");
    }
    
    private String saveNewProjectStatus(ProjectDto projectDto) {
        try {
            projectService.saveProject(projectDto);
        } catch (ProjectNameExistsException e) {
            return "nameExsists";
        }
        return "success";
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "project/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeProject(@PathVariable int id) {
        projectService.removeProject(id);
        return new ModelAndView("redirect:/projects/manage");
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "project/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editProject(@PathVariable int id) {

        ModelAndView model = new ModelAndView("project/edit");
        Project project = projectService.getProjectById(id);
        model.addObject("project", project);

        return model;
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "project/update", method = RequestMethod.POST)
    public ModelAndView updateProject(@ModelAttribute(value = "project") ProjectDto projectDto, BindingResult result) {

        projectService.updateProject(projectDto);
//        return new ModelAndView("redirect:/projects/manage");
        return null;
    }
    
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "project/details/{id}", method = RequestMethod.GET)
    public ModelAndView showProjectDetails(@PathVariable int id) {
        Project project = projectService.getProjectById(id);
        ModelAndView model = new ModelAndView("project/details");
        model.addObject("project", project);
        return model;
    }
}

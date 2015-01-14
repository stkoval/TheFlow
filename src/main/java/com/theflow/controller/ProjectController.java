/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.service.ProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Stas
 */
@Controller
public class ProjectController {
    
    static final Logger logger = Logger.getLogger(ProjectController.class.getName());
    
    @Autowired
    private ProjectService projectService;
}

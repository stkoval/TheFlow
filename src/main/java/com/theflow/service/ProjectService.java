/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.Project;
import com.theflow.dto.ProjectDto;
import java.util.List;
import validation.ProjectNameExistsException;


/**
 *
 * @author Stas
 */
public interface ProjectService {

    public List<Project> getProjectList();

    public void saveProject(ProjectDto project)  throws ProjectNameExistsException;

    public void removeProject(int id);

    public Project getProjectById(int id);

    public void updateProject(ProjectDto project);
}

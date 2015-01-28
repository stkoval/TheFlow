/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Project;
import java.util.List;

/**
 *
 * @author Stas
 */
public interface ProjectDao {
    
    public void saveProject(Project project);
    
    public void updateProject(Project project);
    
    public void removeProject(int id);
    
    public Project getProjectById(int id);

    public List<Project> getProjectList();
}

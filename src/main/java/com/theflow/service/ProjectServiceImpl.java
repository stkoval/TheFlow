/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.dao.CompanyDao;
import com.theflow.dao.ProjectDao;
import com.theflow.domain.Company;
import com.theflow.domain.Project;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Stas
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Project> getProjectList() {
        return projectDao.getProjectList();
    }

    @Override
    public void saveProject(Project project) {
        FlowUserDetailsService.User principal
                        = (FlowUserDetailsService.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        int companyId = principal.getCompanyId();
        Company currentCompany  = companyDao.getCompanyById(companyId);
        project.setCompany(currentCompany);
        projectDao.saveProject(project);
    }

    @Override
    public void removeProject(int id) {
        projectDao.removeProject(id);
    }

    @Override
    public Project getProjectById(int id) {
        return projectDao.getProjectById(id);
    }

    @Override
    public void updateProject(Project project) {
        projectDao.updateProject(project);
    }
}

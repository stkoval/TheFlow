/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.dao.ProjectDao;
import com.theflow.domain.Project;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<Project> getProjectList() {
        return projectDao.getProjectList();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Project;
import com.theflow.service.FlowUserDetailsService;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stas
 */
@Repository("projectDao")
public class ProjectDaoImpl implements ProjectDao{
    
    static final Logger logger = Logger.getLogger(ProjectDao.class.getName());
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveProject(Project project) {
    }

    @Override
    public void updateProject(Project project) {
    }

    @Override
    public void removeProject(int id) {
    }

    @Override
    public Project getProject(int id) {
        return (Project)sessionFactory.getCurrentSession().get(Project.class, id);
    }

    @Override
    public List<Project> getProjectList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Project.class);
        FlowUserDetailsService.User principal = (FlowUserDetailsService.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cr.add(Restrictions.eq("company.id", principal.getCompanyId()));
        return (List<Project>)cr.list();
    }
    
}

package com.theflow.dao;

import com.theflow.domain.Project;
import com.theflow.service.FlowUserDetailsService;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
public class ProjectDaoImpl implements ProjectDao {

    static final Logger logger = Logger.getLogger(ProjectDao.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveProject(Project project) {
        sessionFactory.getCurrentSession().save(project);
    }

    @Override
    public void updateProject(Project project) {
        sessionFactory.getCurrentSession().update(project);
    }

    @Override
    public void removeProject(int id) {
        Session session = sessionFactory.getCurrentSession();
        Project p = (Project) session.get(Project.class, id);
//        String hql = "delete from Project where projectId = :projectId";
//        Query q = session.createQuery(hql);
//        q.setParameter("projectId", id);
//        q.executeUpdate();
        session.delete(p);
    }

    @Override
    public Project getProjectById(int id) {
        return (Project) sessionFactory.getCurrentSession().get(Project.class, id);
    }

    @Override
    public List<Project> getProjectList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Project.class);
        FlowUserDetailsService.User principal = (FlowUserDetailsService.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cr.add(Restrictions.eq("company.id", principal.getCompanyId()));
        return (List<Project>) cr.list();
    }

    @Override
    public Project findByName(String projName) {
        List<Project> projects = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Project where projName = :name";
        Query q = session.createQuery(hql);
        q.setParameter("name", projName);
        projects = q.list();
        if (projects.size() > 0) {
            return projects.get(0);
        } else {
            return null;
        }
    }
}

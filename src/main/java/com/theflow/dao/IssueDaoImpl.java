package com.theflow.dao;

import helpers.IssueConstraints;
import com.theflow.domain.Issue;
import com.theflow.domain.Issue.IssuePriority;
import com.theflow.domain.Issue.IssueStatus;
import com.theflow.domain.Issue.IssueType;
import com.theflow.domain.Project;
import com.theflow.dto.IssueSearchParams;
import com.theflow.service.FlowUserDetailsService;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Subqueries;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Stas
 */
@Repository("issueDao")
public class IssueDaoImpl implements IssueDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public int saveIssue(Issue issue) {
        return (int) sessionFactory.getCurrentSession().save(issue);
    }
    
    @Override
    public void updateIssue(Issue issue) {
        Session session = sessionFactory.getCurrentSession();
        session.update(issue);
    }
    
    @Override
    public void removeIssue(int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "delete from Issue where issueId = :issueId";
        Query q = session.createQuery(hql);
        q.setParameter("issueId", id);
        q.executeUpdate();
    }
    
    @Override
    public Issue getIssueById(int issueId) {
        
        return (Issue) sessionFactory.getCurrentSession().get(Issue.class, issueId);
    }
    
    @Override
    public List<Issue> getAllIssues() {
        FlowUserDetailsService.User principal
                        = (FlowUserDetailsService.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        
        Session session = sessionFactory.getCurrentSession();

        //getting all projects related to company
        List<Integer> projectIds = session.createSQLQuery("select project_id from projects where company_id = " + principal.getCompanyId()).list();
        
        if (projectIds.isEmpty()) {
            return null;
        }

        //getting issues related to projects user is added to
        String hql1 = "from Issue where project.projectId in (:projects)";
        Query q1 = session.createQuery(hql1);
        q1.setParameterList("projects", projectIds);
        List<Issue> issues = (List<Issue>) q1.list();
        return issues;
    }
    
    @Override
    public List<Issue> searchIssues(IssueSearchParams issueSearchParams) {
        Session session = sessionFactory.getCurrentSession();
        FlowUserDetailsService.User principal
                        = (FlowUserDetailsService.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        Criteria cr;
        if (issueSearchParams.isAll()) {
            return getAllIssues();
        } else {
            cr = session.createCriteria(Issue.class);
            if (issueSearchParams.isTask() && issueSearchParams.isBug()) {
                Criterion bugs = Restrictions.eq(IssueConstraints.TYPE, IssueType.BUG);
                Criterion tasks = Restrictions.eq(IssueConstraints.TYPE, IssueType.TASK);
                cr.add(Restrictions.or(tasks, bugs));
            } else {
                if (issueSearchParams.isBug()) {
                    cr.add(Restrictions.eq(IssueConstraints.TYPE, IssueType.BUG));
                }
                if (issueSearchParams.isTask()) {
                    cr.add(Restrictions.eq(IssueConstraints.TYPE, IssueType.TASK));
                }
            }
            if (issueSearchParams.isHigh()) {
                cr.add(Restrictions.eq(IssueConstraints.PRIORITY, IssuePriority.HIGH));
            }
            if (issueSearchParams.isStatusNew()) {
                cr.add(Restrictions.eq(IssueConstraints.STATUS, IssueStatus.NEW));
            }
            if (issueSearchParams.isToMe()) {
                cr.add(Restrictions.eq("assignee.userId", principal.getUserId()));
            }
            if (issueSearchParams.getProjectId() != 0) {
                cr.add(Restrictions.eq("project.projectId", issueSearchParams.getProjectId()));
            } else {
                int companyId = principal.getCompanyId();
                DetachedCriteria projects = DetachedCriteria.forClass(Project.class)
                        .setProjection(Property.forName("projectId"))
                        .add(Restrictions.eq("company.companyId", companyId));
                cr.add(Subqueries.propertyIn("project.projectId", projects));
            }
        }
        return (List<Issue>) cr.list();
    }

    @Override
    public List<Issue> getIssueByProjectId(int projectId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Issue i where i.project.projectId = :projectId";
        Query q = session.createQuery(hql);
        q.setParameter("projectId", projectId);
        return q.list();
    }
}

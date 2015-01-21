package com.theflow.dao;

import com.theflow.domain.Issue;
import com.theflow.domain.Issue.IssuePriority;
import com.theflow.domain.Issue.IssueStatus;
import com.theflow.domain.Issue.IssueType;
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.dto.IssueSearchParams;
import com.theflow.service.FlowUserDetailsService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.theflow.utils.NVPair;
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
    
    static final Logger logger = Logger.getLogger(IssueDao.class.getName());
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private CompanyDao companyDao;
    
    @Override
    public int saveIssue(Issue issue) {
        logger.debug("*************inside Issue Dao*********saving issue*********issue title: " + issue.getTitle());
        return (int) sessionFactory.openSession().save(issue);
    }
    
    @Override
    public void updateIssue(Issue issue) {
        sessionFactory.openSession().update(issue);
    }
    
    @Override
    public void removeIssue(int id) {
    }
    
    @Override
    public List<Issue> getIssues(List<NVPair> pairs) {
        List<Issue> list = new ArrayList<>();
        
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Issue.class);
        
        for (NVPair pair : pairs) {
            String name = (String) pair.getLeft();
            Object value = pair.getRight();
            if (name.equals(IssueConstraints.CREATOR_ID) || name.equals(IssueConstraints.ASSIGNEE_ID)
                    || name.equals(IssueConstraints.PROJECT_ID) || name.equals(IssueConstraints.ID)) {
                criteria.add(Restrictions.eq(name, (int) value));
            } else if (name.equals(IssueConstraints.STATUS)) {
                IssueStatus enumVal = IssueStatus.valueOf((String) value);
                switch (enumVal) {
                    case NEW:
                        criteria.add(Restrictions.eq(name, IssueStatus.NEW));
                        break;
                    case INPROGRESS:
                        criteria.add(Restrictions.eq(name, IssueStatus.INPROGRESS));
                        break;
                    case REVIEW:
                        criteria.add(Restrictions.eq(name, IssueStatus.REVIEW));
                        break;
                    case RESOLVED:
                        criteria.add(Restrictions.eq(name, IssueStatus.RESOLVED));
                        break;
                }
            } else if (name.equals(IssueConstraints.TYPE)) {
                IssueType enumVal = IssueType.valueOf((String) value);
                switch (enumVal) {
                    case BUG:
                        criteria.add(Restrictions.eq(name, IssueType.BUG));
                        break;
                    case TASK:
                        criteria.add(Restrictions.eq(name, IssueType.TASK));
                        break;
                }
            } else if (name.equals(IssueConstraints.PRIORITY)) {
                IssuePriority enumVal = IssuePriority.valueOf((String) value);
                switch (enumVal) {
                    case HIGH:
                        criteria.add(Restrictions.eq(name, IssuePriority.HIGH));
                        break;
                    case MEDIUM:
                        criteria.add(Restrictions.eq(name, IssuePriority.MEDIUM));
                        break;
                    case LOW:
                        criteria.add(Restrictions.eq(name, IssuePriority.LOW));
                        break;
                }
            } else if (name.equals(IssueConstraints.CREATION_DATE) || name.equals(IssueConstraints.MODIFICATION_DATE)) {
                criteria.add(Restrictions.eq(name, (Date) value));
            } else if (name.equals(IssueConstraints.MODIFICATION_DATE_FROM)) {
                criteria.add(Restrictions.ge(IssueConstraints.MODIFICATION_DATE, (Date) value));
            } else if (name.equals(IssueConstraints.CREATION_DATE_FROM)) {
                criteria.add(Restrictions.ge(IssueConstraints.CREATION_DATE, (Date) value));
            } else if (name.equals(IssueConstraints.MODIFICATION_DATE_TO) || name.equals(IssueConstraints.CREATION_DATE_TO)) {
                criteria.add(Restrictions.le(IssueConstraints.MODIFICATION_DATE, (Date) value));
            } else if (name.equals(IssueConstraints.CREATION_DATE_TO)) {
                criteria.add(Restrictions.le(IssueConstraints.CREATION_DATE, (Date) value));
            }
        }
        return criteria.list();
    }
    
    @Override
    public Issue getIssueById(int issueId) {
        
        return (Issue) sessionFactory.openSession().get(Issue.class, issueId);
    }
    
    @Override
    public List<Issue> getAllIssues() {
        
        Session session = sessionFactory.openSession();

        //Current authenticated user
        User user = userDao.getCurrentUser();

        //getting all projects related to company
        List<Integer> projectIds = session.createSQLQuery("select project_id from projects where company_id = " + user.getCompany().getCompanyId()).list();
        
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
        Session session = sessionFactory.openSession();
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
                User current = userDao.getCurrentUser();
                cr.add(Restrictions.eq(IssueConstraints.ASSIGNEE, current));
            }
            if (issueSearchParams.getProjectId() != 0) {
                cr.add(Restrictions.eq("project.projectId", issueSearchParams.getProjectId()));
            } else {
                FlowUserDetailsService.User principal
                        = (FlowUserDetailsService.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
                int companyId = principal.getCompanyId();
                DetachedCriteria projects = DetachedCriteria.forClass(Project.class)
                        .setProjection(Property.forName("projectId"))
                        .add(Restrictions.eq("company", companyDao.getCompanyById(companyId)));
                cr.add(Subqueries.propertyIn("project.projectId", projects));
            }
        }
        return (List<Issue>) cr.list();
    }
}

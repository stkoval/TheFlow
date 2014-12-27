/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Issue;
import com.theflow.domain.Issue.IssuePriority;
import com.theflow.domain.Issue.IssueStatus;
import com.theflow.domain.Issue.IssueType;
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
import utils.NVPair;

/**
 *
 * @author Stas
 */
@Repository("issueDao")
public class IssueDaoImpl implements IssueDao {
    
    static final Logger logger = Logger.getLogger(IssueDao.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

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
                    case CLOSED:
                        criteria.add(Restrictions.eq(name, IssueStatus.CLOSED));
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
        
        return (Issue)sessionFactory.openSession().get(Issue.class, issueId);
    }

    @Override
    public List<Issue> getAllIssues() {
        return sessionFactory.openSession().createCriteria(Issue.class).list();
    }

}

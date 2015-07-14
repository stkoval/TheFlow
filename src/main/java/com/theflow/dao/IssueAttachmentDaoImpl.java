package com.theflow.dao;

import com.theflow.domain.IssueAttachment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stas
 */
@Repository("issueAttachmentDao")
public class IssueAttachmentDaoImpl implements IssueAttachmentDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public IssueAttachment getIssueAttachmentById(int attachId) {
        return (IssueAttachment) sessionFactory.getCurrentSession().get(IssueAttachment.class, attachId);
    }

    @Override
    public void removeIssueAttachment(int attachId) {
        Session session = sessionFactory.getCurrentSession();
        String hql1 = "delete from IssueAttachment where id = :attachId";
        Query q1 = session.createQuery(hql1);
        q1.setParameter("attachId", attachId);
        q1.executeUpdate();
    }
    
}

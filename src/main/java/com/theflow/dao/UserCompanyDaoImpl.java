package com.theflow.dao;

import com.theflow.domain.User;
import com.theflow.domain.UserCompany;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stas
 */
@Repository("userCompanyDao")
public class UserCompanyDaoImpl implements UserCompanyDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveUserCompany(UserCompany userCompany) {
        Session session = sessionFactory.getCurrentSession();
        session.save(userCompany);
    }

    @Override
    public void updateUserCompany(UserCompany userCompany) {
        Session session = sessionFactory.getCurrentSession();
        session.update(userCompany);
    }

    @Override
    public List<User> getUsersByCompanyId(int companyId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select uc.user from UserCompany uc where uc.company.companyId = :companyId";
        Query q = session.createQuery(hql);
        q.setParameter("companyId", companyId);
        return  q.list();
    }
}

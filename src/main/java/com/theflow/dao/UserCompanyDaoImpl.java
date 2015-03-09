package com.theflow.dao;

import com.theflow.domain.UserCompany;
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
    
}

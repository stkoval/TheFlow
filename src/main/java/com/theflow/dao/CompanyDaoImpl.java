/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Company;
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
@Repository("companyDao")
public class CompanyDaoImpl implements CompanyDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public Company getCompanyById(int id) {
        return (Company) sessionFactory.getCurrentSession().get(Company.class, id);
    }

    @Override
    public int saveCompany(Company company) {
        sessionFactory.getCurrentSession().save(company);
        return company.getCompanyId();
    }

    @Override
    public Company getCompanyByName(String companyName) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Company where company_name = :name";
        Query q = session.createQuery(hql);
        q.setParameter("name", companyName);
        List<Company> companies = q.list();
        if (companies.size() > 0) {
            return companies.get(0);
        } else {
            return null;
        }
    }

    //checks if company alias get from request path matches any existing company alias from db
    @Override
    public List<String> getAllCompanyAliases() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "select company_alias from companies";
        Query q = session.createSQLQuery(sql);
        return q.list();
    }

    @Override
    public Company getCompanyByAlias(String companyAlias) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Company where companyAlias = :companyAlias";
        Query q = session.createQuery(hql);
        q.setParameter("companyAlias", companyAlias);
        List<Company> companies = q.list();
        if (companies.size() > 0) {
            return companies.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void updateCompany(Company company) {
        Session session = sessionFactory.getCurrentSession();
        session.update(company);
    }

    @Override
    public void removeCompany(int companyId) {
        Session session = sessionFactory.getCurrentSession();
        
        String hql1 = "delete from Project where companyId = :companyId";
        Query q1 = session.createQuery(hql1);
        q1.setParameter("companyId", companyId);
        q1.executeUpdate();
        
        
        String hql2 = "delete from UserCompany uc where uc.company.companyId = :companyId";
        Query q2 = session.createQuery(hql2);
        q2.setParameter("companyId", companyId);
        q2.executeUpdate();
        
        String hql3 = "delete from Company where companyId = :companyId";
        Query q3 = session.createQuery(hql3);
        q3.setParameter("companyId", companyId);
        q3.executeUpdate();
    }
}

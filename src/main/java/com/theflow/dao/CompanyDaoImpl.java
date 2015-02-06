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
        return (Company) sessionFactory.getCurrentSession().load(Company.class, id);
    }

    @Override
    public int saveCompany(Company company) {
        Company c = company;
        sessionFactory.getCurrentSession().save(c);
        return company.getCompanyId();
    }

    @Override
    public Company findByName(String companyName) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Company where name = :name";
        Query q = session.createQuery(hql);
        q.setParameter("name", companyName);
        List<Company> companies = q.list();
        if (companies.size() > 0) {
            return companies.get(0);
        } else {
            return null;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Company;
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
        return (Company) sessionFactory.openSession().load(Company.class, id);
    }

    @Override
    public void saveCompany(Company company) {
        sessionFactory.openSession().save(company);
    }
    
}

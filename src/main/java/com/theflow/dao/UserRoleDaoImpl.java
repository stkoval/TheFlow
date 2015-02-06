/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.UserRole;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stas
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao {
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveRole(UserRole role) {
        sessionFactory.getCurrentSession().save(role);
    }
    
}

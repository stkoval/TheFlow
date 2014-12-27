/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stas
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveUser(User user) {
        
    }

    @Override
    public void updateUser(User user) {
        
    }

    @Override
    public void removeUser(int id) {
        
    }

    @Override
    public User getUser(int id) {
        return (User)sessionFactory.openSession().load(User.class, id);
    }
    
}

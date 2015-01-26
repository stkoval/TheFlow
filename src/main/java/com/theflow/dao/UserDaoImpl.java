/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.User;
import com.theflow.service.FlowUserDetailsService;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stas
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUserReg(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void removeUser(int id) {

    }

    @Override
    public User getUserById(int id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = new ArrayList<>();

        users = sessionFactory.getCurrentSession()
                .createQuery("from User where email=?")
                .setParameter(0, email)
                .list();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(auth.getName());
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        FlowUserDetailsService.User principal
                        = (FlowUserDetailsService.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        int companyId = principal.getCompanyId();
        String hql = "from User where company.companyId = " + companyId;
        Query q = session.createQuery(hql);
        List<User> users = (List<User>)q.list();
        return users;
    }
}

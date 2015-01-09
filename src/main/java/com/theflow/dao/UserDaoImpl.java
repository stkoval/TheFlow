/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void saveUserReg(User user) {
        sessionFactory.openSession().save(user);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void removeUser(int id) {

    }

    @Override
    public User getUser(int id) {
        return (User) sessionFactory.openSession().load(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = new ArrayList<>();

        users = sessionFactory.getCurrentSession()
                .createQuery("from User where email=?")
                .setParameter(0, email)
                .list();
        if (users.size() > 0) {
            logger.debug("********************userssize" + users.size() + "***********email: " + email + " *********roles: " + users.get(0).getUserRole().size());
            return users.get(0);
        } else {

            return null;
        }
    }

}

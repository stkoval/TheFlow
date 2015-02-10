package com.theflow.dao;

import com.theflow.domain.UserRole;
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
@Repository
public class UserRoleDaoImpl implements UserRoleDao {
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveRole(UserRole role) {
        sessionFactory.getCurrentSession().save(role);
    }

    @Override
    public UserRole getRoleByUserId(int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from UserRole where user.userId = :userId";
        Query q = session.createQuery(hql);
        q.setParameter("userId", id);
        List<UserRole> roles = q.list();
        return roles.get(0);
    }
    
    @Override
    public void updateRole(UserRole role) {
        Session session = sessionFactory.getCurrentSession();
        session.update(role);
    }

    @Override
    public void removeRole(UserRole userRole) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "delete from user_roles where username = :username";
        Query q = session.createSQLQuery(sql);
        q.setParameter("username", userRole.getUser().getEmail());
        q.executeUpdate();
    }
}

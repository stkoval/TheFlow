package com.theflow.dao;

import com.theflow.domain.UserRole;

/**
 *
 * @author Stas
 */
public interface UserRoleDao {

    public void saveRole(UserRole roleAdmin);

    public UserRole getRoleByUserId(int id);
    
    public void updateRole(UserRole role);

    public void removeRole(UserRole userRole);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.UserRole;

/**
 *
 * @author Stas
 */
public interface UserRoleDao {

    public void saveRole(UserRole roleAdmin);
    
}

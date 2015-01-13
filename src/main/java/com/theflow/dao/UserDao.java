/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.User;

/**
 *
 * @author Stas
 */
public interface UserDao {
    
    public void saveUserReg(User user);
    
    public void updateUser(User user);
    
    public void removeUser(int id);
    
    public User getUserById(int id);
    
    public User findByEmail(String email);
    
    public User getCurrentUser();
}

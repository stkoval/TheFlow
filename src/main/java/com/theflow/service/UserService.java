/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.User;
import com.theflow.dto.UserDto;
import java.util.List;
import validation.CompanyExistsException;
import validation.EmailExistsException;

/**
 *
 * @author Stas
 */
public interface UserService {
    public int saveUserAddedAfterRegistration(UserDto userDto) throws EmailExistsException, CompanyExistsException;
    public List<User> getAllUsers();

    public int saveUserAddedByAdmin(UserDto userDto) throws EmailExistsException;

    public void removeUser(int id);
    
    public User getUserById(int id);
    
    public void changeUserRole(String role, int id);
    
    public FlowUserDetailsService.User getPrinciple();
}

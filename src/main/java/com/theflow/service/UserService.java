/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.User;
import com.theflow.dto.UserDto;
import java.util.List;

/**
 *
 * @author Stas
 */
public interface UserService {
    public void saveUserReg(UserDto userDto);
    public List<User> getAllUsers();
}

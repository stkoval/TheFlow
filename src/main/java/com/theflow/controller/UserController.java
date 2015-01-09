/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.dto.UserDTO;
import com.theflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Stas
 */
@Controller
public class UserController {
    
    @Autowired
    private UserService userService;

    @RequestMapping("profile")
    public String showProfile() {
        return "profile/profile";
    }

    @RequestMapping(value = "user/register", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute(value = "user") UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "signin/registration";
        }
        
        userService.saveUserReg(userDTO);
        
        return"signin/login";
    }
}

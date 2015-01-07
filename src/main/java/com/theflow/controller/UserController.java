/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Stas
 */
@Controller
public class UserController {
    
    @RequestMapping("profile")
    public String showProfile() {
        return "profile/profile";
    }
}

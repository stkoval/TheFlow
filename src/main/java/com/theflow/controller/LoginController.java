/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Stas
 */

@Controller
public class LoginController {
    
    static final Logger logger = Logger.getLogger(LoginController.class.getName());
    
//    @RequestMapping(value = "/login")
//    public String showLoginPage() {
//        return "login";
//    }
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String redirect() {

        return "home/home";
    }
    
    @RequestMapping(value = "/403", method = RequestMethod.POST)
    public String denied() {

        return "403";
    }
}

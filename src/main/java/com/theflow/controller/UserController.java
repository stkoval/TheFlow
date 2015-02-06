/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.controller;

import com.theflow.dto.UserDto;
import com.theflow.service.UserService;
import java.util.Locale;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import validation.CompanyExistsException;
import validation.EmailExistsException;

/**
 *
 * @author Stas
 */
@Controller
public class UserController {

    static final Logger logger = Logger.getLogger(IssueController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("profile")
    public String showProfile() {
        return "profile/profile";
    }

    @RequestMapping(value = "user/registration", method = RequestMethod.GET)
    public ModelAndView showUserRegistrationForm() {
        ModelAndView model = new ModelAndView("signin/registration");
        UserDto user = new UserDto();
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ModelAndView registerNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        logger.debug("Registering user account with information: {}" + userDto);
        if (result.hasErrors()) {
            return new ModelAndView("signin/registration", "user", userDto);
        }

        logger.debug("No validation errors found. Continuing registration process.");

        String registered = saveNewUser(userDto);
        if (registered.equals("emailExsists")) {
            result.rejectValue("email", "message.emailError");
            return new ModelAndView("signin/registration", "user", userDto);
        } else if (registered.equals("companyExists")) {
            result.rejectValue("companyName", "message.companyError");
            return new ModelAndView("signin/registration", "user", userDto);
        }
        ModelAndView model = new ModelAndView("signin/login");
        model.addObject("message", messageSource.getMessage("label.successRegister.title", null, Locale.ENGLISH));
        return model;
    }

    private String saveNewUser(UserDto userDto) {
        try {
            userService.saveUserReg(userDto);
        } catch (EmailExistsException e) {
            return "emailExsists";
        } catch (CompanyExistsException ex) {
            return "companyExists";
        }
        return "success";
    }
}

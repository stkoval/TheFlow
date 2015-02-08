package com.theflow.controller;

import com.theflow.domain.User;
import com.theflow.dto.UserDto;
import com.theflow.service.UserService;
import java.util.Locale;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "user/profile";
    }

    @RequestMapping(value = "user/registration", method = RequestMethod.GET)
    public ModelAndView showUserRegistrationForm() {
        ModelAndView model = new ModelAndView("signin/registration");
        UserDto user = new UserDto();
        model.addObject("user", user);
        return model;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "user/add", method = RequestMethod.GET)
    public ModelAndView showAddNewUserForm() {
        ModelAndView model = new ModelAndView("user/adduser");
        UserDto user = new UserDto();
        model.addObject("user", user);
        return model;
    }

    //save account user after registration procees from login page
    @RequestMapping(value = "/user/saveaccount", method = RequestMethod.POST)
    public ModelAndView registerNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        logger.debug("Registering user account with information: {}" + userDto);
        if (result.hasErrors()) {
            return new ModelAndView("signin/registration", "user", userDto);
        }

        logger.debug("No validation errors found. Continuing registration process.");

        String registered = saveNewUserAccount(userDto);
        if (registered.equals("emailExsists")) {
            result.rejectValue("email", "message.emailError");
            return new ModelAndView("signin/registration", "user", userDto);
        } else if (registered.equals("companyExists")) {
            result.rejectValue("companyName", "message.companyError");
            return new ModelAndView("signin/registration", "user", userDto);
        }
        ModelAndView model = new ModelAndView("signin/login");
        model.addObject("message", messageSource.getMessage("label.successRegister.title", null, Locale.ENGLISH) + " " + userDto.getEmail());
        return model;
    }
    
    //add new user to existing company through manage users page by admin user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/saveuser", method = RequestMethod.POST)
    public ModelAndView addNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        logger.debug("Registering user account with information: {}" + userDto);
        if (result.hasErrors()) {
            return new ModelAndView("user/adduser", "user", userDto);
        }

        logger.debug("No validation errors found. Continuing registration process.");

        String registered = saveNewUserAccount(userDto);
        if (registered.equals("emailExsists")) {
            result.rejectValue("email", "message.emailError");
            return new ModelAndView("user/add", "user", userDto);
        } 
        ModelAndView model = new ModelAndView("user/manage");
        model.addObject("message", messageSource.getMessage("label.successAddUser.title", null, Locale.ENGLISH));
        return model;
    }

    private String saveNewUserAccount(UserDto userDto) {
        try {
            userService.saveUserReg(userDto);
        } catch (EmailExistsException e) {
            return "emailExsists";
        } catch (CompanyExistsException ex) {
            return "companyExists";
        }
        return "success";
    }
    
    private String saveNewUserEmployee(UserDto userDto) {
        try {
            userService.saveUserEmp(userDto);
        } catch (EmailExistsException e) {
            return "emailExsists";
        } 
        return "success";
    }
    
    //removes issue from database
    @RequestMapping(value = "user/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeUser(@PathVariable int id) {
        userService.removeUser(id);
        return new ModelAndView("redirect:../../home");
    }
    
    @RequestMapping(value = "user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable int id) {

        ModelAndView model = new ModelAndView("user/edit");
        User user = userService.getUserById(id);
        model.addObject("user", user);

        return model;
    }
}

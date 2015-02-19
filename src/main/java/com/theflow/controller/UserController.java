package com.theflow.controller;

import com.theflow.domain.User;
import com.theflow.domain.UserRole;
import com.theflow.dto.UserDto;
import com.theflow.service.UserService;
import helpers.UserRoleConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import validation.CompanyExistsException;
import validation.EmailExistsException;

/**
 *
 * @author Stas
 */
@Controller
public class UserController {

    static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;
    
    @RequestMapping(value="profile", method = RequestMethod.GET)
    public ModelAndView showProfile() {
        ModelAndView model = new ModelAndView("user/profile");
        User user = userService.getUserById(userService.getPrinciple().getUserId());
        Set<UserRole> roles = user.getUserRole();
        List<UserRole> listRoles = new ArrayList<>(roles);
        model.addObject("user", user);
        model.addObject("roles", listRoles);
        return model;
    }

    @RequestMapping(value = "user/registration", method = RequestMethod.GET)
    public ModelAndView showUserRegistrationForm() {
        ModelAndView model = new ModelAndView("signin/registration");
        UserDto user = new UserDto();
        model.addObject("user", user);
        return model;
    }
    
    @PreAuthorize("hasRole('Admin')")
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
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "/user/saveuser", method = RequestMethod.POST)
    public ModelAndView addNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        logger.debug("Registering user account with information: {}" + userDto);
        if (result.hasErrors()) {
            return new ModelAndView("user/adduser", "user", userDto);
        }

        logger.debug("No validation errors found. Continuing registration process.");

        String registered = saveNewUserEmployee(userDto);
        if (registered.equals("emailExsists")) {
            result.rejectValue("email", "message.emailError");
            return new ModelAndView("/user/adduser", "user", userDto);
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
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "user/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeUser(@PathVariable int id) {
        userService.removeUser(id);
        return new ModelAndView("redirect:/home/home");
    }
    
    @PreAuthorize("hasAnyRole('Admin','Observer')")
    @RequestMapping(value = "user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable int id) {

        ModelAndView model = new ModelAndView("user/edit");
        User user = userService.getUserById(id);
        model.addObject("user", user);

        return model;
    }
    
    @PreAuthorize("hasAnyRole('Admin','Observer')")
    @RequestMapping(value = "/users/manage", method = RequestMethod.GET)
    public ModelAndView showManageUsersPage() {
        ModelAndView model = new ModelAndView("user/manage");
        List<User> users = userService.getAllUsers();
        model.addObject("users", users);
        return model;
    }
    
    @PreAuthorize("hasAnyRole('Admin','Observer')")
    @RequestMapping(value = "user/details/{id}", method = RequestMethod.GET)
    public ModelAndView showUserDetails(@PathVariable int id) {
        ModelAndView model = new ModelAndView("user/details");
        User user = userService.getUserById(id);
        List<UserRoleConstants> roles = Arrays.asList(UserRoleConstants.values());
        String currentRole = user.getUserRole().toArray()[0].toString();
        model.addObject("user", user);
        model.addObject("roles", roles);
        model.addObject("current_role", currentRole);
        return model;
    }
    
    @PreAuthorize("hasRole('Admin')")
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "user/changerole/{id}", method = RequestMethod.GET)
    public void changeUserAuchorities(@RequestParam(value = "role") String role,
            @PathVariable int id) {
        userService.changeUserRole(role, id);
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, HibernateException exception) {
        logger.error("Request: " + req.getRequestURL() + " exception " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error/error");
        return mav;
    }
}

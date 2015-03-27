package com.theflow.controller;

import com.theflow.domain.User;
import com.theflow.dto.UserDto;
import com.theflow.dto.UserProfileDto;
import com.theflow.service.FlowUserDetailsService;
import com.theflow.service.UserService;
import helpers.UserRoleConstants;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import validation.CompanyAliasExistsException;
import validation.CompanyExistsException;
import validation.EmailExistsException;
import validation.UsernameDuplicationException;

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

    @PreAuthorize("hasAnyRole('Admin','User','Cabinet')")
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView showUserProfilePage() {
        ModelAndView model = new ModelAndView("user/profile");

        User user = userService.getUserById(userService.getPrincipal().getUserId());
        FlowUserDetailsService.User principal = userService.getPrincipal();

        model.addObject("user", user);
        return model;
    }

    //user registration from landing page
    @RequestMapping(value = "user/registration", method = RequestMethod.GET)
    public ModelAndView showUserRegistrationForm() {
        ModelAndView model = new ModelAndView("signin/registration");
        UserDto user = new UserDto();
        model.addObject("user", user);
        return model;
    }

    //add user by admin from manage project page
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
    public ModelAndView saveNewUserFromRegistration(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("signin/registration", "user", userDto);
        }

        try {
            userService.saveUserAddedAfterRegistration(userDto);
        } catch (EmailExistsException e) {
            result.rejectValue("email", "message.emailError");
            return new ModelAndView("signin/registration", "user", userDto);
        } catch (CompanyExistsException ex) {
            result.rejectValue("companyName", "message.companyError");
            return new ModelAndView("signin/registration", "user", userDto);
        } catch (CompanyAliasExistsException ex) {
            result.rejectValue("companyAlias", "message.companyAliasError");
            return new ModelAndView("signin/registration", "user", userDto);
        }

        ModelAndView model = new ModelAndView("signin/login");
        model.addObject("message", messageSource.getMessage("label.successRegister.title", null, Locale.ENGLISH) + " " + userDto.getEmail());
        return model;
    }

    //add new user to existing company through manage users page by admin user
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "/user/saveuser", method = RequestMethod.POST)
    public ModelAndView saveNewUserFromAdminTools(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        logger.debug("Registering user account with information: {}" + userDto);
        if (result.hasErrors()) {
            return new ModelAndView("user/adduser", "user", userDto);
        }

        logger.debug("No validation errors found. Continuing registration process.");

        try {
            userService.saveUserAddedByAdmin(userDto);
        } catch (UsernameDuplicationException ex) {
            result.rejectValue("email", "message.duplicateUser");
            ModelAndView mav = new ModelAndView("/user/adduser", "user", userDto);
            return mav;
        } catch (EmailExistsException e) {
            result.rejectValue("email", "message.emailError");
            ModelAndView mav = new ModelAndView("/user/add_existing", "user", userDto);
            mav.addObject("usernameExists", userDto.getEmail());
            return mav;
        }

        ModelAndView model = new ModelAndView("redirect:/users/manage");
        model.addObject("message", messageSource.getMessage("label.successAddUser.title", null, Locale.ENGLISH) + userDto.getEmail());
        return model;
    }

    //removes user from database
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "user/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeUser(@PathVariable(value = "id") int userId) {
        userService.removeUser(userId);
        return new ModelAndView("redirect:/users/manage");
    }

    //add user that already registered
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "/user/add_existing", method = RequestMethod.POST)
    public ModelAndView addExistingUserToCompany(HttpServletRequest request, BindingResult result) {
        userService.addExistingUserToCompany(request.getParameter("username"));
        return new ModelAndView("redirect:/users/manage");
    }

    //edit user from profile page
    @PreAuthorize("hasAnyRole('Admin','User')")
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showUserEditForm(@PathVariable(value = "id") int userId) {

        ModelAndView model = new ModelAndView("user/edit");
        User user = userService.getUserById(userId);
        model.addObject("user", user);

        return model;
    }

    @PreAuthorize("hasAnyRole('Admin','User')")
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ModelAndView updateIssue(@ModelAttribute(value = "user") @Valid UserProfileDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("/user/edit", "user", userDto);
        }
        userService.updateUser(userDto);
        return new ModelAndView("redirect:/users/manage");
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
    public ModelAndView showUserDetails(@PathVariable(value = "id") int userId) {
        ModelAndView model = new ModelAndView("user/details");
        User user = userService.getUserById(userId);
        List<UserRoleConstants> roles = Arrays.asList(UserRoleConstants.values());
        model.addObject("user", user);
        model.addObject("roles", roles);
        return model;
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "user/{id}/role", method = RequestMethod.GET)
    public void changeUserAuthorities(@RequestParam(value = "role") String role,
            @PathVariable(value = "id") int userId) {
        userService.changeUserRole(role, userId);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, HibernateException exception) {
        logger.error("Request: " + req.getRequestURL() + " exception " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("/error/error");
        return mav;
    }
}

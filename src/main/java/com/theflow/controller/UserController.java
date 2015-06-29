package com.theflow.controller;

import com.theflow.domain.Company;
import com.theflow.domain.User;
import com.theflow.domain.UserCompany;
import com.theflow.dto.PasswordDto;
import com.theflow.dto.UserDto;
import com.theflow.dto.UserProfileDto;
import com.theflow.service.CompanyService;
import com.theflow.service.FlowUserDetailsService;
import com.theflow.service.UserService;
import helpers.UserRoleConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import validation.CompanyAliasExistsException;
import validation.CompanyCreatorDeletingException;
import validation.CompanyExistsException;
import validation.CompanyNotFoundException;
import validation.EmailExistsException;
import validation.InvalidPasswordException;
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

    @Autowired
    private CompanyService companyService;

    @PreAuthorize("hasAnyRole('Admin','User','Cabinet')")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView showUserProfilePage() {
        ModelAndView model = new ModelAndView("user/profile");

        User user = userService.getUserById(userService.getPrincipal().getUserId());

        model.addObject("user", user);

        byte[] profileImage = user.getImage();
        if (profileImage != null && profileImage.length > 0) {

            Base64.Encoder base64Encoder = Base64.getEncoder();
            StringBuilder imageString = new StringBuilder();
            imageString.append("data:image/jpeg;base64,");
            String encoded = base64Encoder.encodeToString(profileImage);
            imageString.append(encoded);
            String image = imageString.toString();
            model.addObject("image", image);
        }
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
    public ModelAndView registerAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        ModelAndView model = new ModelAndView("signin/login");
        if (result.hasErrors()) {
            return new ModelAndView("/signin/registration", "user", userDto);
        }

        try {
            userService.saveUserAddedAfterRegistration(userDto);
        } catch (EmailExistsException e) {
            result.rejectValue("email", "message.emailError");
            ModelAndView mav = new ModelAndView("/signin/registration_user_exists", "user", userDto);
            return mav;
        } catch (CompanyExistsException ex) {
            result.rejectValue("companyName", "message.companyError");
            return new ModelAndView("/signin/registration", "user", userDto);
        } catch (CompanyAliasExistsException ex) {
            result.rejectValue("companyAlias", "message.companyAliasError");
            return new ModelAndView("/signin/registration", "user", userDto);
        }

        model.addObject("message", messageSource.getMessage("message.user.register.success", null, Locale.ENGLISH) + " " + userDto.getEmail());
        return model;
    }

    //save account user after registration procees from login page when user exists in db
    @RequestMapping(value = "/signin/new_account_user_exists", method = RequestMethod.POST)
    public ModelAndView registerAccountUserExists(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("signin/login");
        String username = request.getParameter("username");
        userService.addNewCompanyUserExists(request.getParameter("username"), request.getParameter("company_name"), request.getParameter("company_alias"));
        model.addObject("message", messageSource.getMessage("message.user.register.success", null, Locale.ENGLISH) + " " + username);
        return model;
    }

    //add new user to existing company through manage users page by admin user
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "/user/saveuser", method = RequestMethod.POST)
    public ModelAndView saveNewUserFromAdminTools(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("user/adduser", "user", userDto);
        }

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
        model.addObject("message", messageSource.getMessage("message.addUser.success", null, Locale.ENGLISH) + userDto.getEmail());
        return model;
    }

    //removes user from database
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "user/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeUser(@PathVariable(value = "id") int userId) {
        ModelAndView model = new ModelAndView("redirect:/users/manage");
        try {
            userService.removeUser(userId);
        } catch (CompanyCreatorDeletingException ex) {
            model.addObject("error", messageSource.getMessage("message.user.creator.deletion", null, Locale.ENGLISH));
        }
        return model;
    }

    //add user that already registered
    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "/user/add_existing", method = RequestMethod.POST)
    public ModelAndView addExistingUserToCompany(HttpServletRequest request) {
        userService.addExistingUserToCompany(request.getParameter("username"));
        return new ModelAndView("redirect:/users/manage");
    }

    //change user password form
    @PreAuthorize("hasAnyRole('Admin','User','Cabinet')")
    @RequestMapping(value = "/user/{id}/changepass", method = RequestMethod.GET)
    public ModelAndView showChangePasswordForm(@PathVariable(value = "id") int userId) {
        ModelAndView model = new ModelAndView("/user/change_pass");
        model.addObject("userId", userId);
        model.addObject("passwordDto", new PasswordDto());
        return model;
    }

    //change user password proceed
    @PreAuthorize("hasAnyRole('Admin','User','Cabinet')")
    @RequestMapping(value = "/user/changepass", method = RequestMethod.POST)
    public ModelAndView changeUserPassword(@Valid PasswordDto passwordDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/profile");
        }
        ModelAndView model = new ModelAndView("redirect:/profile");
        try {
            userService.changePassword(passwordDto);
        } catch (InvalidPasswordException ex) {
            result.rejectValue("password", "PasswordMatches.user");
            ModelAndView model1 = new ModelAndView("/user/change_pass", "passwordDto", passwordDto);
            model1.addObject("userId", passwordDto.getUserId());
            return new ModelAndView("/user/change_pass", "passwordDto", passwordDto);
        }
        model.addObject("message", messageSource.getMessage("message.user.changepass.success", null, Locale.ENGLISH));

        //refresh authentication object
        User editedUser = userService.getUserById(passwordDto.getUserId());
        refreshAuthentication(editedUser);

        return model;
    }

    //edit user from profile page
    @PreAuthorize("hasAnyRole('Admin','User','Cabinet')")
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showUserEditForm(@PathVariable(value = "id") int userId) {
        int id = userService.getPrincipal().getUserId();
        if (id != userId) {
            ModelAndView model = new ModelAndView("redirect:/users/manage");
            model.addObject("message", messageSource.getMessage("message.norights", null, Locale.ENGLISH));
            return model;
        }
        ModelAndView model = new ModelAndView("/user/edit");
        User user = userService.getUserById(userId);
        model.addObject("user", user);

        return model;
    }

    @PreAuthorize("hasAnyRole('Admin','User','Cabinet')")
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ModelAndView updateIssue(@ModelAttribute(value = "user") @Valid UserProfileDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("/user/edit", "user", userDto);
        }
        userService.updateUser(userDto);
        User editedUser = userService.getUserById(userDto.getUserId());
        refreshAuthentication(editedUser);
        return new ModelAndView("redirect:/profile");
    }

    //Refreshes Authentication object when logged in user's data was changed
    private void refreshAuthentication(User editedUser) {
        FlowUserDetailsService.User editedPrincipal = buildPrincipalAfterEditing(editedUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(editedPrincipal, editedPrincipal.getPassword(), editedPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Always synchronize with flowUserDetailsService!!!
    private FlowUserDetailsService.User buildPrincipalAfterEditing(User editedUser) {
        FlowUserDetailsService.User principal = userService.getPrincipal();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(principal.getRole()));
        FlowUserDetailsService.User editedPrincipal = new FlowUserDetailsService.User(editedUser.getEmail(),
                editedUser.getPassword(), grantedAuthorities, editedUser.getFirstName(), editedUser.getLastName(),
                editedUser.getUserId(), editedUser.isEnabled(), principal.getCompanyId(), principal.getCompanyName(),
                principal.getCompanyAlias(), principal.getRole());
        return editedPrincipal;
    }

    @PreAuthorize("hasAnyRole('Admin','Observer')")
    @RequestMapping(value = "/users/manage", method = RequestMethod.GET)
    public ModelAndView showManageUsersPage() {
        ModelAndView model = new ModelAndView("user/manage");
        List<UserCompany> userCompanys = userService.getUserCompanyByCompanyId(userService.getPrincipal().getCompanyId());
        List<UserRoleConstants> roles = Arrays.asList(UserRoleConstants.values());
        model.addObject("users", userCompanys);
        model.addObject("roles", roles);
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
        int companyId = userService.getPrincipal().getCompanyId();
        UserCompany uc = userService.getUserCompanyByUserIdAndCompanyId(userId, companyId);
        model.addObject("current_role", uc.getUserRole());
        byte[] profileImage = user.getImage();
        if (profileImage != null && profileImage.length > 0) {

            Base64.Encoder base64Encoder = Base64.getEncoder();
            StringBuilder imageString = new StringBuilder();
            imageString.append("data:image/jpeg;base64,");
            String encoded = base64Encoder.encodeToString(profileImage);
            imageString.append(encoded);
            String image = imageString.toString();
            model.addObject("image", image);
        }
        return model;
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "user/{id}/role", method = RequestMethod.GET)
    public ModelAndView changeUserAuthorities(@RequestParam(value = "role") String role,
            @PathVariable(value = "id") int userId) {
        int companyId = userService.getPrincipal().getCompanyId();
        Company company;
        ModelAndView model = new ModelAndView("redirect:/user/details/" + userId);
        try {
            company = companyService.getCompanyById(companyId);
            if (company.getCreator().getUserId() == userId) {
                return model;
            }
        } catch (CompanyNotFoundException ex) {
            model.addObject("error", messageSource.getMessage("message.company.notfound", null, Locale.ENGLISH));
        }
        userService.changeUserRole(role, userId);
        return model;
    }

    @RequestMapping(value = "user/{id}/image", method = RequestMethod.POST)
    public ModelAndView uploadProfileImage(@RequestParam("image") CommonsMultipartFile image, @PathVariable(value = "id") int userId) {
        ModelAndView model = new ModelAndView("redirect:/profile");
        if (!image.isEmpty()) {
            User user = userService.getUserById(userId);
            user.setImage(image.getBytes());
            userService.updateUser(user);
        }
        return model;
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

package com.theflow.service;

import com.theflow.dao.CompanyDao;
import com.theflow.dao.UserCompanyDao;
import com.theflow.dao.UserDao;
import com.theflow.domain.Company;
import com.theflow.domain.User;
import com.theflow.domain.UserCompany;
import com.theflow.dto.UserDto;
import com.theflow.dto.UserProfileDto;
import helpers.UserRoleConstants;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validation.CompanyAliasExistsException;
import validation.CompanyExistsException;
import validation.EmailExistsException;
import validation.UsernameDuplicationException;

/**
 *
 * @author Stas
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserCompanyDao userCompanyDao;

    //Saves user from registration page. Assignes admin role
    @Override
    public int saveUserAddedAfterRegistration(UserDto userDto) throws CompanyExistsException, CompanyAliasExistsException {
        if (companyExist(userDto.getCompanyName())) {
            throw new CompanyExistsException("There is a company already registered with name: "
                    + userDto.getCompanyName());
        }
        if (companyAliasExist(userDto.getCompanyAlias())) {
            throw new CompanyAliasExistsException("This company alias is alreade registered, please try another one: "
                    + userDto.getCompanyAlias());
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setEnabled(true);
        
        UserCompany userCompany = new UserCompany();
        
        Company company = new Company(userDto.getCompanyName());
        company.setAlias(userDto.getCompanyAlias());
        
        userCompany.setUser(user);
        userCompany.setCompany(company);
        userCompany.setUserRole(UserRoleConstants.ADMIN.toString());
        

        userCompanyDao.saveUserCompany(userCompany);
        return user.getUserId();
    }

    private boolean emailExist(String email) {
        User user = userDao.findUserByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    private boolean companyExist(String companyName) {
        Company company = companyDao.getCompanyByName(companyName);
        if (company != null) {
            return true;
        }
        return false;
    }
    
    private boolean companyAliasExist(String companyAlias) {
        Company company = companyDao.getCompanyByAlias(companyAlias);
        if (company != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    //add new user to existing company through manage users page. Assignes user role
    @Override
    public int saveUserAddedByAdmin(UserDto userDto) throws EmailExistsException, UsernameDuplicationException {
        if (userAlreadyAdded(userDto.getEmail())) {
            throw new UsernameDuplicationException("User already added: " + userDto.getEmail());
        }
        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email adress: "
                    + userDto.getEmail());
        }
        
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        Company company = companyDao.getCompanyByName(userDto.getCompanyName());
        user.setEnabled(true);
        
        UserCompany userCompany = new UserCompany();
        userCompany.setUser(user);
        userCompany.setCompany(company);
        userCompany.setUserRole(UserRoleConstants.USER.toString());
        
        userCompanyDao.saveUserCompany(userCompany);
        return user.getUserId();
    }
    
    @Override
    public void updateUser(UserProfileDto userDto) {
        User user = userDao.getUserById(userDto.getUserId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        userDao.updateUser(user);
    }

    @Override
    public void removeUser(int id) {
        userDao.removeUser(id, getPrincipal().getCompanyId());
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    //change user role on manage users page
    @Override
    public void changeUserRole(String role, int id) {
        User user = userDao.getUserById(id);
        int companyId = getPrincipal().getCompanyId();
        Set<UserCompany> userCompanies = user.getUserCompanies();
        for (UserCompany userCompany : userCompanies) {
            if (userCompany.getCompany().getCompanyId() == companyId) {
                userCompany.setUserRole(role);
                userCompanyDao.updateUserCompany(userCompany);
                break;
            }
        }
    }

    @Override
    public FlowUserDetailsService.User getPrincipal() {
        return (FlowUserDetailsService.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void addExistingUserToCompany(String email) {
        User user = userDao.findUserByEmail(email);
        Company company = companyDao.getCompanyByName(getPrincipal().getCompanyName());
        UserCompany userCompany = new UserCompany();
        userCompany.setCompany(company);
        userCompany.setUser(user);
        userCompanyDao.saveUserCompany(userCompany);
    }

    private boolean userAlreadyAdded(String email) {
        boolean added = false;
        List<User> users = userCompanyDao.getUsersByCompanyId(getPrincipal().getCompanyId());
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                added = true;
                break;
            }
        }
        return added;
    }

    @Override
    public List<UserCompany> getUserCompaniesForCurrentUser() {
        FlowUserDetailsService.User principal = getPrincipal();
        List<UserCompany> userCompanies = userCompanyDao.getUserCompaniesByUserId(principal.getUserId());
        return userCompanies;
    }

    @Override
    public UserCompany getUserCompanyById(int ucId) {
        return userCompanyDao.getUserCompanyById(ucId);
    }
}

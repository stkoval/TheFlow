package com.theflow.service;

import com.theflow.dao.CompanyDao;
import com.theflow.dao.UserDao;
import com.theflow.domain.Company;
import com.theflow.domain.User;
import com.theflow.domain.UserRole;
import com.theflow.dto.UserDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validation.CompanyExistsException;
import validation.EmailExistsException;

/**
 *
 * @author Stas
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private CompanyDao companyDao;
    
    @Autowired
    private UserDao userDao;
    
    //Saves user from registration page. Assignes admin role
    @Override
    public int saveUserReg(UserDto userDto) throws EmailExistsException, CompanyExistsException{
        if (emailExist(userDto.getEmail())) {  
            throw new EmailExistsException("There is an account with that email adress: " + 
              userDto.getEmail());
        }
        if (companyExist(userDto.getCompanyName())) {  
            throw new CompanyExistsException("There is a company already registered with name: " + 
              userDto.getCompanyName());
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        Company company = new Company(userDto.getCompanyName());
        int companyId = companyDao.saveCompany(company);
        user.setCompany(companyDao.getCompanyById(companyId));
        user.getUserRole().add(new UserRole(user,"ROLE_ADMIN"));
        user.setEnabled(true);

        return userDao.saveUserReg(user);
    }
    
    private boolean emailExist(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
    
    private boolean companyExist(String companyName) {
        Company company = companyDao.findByName(companyName);
        if (company != null) {
            return true;
        }
        return false;
    }
    
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}

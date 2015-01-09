/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.dao.CompanyDao;
import com.theflow.dao.UserDao;
import com.theflow.domain.Company;
import com.theflow.domain.User;
import com.theflow.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stas
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private CompanyDao companyDao;
    
    @Autowired
    private UserDao userDao;
    
    @Override
    public void saveUserReg(UserDTO userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        Company company = new Company(userDto.getCompanyName());
        companyDao.saveCompany(company);
        user.setCompany(company);

        userDao.saveUserReg(user);
    }
    
}

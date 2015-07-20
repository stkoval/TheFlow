/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.User;
import com.theflow.domain.UserCompany;
import com.theflow.dto.CompanyDto;
import com.theflow.dto.PasswordDto;
import com.theflow.dto.UserDto;
import com.theflow.dto.UserProfileDto;
import java.util.List;
import validation.CompanyAliasExistsException;
import validation.CompanyCreatorDeletingException;
import validation.CompanyExistsException;
import validation.EmailExistsException;
import validation.InvalidPasswordException;
import validation.UsernameDuplicationException;

/**
 *
 * @author Stas
 */
public interface UserService {
    public int saveUserAddedAfterRegistration(UserDto userDto) throws EmailExistsException, CompanyExistsException, CompanyAliasExistsException;
    public List<User> getAllUsers();

    public int saveUserAddedByAdmin(UserDto userDto) throws EmailExistsException, UsernameDuplicationException;

    public void removeUser(int id)  throws CompanyCreatorDeletingException;
    
    public User getUserById(int id);
    
    public void changeUserRole(String role, int userId);
    
    public FlowUserDetailsService.User getPrincipal();
    
    public void updateUser(UserProfileDto userDto);
    
    public void updateUser(User user);

    public void addExistingUserToCompany(String email);

    public List<UserCompany> getUserCompaniesForCurrentUser();

    public UserCompany getUserCompanyById(int ucId);

    public void saveNewCompanyFromCabinet(CompanyDto companyDto)  throws CompanyAliasExistsException, CompanyExistsException;

    public UserCompany getUserCompanyByUserIdAndCompanyId(int userId, int companyId);

    public List<UserCompany> getUserCompanyByCompanyId(int companyId);

    public List<UserCompany> getOwnCompanies();

    public void changePassword(PasswordDto passwordDto) throws InvalidPasswordException;

    public void addNewCompanyUserExists(String username, String password, String companyName, String companyAlias) throws InvalidPasswordException;
}

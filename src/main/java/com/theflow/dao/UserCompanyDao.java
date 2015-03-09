package com.theflow.dao;

import com.theflow.domain.UserCompany;

/**
 *
 * @author Stas
 */
public interface UserCompanyDao {
    public void saveUserCompany(UserCompany userCompany);
    public void updateUserCompany(UserCompany userCompany);
}

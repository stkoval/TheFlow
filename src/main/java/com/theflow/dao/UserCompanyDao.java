package com.theflow.dao;

import com.theflow.domain.User;
import com.theflow.domain.UserCompany;
import java.util.List;

/**
 *
 * @author Stas
 */
public interface UserCompanyDao {
    public void saveUserCompany(UserCompany userCompany);
    public void updateUserCompany(UserCompany userCompany);

    public List<User> getUsersByCompanyId(int companyId);

    public List<UserCompany> getUserCompaniesByUserId(int userId);

    public UserCompany getUserCompanyById(int ucId);

    public UserCompany getUserCompaniesByUserIdAndCompanyId(int userId, int companyId);

    public List<UserCompany> getUserCompaniesByCompanyId(int companyId);

    public List<UserCompany> getOwnCompanies();
}

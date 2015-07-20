/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Company;
import com.theflow.domain.UserCompany;
import java.util.List;

/**
 *
 * @author Stas
 */
public interface CompanyDao {
    public Company getCompanyById(int id);
    public int saveCompany(Company company);

    public Company getCompanyByName(String companyName);
    
    public Company getCompanyByAlias(String companyName);
    
    public List<String> getAllCompanyAliases();

    public void updateCompany(Company company);

    public void removeCompany(int companyId);
}

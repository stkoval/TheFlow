/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.Company;
import validation.CompanyExistsException;
import validation.CompanyNotExistException;

/**
 *
 * @author Stas
 */
public interface CompanyService {

    public Company getCompanyById(int companyId);
    
    public boolean checkIfPartOfPathSubdomain(String companyName);
    
    public void saveNewCompany(Company company) throws CompanyExistsException;
}

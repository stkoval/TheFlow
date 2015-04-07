/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.Company;
import com.theflow.dto.CompanyDto;
import validation.CompanyAliasExistsException;
import validation.CompanyExistsException;
import validation.CompanyNotFoundException;

/**
 *
 * @author Stas
 */
public interface CompanyService {

    public Company getCompanyById(int companyId) throws CompanyNotFoundException;
    
    public void saveNewCompany(Company company) throws CompanyExistsException, CompanyAliasExistsException;

    public void updateCompany(CompanyDto companyDto) throws CompanyNotFoundException, CompanyExistsException, CompanyAliasExistsException;

    public void removeCompany(int companyId);
}

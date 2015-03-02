/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.Company;
import java.util.List;

/**
 *
 * @author Stas
 */
public interface CompanyService {

    public boolean companyExists(String companyName);
    public List<Company> getAllCompanies();

    public Company getCompanyById();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.Company;

/**
 *
 * @author Stas
 */
public interface CompanyDao {
    public Company getCompanyById(int id);
    public void saveCompany(Company company);
}

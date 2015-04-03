package com.theflow.service;

import com.theflow.dao.CompanyDao;
import com.theflow.domain.Company;
import com.theflow.dto.CompanyDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validation.CompanyExistsException;
import validation.CompanyNotFoundException;

/**
 *
 * @author Stas
 */
@Service
public class CompanyServiceImpl implements CompanyService{
    
    @Autowired
    private CompanyDao companyDao;

    @Override
    @Transactional
    public Company getCompanyById(int companyId) throws CompanyNotFoundException {
        Company company = companyDao.getCompanyById(companyId);
        if (company == null) {
            throw new CompanyNotFoundException("Company not exists");
        }
        return company;
    }

    @Override
    @Transactional
    public boolean checkIfPartOfPathSubdomain(String companyAlias) {
        List<String> companyNames = companyDao.getAllCompanyAliases();
        boolean exists = false;
        if (companyNames == null || companyNames.isEmpty()) {
            return false;
        } else {
            for (String name : companyNames) {
                if (name.equalsIgnoreCase(companyAlias)) {
                    exists = true;
                }
            }
        }
        return exists;
    }

    @Override
    @Transactional
    public void saveNewCompany(Company company) throws CompanyExistsException {
        if (companyExist(company.getName())) {
            throw new CompanyExistsException("Company already exists: " + company.getName());
        } else {
            companyDao.saveCompany(company);
        }
    }
    
    private boolean companyExist(String companyName) {
        Company company = companyDao.getCompanyByName(companyName);
        if (company != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void updateCompany(CompanyDto companyDto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

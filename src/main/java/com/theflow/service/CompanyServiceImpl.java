package com.theflow.service;

import com.theflow.dao.CompanyDao;
import com.theflow.domain.Company;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validation.CompanyExistsException;
import validation.CompanyNotExistException;

/**
 *
 * @author Stas
 */
@Service
public class CompanyServiceImpl implements CompanyService{
    
    @Autowired
    private CompanyDao companyDao;

    @Transactional
    @Override
    public Company getCompanyById(int companyId) {
        return companyDao.getCompanyById(companyId);
    }

    @Transactional
    @Override
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

    @Transactional
    @Override
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
    
}

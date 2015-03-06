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
    public boolean checkIfCompanyExists(String companyAlias) throws CompanyNotExistException {
        List<String> companyNames = companyDao.getAllCompanyAliases();
        boolean exists = false;
        if (companyNames == null || companyNames.isEmpty()) {
            throw new CompanyNotExistException("Company does not exist: " + companyAlias);
        } else {
            for (String name : companyNames) {
                if (name.equalsIgnoreCase(companyAlias)) {
                    exists = true;
                }
            }
        }
        if (exists == false) {
            throw new CompanyNotExistException("Company does not exist: " + companyAlias);
        }
        return true;
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

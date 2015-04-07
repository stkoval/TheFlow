package com.theflow.service;

import com.theflow.dao.CompanyDao;
import com.theflow.domain.Company;
import com.theflow.dto.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validation.CompanyAliasExistsException;
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
    public void saveNewCompany(Company company) throws CompanyExistsException, CompanyAliasExistsException {
        if (companyExists(company.getName())) {
            throw new CompanyExistsException("Company already exists: " + company.getName());
        } else if (companyAliasExists(company.getAlias())) {
            throw new CompanyAliasExistsException("Company alias already exists: " + company.getName());
        } else {
            companyDao.saveCompany(company);
        }
    }
    
    //use when updating company
    private boolean companyExists(String companyName, int companyId) {
        Company company = companyDao.getCompanyByName(companyName);
        if (company != null && company.getCompanyId() != companyId) {
            return true;
        }
        return false;
    }
    
    //use when creating new company
    private boolean companyExists(String companyName) {
        Company company = companyDao.getCompanyByName(companyName);
        if (company != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void updateCompany(CompanyDto companyDto) throws CompanyNotFoundException, CompanyExistsException, CompanyAliasExistsException{
        Company company = companyDao.getCompanyById(companyDto.getCompanyId());
        if (company == null) {
            throw new CompanyNotFoundException("Company not found");
        } else if (companyExists(companyDto.getCompanyName(), companyDto.getCompanyId())) {
            throw new CompanyExistsException("Company already exists");
        } else if (companyAliasExists(companyDto.getCompanyAlias(), companyDto.getCompanyId())) {
            throw new CompanyAliasExistsException("Company alias already exists");
        }
        company.setName(companyDto.getCompanyName());
        company.setAlias(companyDto.getCompanyAlias());
        companyDao.updateCompany(company);
    }

    //use when updating company
    private boolean companyAliasExists(String alias, int companyId) {
        Company company = companyDao.getCompanyByAlias(alias);
        if (company != null && company.getCompanyId() != companyId) {
            return true;
        }
        return false;
    }
    
    //use when creating company
    private boolean companyAliasExists(String alias) {
        Company company = companyDao.getCompanyByAlias(alias);
        if (company != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void removeCompany(int companyId) {
        companyDao.removeCompany(companyId);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import validation.ValidCompanyAlias;

/**
 *
 * @author Stas
 */
public class CompanyDto {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 30)
    private String companyName;
    
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 16)
    @ValidCompanyAlias
    private String companyAlias;

    public CompanyDto(String companyName, String companyAlias) {
        this.companyName = companyName;
        this.companyAlias = companyAlias;
    }

    public CompanyDto() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAlias() {
        return companyAlias;
    }

    public void setCompanyAlias(String companyAlias) {
        this.companyAlias = companyAlias;
    }
}

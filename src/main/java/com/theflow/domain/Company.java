/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Stas
 */
@Entity
@Table(name = "companies")
public class Company implements Serializable {

    @Id
    @Column(name = "company_id")
    private int companyId;
    
    @Column(name = "company_name")
    private String name;
    
    @OneToMany(mappedBy = "company")
    private Set<User> employees;
    
    @OneToMany(mappedBy = "company")
    private Set<Project> projects;
    
    public Company() {}
    
    public Company(String name) {
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<User> employees) {
        this.employees = employees;
    }

    public Set<Project> getProjectIds() {
        return projects;
    }

    public void setProjectIds(Set<Project> projects) {
        this.projects = projects;
    }
}

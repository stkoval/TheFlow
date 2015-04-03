package com.theflow.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @GeneratedValue
    @Column(name = "company_id")
    private int companyId;
    
    @Column(name = "company_name")
    private String name;
    
    @Column(name = "company_alias")
    private String companyAlias;
    
    @OneToMany(mappedBy = "company")
    private Set<UserCompany> userCompanies;
    
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    
    public Company() {}
    
    public Company(String name) {
        this.name = name;
    }

    public Company(String name, String companyAlias) {
        this.name = name;
        this.companyAlias = companyAlias;
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
    
    public String getAlias() {
        return companyAlias;
    }

    public void setAlias(String alias) {
        this.companyAlias = alias;
    }
    
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.companyId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Company other = (Company) obj;
        if (this.companyId != other.companyId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Company{" + "companyId=" + companyId + ", name=" + name + '}';
    }

}

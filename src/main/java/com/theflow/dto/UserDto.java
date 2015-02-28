package com.theflow.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import validation.PasswordMatches;
import validation.ValidEmail;

/**
 *
 * @author Stas
 */
@PasswordMatches
public class UserDto {
    
    private int userId;
    
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30)
    @Pattern(message="Invalid symbols. Please, use only letters", regexp="^[a-zA-Z]+$")
    private String firstName;
    
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30)
    @Pattern(message="Invalid symbols. Please, use only letters", regexp="^[a-zA-Z]+$")
    private String lastName;
    
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
    
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;
    
    @NotNull
    @NotEmpty
    private String matchingPassword;
    
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 30)
    private String companyName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}

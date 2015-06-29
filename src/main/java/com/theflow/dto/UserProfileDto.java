package com.theflow.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Stas
 */
public class UserProfileDto {
    
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
    
    private String fullName;
    
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

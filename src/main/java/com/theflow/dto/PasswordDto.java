/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Stas
 */
public class PasswordDto {
    
    private int userId;
    
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;
    
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    private String newPassword;
    
    @NotNull
    @NotEmpty
    private String matchingPassword;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}

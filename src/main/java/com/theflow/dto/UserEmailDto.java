/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import validation.ValidEmail;

/**
 *
 * @author Stanislav
 */
public class UserEmailDto {
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    public UserEmailDto(String email) {
        this.email = email;
    }
    public UserEmailDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

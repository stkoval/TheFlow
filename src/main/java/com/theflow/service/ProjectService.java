/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * @author Stas
 */
public interface ProjectService {
    public String getAssigneeList(int projectId) throws JsonProcessingException;
}

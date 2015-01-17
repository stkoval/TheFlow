/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.theflow.domain.Project;
import java.util.List;


/**
 *
 * @author Stas
 */
public interface ProjectService {

    public List<Project> getProjectList();
}

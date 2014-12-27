/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theflow.dao.ProjectDao;
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.dto.UserDTO;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stas
 */
@Service
public class ProjectServiceImpl implements ProjectService{
    
    @Autowired
    private ProjectDao projectDao;

    @Override
    public String getAssigneeList(int projectId) throws JsonProcessingException {
        System.out.println("**************** projectid: " + projectId);
        Project project = projectDao.getProject(projectId);
        if (project == null) return ""; 
        
        Set<User> assignees = project.getAddedUsers();
        
        Set<UserDTO> usersDTO = new HashSet<>();
        UserDTO userDTO = null;
        for (User user : assignees) {
            userDTO = new UserDTO();
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setLogin(user.getLogin());
            userDTO.setUserId(user.getUserId());
            usersDTO.add(userDTO);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonAssignee = mapper.writeValueAsString(usersDTO);
        System.out.println("****************" + jsonAssignee);
        return jsonAssignee;
    } 
    
}

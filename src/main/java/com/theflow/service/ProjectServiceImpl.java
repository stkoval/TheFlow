package com.theflow.service;

import com.theflow.dao.CompanyDao;
import com.theflow.dao.ProjectDao;
import com.theflow.domain.Project;
import com.theflow.dto.ProjectDto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validation.ProjectNameExistsException;

/**
 *
 * @author Stas
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private CompanyDao companyDao;
    
    @Autowired
    MessageSource messageSource; 

    @Override
    public List<Project> getProjectList() {
        return projectDao.getProjectList();
    }

    @Override
    public void saveProject(ProjectDto projectDto) throws ProjectNameExistsException {
        if (projectNameExists(projectDto.getProjName())) {
            throw new ProjectNameExistsException(messageSource.getMessage("message.project.exists", null, Locale.ENGLISH)
                    + projectDto.getProjName());
        }
        FlowUserDetailsService.User principal
                        = (FlowUserDetailsService.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        int companyId = principal.getCompanyId();
        Project project = new Project();
        project.setProjName(projectDto.getProjName());
        project.setProjDescription(projectDto.getProjDescription());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date startDate = null;
        Date releaseDate = null;
        if (projectDto.getStartDate() != null || !projectDto.getStartDate().equals("")) {
            try {
                startDate = df.parse(projectDto.getStartDate());
            } catch (ParseException ex) {
                Logger.getLogger(ProjectServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (projectDto.getReleaseDate() != null || !projectDto.getReleaseDate().equals("")) {
            try {
                releaseDate = df.parse(projectDto.getReleaseDate());
            } catch (ParseException ex) {
                Logger.getLogger(ProjectServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        project.setStartDate(startDate);
        project.setReleaseDate(releaseDate);
        project.setCompanyId(companyId);
        project.setActive(true);
        projectDao.saveProject(project);
    }

    @Override
    public void removeProject(int id) {
        projectDao.removeProject(id);
    }

    @Override
    public Project getProjectById(int id) {
        return projectDao.getProjectById(id);
    }

    @Override
    public void updateProject(ProjectDto projectDto) throws ProjectNameExistsException {
        Project project = projectDao.getProjectById(projectDto.getProjectId());
        if (!project.getProjName().equals(projectDto.getProjName())) {
            
            //checking if project name is already exist
            if (projectNameExists(projectDto.getProjName())) {
                throw new ProjectNameExistsException("There is a project with that name already added: "
                        + projectDto.getProjName());
            } else {
                project.setProjName(projectDto.getProjName());
            }
        }
        project.setProjDescription(projectDto.getProjDescription());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date startDate = null;
        Date releaseDate = null;
        if (projectDto.getStartDate() != null || !projectDto.getStartDate().equals("")) {
            try {
                startDate = df.parse(projectDto.getStartDate());
            } catch (ParseException ex) {
                Logger.getLogger(ProjectServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (projectDto.getReleaseDate() != null || !projectDto.getReleaseDate().equals("")) {
            try {
                releaseDate = df.parse(projectDto.getReleaseDate());
            } catch (ParseException ex) {
                Logger.getLogger(ProjectServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        project.setStartDate(startDate);
        project.setReleaseDate(releaseDate);
        projectDao.updateProject(project);
    }

    private boolean projectNameExists(String projName) {
        Project project = projectDao.findByName(projName);
        if (project != null) {
            return true;
        }
        return false;
    }
}

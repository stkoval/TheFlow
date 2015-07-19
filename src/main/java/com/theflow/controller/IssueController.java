package com.theflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theflow.domain.Issue;
import com.theflow.domain.IssueAttachment;
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.dto.IssueDto;
import com.theflow.dto.IssueSearchParams;
import com.theflow.service.FlowEmailService;
import com.theflow.service.IssueAttachmentService;
import com.theflow.service.IssueService;
import com.theflow.service.ProjectService;
import com.theflow.service.UserService;
import helpers.TimeParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import validation.IssueAttachmentConstraintViolationException;
import validation.ProjectRequiredException;

/**
 *
 * @author Stas
 */
@Controller
public class IssueController {

    static final Logger logger = Logger.getLogger(IssueController.class.getName());

    @Autowired
    private IssueService issueService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private IssueAttachmentService issueAttachmentService;
    
    @Autowired
    FlowEmailService mailService;

    private String attachPath = "/home/stas/workspace/flow_uploads/issue_attach/";

    private static final int BUFFER_SIZE = 1048576;

    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private ServletContext servletContext;

    //searching issue header smart search
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "issues/search")
    public String searchIssues(@RequestParam(value = "filter", required = false) String[] filter,
            @RequestParam(value = "project_id", required = false) Integer projectId
    ) {
        List<IssueDto> issues = issueService.searchIssues(new IssueSearchParams(filter, projectId));

        String issuesString = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            issuesString = mapper.writeValueAsString(issues);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(IssueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return issuesString;
    }

    //saving issue after issue creation form is populated
    @PreAuthorize("hasAnyRole('Admin','User')")
    @RequestMapping(value = "issue/save", method = RequestMethod.POST)
    public ModelAndView saveIssue(@ModelAttribute(value = "issue") @Valid IssueDto issueDto, BindingResult result, @RequestParam(required = false) CommonsMultipartFile[] fileUpload) {

        ModelAndView model = new ModelAndView("redirect:/home");

        if (fileUpload != null && fileUpload.length > 0) {
            try {
                issueService.uploadAttachment(fileUpload, issueDto.getIssueId());
            } catch (IssueAttachmentConstraintViolationException e) {
                model.addObject("error", messageSource.getMessage("error.issue.attachment", null, Locale.ENGLISH));
            }
        }

        try {
            issueService.saveIssue(issueDto);
        } catch (ProjectRequiredException ex) {
            model.addObject("message", messageSource.getMessage("message.project.required", null, Locale.ENGLISH));
        }

        return model;
    }

    @PreAuthorize("hasAnyRole('Admin','User')")
    @RequestMapping("issue/add")
    public ModelAndView showCreateIssueForm() {
        ModelAndView model = new ModelAndView("issue/addissue", "issue", new IssueDto());
        List<String> types = issueService.getIssueTypes();
        List<String> statuses = issueService.getIssueStatuses();
        List<String> priorities = issueService.getIssuePriorities();
        List<User> assignees = userService.getAllUsers();
        List<Project> projects = projectService.getProjectList();

        model.addObject("statuses", statuses);
        model.addObject("types", types);
        model.addObject("priorities", priorities);
        model.addObject("assignees", assignees);
        model.addObject("projects", projects);

        return model;
    }

    @PreAuthorize("hasAnyRole('Admin','User')")
    @RequestMapping(value = "issue/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeIssue(@PathVariable(value = "id") int issueId) {
        issueService.removeIssue(issueId);
        return new ModelAndView("redirect:/home");
    }

    //get all issues related to company
    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/all", method = RequestMethod.GET)
    public ModelAndView getAllIssues() {
        ModelAndView model = new ModelAndView("issue/table");
        List<Issue> issues = issueService.getAllIssues();
        if (issues == null) {
            String message = "There are no issues created";
            model.addObject("message", message);
            return model;
        }
        List<Project> projects = projectService.getProjectList();
        model.addObject("issues", issues);

        model.addObject("projects", projects);

        List<User> users = userService.getAllUsers();
        model.addObject("users", users);

        return model;
    }

    @PreAuthorize("hasAnyRole('Admin','User')")
    @RequestMapping(value = "issue/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showIssueEditForm(@PathVariable(value = "id") int issueId) {

        ModelAndView model = new ModelAndView("issue/edit");
        Issue issue = issueService.getIssueById(issueId);
        List<String> types = issueService.getIssueTypes();
        List<String> statuses = issueService.getIssueStatuses();
        List<String> priorities = issueService.getIssuePriorities();
        List<User> users = userService.getAllUsers();
        List<Project> projects = projectService.getProjectList();
        IssueDto issueDto = issueService.populateIssueDtoFildsFromIssue(issue, issueId);

        model.addObject("statuses", statuses);
        model.addObject("types", types);
        model.addObject("priorities", priorities);
        model.addObject("assignees", users);
        model.addObject("projects", projects);
        model.addObject("issue", issueDto);

        return model;
    }

    //updating issue after issue edit form is populated
    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/update", method = RequestMethod.POST)
    public ModelAndView updateIssue(@ModelAttribute(value = "issue") @Valid IssueDto issueDto, BindingResult result, @RequestParam(value = "fileUpload", required = false) CommonsMultipartFile[] fileUpload) {

        ModelAndView model = new ModelAndView("redirect:/home");

        if (fileUpload != null && fileUpload.length > 0) {
            try {
                issueService.uploadAttachment(fileUpload, issueDto.getIssueId());
            } catch (IssueAttachmentConstraintViolationException e) {
                model.addObject("error", messageSource.getMessage("error.issue.attachment", null, Locale.ENGLISH));
            }
        }
        issueService.updateIssue(issueDto);
        return new ModelAndView("redirect:/home");
    }

    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/details/{id}", method = RequestMethod.GET)
    public ModelAndView showIssueDetailsPage(@PathVariable(value = "id") int issueId) {
        ModelAndView model = new ModelAndView("issue/details");
        Issue issue = issueService.getIssueById(issueId);

        //persent variables for issue details time management progress bars
        int estimated = TimeParser.parse(issue.getEstimatedTime());
        int logged = TimeParser.parse(issue.getLoggedTime());
        double pEst = 0; //persent values
        double pLogged = 0;
        if (estimated == logged && estimated != 0) {
            pEst = 100;
            pLogged = 100;
        } else if (estimated == 0 && logged > 0) {
            pLogged = 100;
        } else if (estimated > 0 && logged == 0) {
            pEst = 100;
        } else if (estimated > 0 && logged > estimated) {
            pLogged = 100;
            pEst = (1d * estimated / logged) * 100;
        } else if (logged > 0 && logged < estimated) {
            pEst = 100;
            pLogged = (1d * logged / estimated) * 100;
        }

        Set<IssueAttachment> attachments = issue.getAttachment();
        if (attachments != null && attachments.size() > 0) {
            model.addObject("attachments", attachments);
        }

        model.addObject("issue", issue);
        model.addObject("estimated", pEst);
        model.addObject("logged", pLogged);
        return model;
    }

    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "/{id}/logtime", method = RequestMethod.POST)
    public ModelAndView logWork(@PathVariable("id") int issueId, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("redirect:/issue/details/" + issueId);
        String sLoggedTime = request.getParameter("logwork");
        Issue issue = issueService.getIssueById(issueId);
        if (sLoggedTime == null) {
            sLoggedTime = "00:00";
        }
        issue.setLoggedTime(sLoggedTime);
        issueService.updateIssue(issue);
        return model;
    }

    //Assign issue to current authenticated user
    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/assign/{id}", method = RequestMethod.GET)
    public ModelAndView assignToCurrentUser(@PathVariable(value = "id") int issueId) {
        issueService.assignToCurrentUser(issueId);
        return new ModelAndView("redirect:/home");
    }

    //Get issues related to project with projectId = id
    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/byproject/{id}", method = RequestMethod.GET)
    public ModelAndView getIssuesByProjectId(@PathVariable(value = "id") int projectId) {
        ModelAndView model = new ModelAndView("/issue/table");
        List<Issue> issues = issueService.getIssuesByProjectId(projectId);
        if (issues == null) {
            String message = "There are no issues created";
            model.addObject("message", message);
            return model;
        }
        List<Project> projects = projectService.getProjectList();
        model.addObject("issues", issues);

        model.addObject("projects", projects);

        List<User> users = userService.getAllUsers();
        model.addObject("users", users);

        model.addObject("projectId", projectId);

        return model;
    }

    //change issue status
    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/{id}/status", method = RequestMethod.GET)
    public ModelAndView changeIssueStatus(@RequestParam(value = "status") String status,
            @PathVariable(value = "id") int issueId) {
        status = status.replace('_', ' ');
        issueService.changeIssueStatus(status, issueId);
        return new ModelAndView("redirect:/home");
    }

    //change issue type
   @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/{id}/type", method = RequestMethod.GET)
    public ModelAndView changeIssueType(@RequestParam(value = "type") String type,
            @PathVariable(value = "id") int issueId) {
        issueService.changeIssueType(type, issueId);
        return new ModelAndView("redirect:/home");
    }

    //change issue priority
    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/{id}/priority", method = RequestMethod.GET)
    public ModelAndView changeIssuePriority(@RequestParam(value = "priority") String priority,
            @PathVariable(value = "id") int issueId) {
        issueService.changeIssuePriority(priority, issueId);
        return new ModelAndView("redirect:/home");
    }

    //change issue assignee
    @PreAuthorize("hasAnyRole('Admin','User', 'Observer')")
    @RequestMapping(value = "issue/{id}/assignee", method = RequestMethod.GET)
    public ModelAndView changeIssueAssignee(@RequestParam(value = "assignee") int userId,
            @PathVariable(value = "id") int issueId) {
        issueService.changeIssueAssignee(userId, issueId);
        return new ModelAndView("redirect:/home");
    }

    //Upload and save attached files
    @RequestMapping(value = "/issue/{id}/upload", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(HttpServletRequest request,
            @RequestParam(value = "fileUpload", required = false) CommonsMultipartFile[] fileUpload, @PathVariable(value = "id") int issueId) throws Exception {

        ModelAndView model = new ModelAndView("redirect:/issue/details/" + issueId);

        try {
            issueService.uploadAttachment(fileUpload, issueId);
        } catch (IssueAttachmentConstraintViolationException e) {
            model.addObject("error", messageSource.getMessage("error.issue.attachment", null, Locale.ENGLISH));
        }
        return model;
    }

    //Download attached file
    @PreAuthorize("hasAnyRole('Admin','User')")
    @RequestMapping(value = "/attachment/{id}/download", method = RequestMethod.GET)
    public void handleFileDownload(@PathVariable(value = "id") int attachmentId,
            HttpServletRequest request, HttpServletResponse response) {

        IssueAttachment attach = issueAttachmentService.getIssueAttachmentById(attachmentId);
        String fullPath = attachPath + attach.getIssue().getIssueId() + "_" + attach.getFileName();
        ServletContext context = request.getServletContext();

        File downloadFile = new File(fullPath);
        FileInputStream inputStream = null;
        OutputStream outStream = null;

        try {
            inputStream = new FileInputStream(downloadFile);

            response.setContentLength((int) downloadFile.length());
            response.setContentType(context.getMimeType(fullPath) + ";charset=UTF-8");

            // response header
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=\"" + attach.getFileName() + "\"";
            response.setHeader(headerKey, headerValue);

            // Write response
            outStream = response.getOutputStream();
            IOUtils.copy(inputStream, outStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != inputStream) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    
    //Remove attached file
    @PreAuthorize("hasAnyRole('Admin','User')")
    @RequestMapping(value = "/attachment/{id}/remove", method = RequestMethod.GET)
    public ModelAndView handleFileRemove(@PathVariable(value = "id") int attachmentId) {

        IssueAttachment attach = issueAttachmentService.getIssueAttachmentById(attachmentId);
        ModelAndView model = new ModelAndView("redirect:/issue/details/" + attach.getIssue().getIssueId());
        
        String fullPath = attachPath + attach.getIssue().getIssueId() + "_" + attach.getFileName();
        File toRemove = new File(fullPath);
        boolean exists = toRemove.exists();
        toRemove.delete();
        Issue issue = attach.getIssue();
        Set<IssueAttachment> attachSet = issue.getAttachment();
        boolean b = attachSet.contains(attach);
        attachSet.remove(attach);
        
        issueService.updateIssue(issue);
        
        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, HibernateException exception) {
        logger.error("Request: " + req.getRequestURL() + " exception " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error/error");
        return mav;
    }
}

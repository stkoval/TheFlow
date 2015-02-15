package com.theflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theflow.domain.Issue;
import com.theflow.domain.Project;
import com.theflow.domain.User;
import com.theflow.dto.IssueDto;
import com.theflow.dto.IssueSearchParams;
import com.theflow.service.IssueService;
import com.theflow.service.ProjectService;
import com.theflow.service.UserService;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "issue/save", method = RequestMethod.POST)
    public ModelAndView saveIssue(@ModelAttribute(value = "issue") @Valid IssueDto issueDto, BindingResult result) {

        issueService.saveIssue(issueDto);

        return new ModelAndView("redirect:/home");
    }

    //creating new issue
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

    //removes issue from database
    @RequestMapping(value = "issue/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeIssue(@PathVariable int id) {
        issueService.removeIssue(id);
        return new ModelAndView("redirect:/home");
    }

    //get all issues related to Company
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
        return model;
    }

    @RequestMapping(value = "issue/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editIssue(@PathVariable int id) {

        ModelAndView model = new ModelAndView("issue/edit");
        Issue issue = issueService.getIssueById(id);
        List<String> types = issueService.getIssueTypes();
        List<String> statuses = issueService.getIssueStatuses();
        List<String> priorities = issueService.getIssuePriorities();
        List<User> users = userService.getAllUsers();
        List<Project> projects = projectService.getProjectList();
        IssueDto issueDto = issueService.populateIssueDtoFildsFromIssue(issue, id);

        model.addObject("statuses", statuses);
        model.addObject("types", types);
        model.addObject("priorities", priorities);
        model.addObject("users", users);
        model.addObject("projects", projects);
        model.addObject("issue", issueDto);

        return model;
    }

    @RequestMapping(value = "issue/update", method = RequestMethod.POST)
    public ModelAndView updateIssue(@ModelAttribute(value = "issue") @Valid IssueDto issueDto, BindingResult result) {

        issueService.updateIssue(issueDto);
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "issue/details/{id}", method = RequestMethod.GET)
    public ModelAndView showIssueDetails(@PathVariable int id) {
        ModelAndView model = new ModelAndView("issue/details");
        Issue issue = issueService.getIssueById(id);
        model.addObject("issue", issue);
        return model;
    }

    @RequestMapping(value = "issue/assign/{issue_id}", method = RequestMethod.GET)
    public ModelAndView assignToCurrentUser(@PathVariable int issue_id) {
        issueService.assignToCurrentUser(issue_id);
        return new ModelAndView("redirect:/home");
    }

    
    @RequestMapping(value = "issue/byproject/{id}", method = RequestMethod.GET)
    public ModelAndView getIssuesByProjectId(@PathVariable int id) {
        ModelAndView model = new ModelAndView("/issue/table");
        List<Issue> issues = issueService.getIssuesByProjectId(id);
        if (issues == null) {
            String message = "There are no issues created";
            model.addObject("message", message);
            return model;
        }
        List<Project> projects = projectService.getProjectList();
        model.addObject("issues", issues);

        model.addObject("projects", projects);
        return model;
    }
            
            
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " exception " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error/error");
        return mav;
    }
}

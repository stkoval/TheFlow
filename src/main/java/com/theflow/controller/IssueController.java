package com.theflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theflow.domain.Issue;
import com.theflow.dto.IssueDTO;
import com.theflow.dto.IssueSearchCriteria;
import com.theflow.service.IssueService;
import java.util.List;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    //searching issue header smart search
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "issues/search")
    public String searchIssues(@RequestParam(value = "new") boolean statusNew,
            @RequestParam(value = "to_me") boolean toMe,
            @RequestParam(value = "high") boolean high,
            @RequestParam(value = "task") boolean task,
            @RequestParam(value = "bug") boolean bug,
            @RequestParam(value = "all") boolean all,
            @RequestParam(value = "project_id") int projectId
    ) {
        List<IssueDTO> issues = issueService.searchIssues(new IssueSearchCriteria(statusNew, toMe, high, task, bug, all, projectId));
//        ModelAndView model = new ModelAndView("issue/table");
//        if (issues == null) {
//            String message = "There are no requested issues found";
//            model.addObject("message", message);
//            return model;
//        }

//        model.addObject("issues", issues);
        String issuesString = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            issuesString = mapper.writeValueAsString(issues);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(IssueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return issuesString;
    }

    //creating new issue
    @RequestMapping(value = "issue/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute(value = "issue") IssueDTO issueDTO, BindingResult result) {

        issueService.saveIssue(issueDTO);

        return "home/home";
    }

    //show issue creation page
    @RequestMapping("issue/add")
    public ModelAndView addIssueForm() {
        return new ModelAndView("issue/addissue", "issue", new IssueDTO());
    }

    //removes issue from database
    @RequestMapping("issue/remove/{id}")
    public void removeIssue(@RequestParam int id) {
        issueService.removeIssue(id);
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
        model.addObject("issues", issues);
        return model;
    }
}

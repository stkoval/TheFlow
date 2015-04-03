package com.theflow.controller;

import com.theflow.domain.Company;
import com.theflow.domain.UserCompany;
import com.theflow.dto.CompanyDto;
import com.theflow.dto.UserProfileDto;
import com.theflow.service.CompanyService;
import com.theflow.service.UserService;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import validation.CompanyNotFoundException;

/**
 *
 * @author Stas
 */
@Controller
public class CompanyController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CompanyService companyService;
    
    //shows page with companies where current user is creator
    @PreAuthorize("hasRole('Cabinet')")
    @RequestMapping(value = "/companies/own", method = RequestMethod.GET)
    public ModelAndView showOwnCompanies(){
        List<UserCompany> userCompanies = userService.getOwnCompanies();
        ModelAndView model = new ModelAndView("/user/own_companies");
        model.addObject("companies", userCompanies);
        return model;
    }
    
    @PreAuthorize("hasRole('Cabinet')")
    @RequestMapping(value = "/company/edit/{companyId}", method = RequestMethod.GET)
    public ModelAndView editCompany(@PathVariable(value = "companyId") int companyId) {
        Company company = null;
        try {
            company = companyService.getCompanyById(companyId);
        } catch (CompanyNotFoundException ex) {
            ModelAndView model = new ModelAndView("redirect:/user/companies/own");
            model.addObject("message", messageSource.getMessage("message.company.notfound", null, Locale.ENGLISH));
        }
        if (company.getCreator().getUserId() != userService.getPrincipal().getUserId()) {
            ModelAndView model = new ModelAndView("redirect:/user/companies/own");
            model.addObject("message", messageSource.getMessage("message.norights", null, Locale.ENGLISH));
        }
        ModelAndView model = new ModelAndView("/company/edit");
        CompanyDto companyDto = new CompanyDto(company.getName(), company.getAlias());
        companyDto.setCompanyId(companyId);
        model.addObject("company", company);
        return model;
    }
    
    @PreAuthorize("hasRole('Cabinet')")
    @RequestMapping(value = "/company/update", method = RequestMethod.POST)
    public ModelAndView updateCompany(@ModelAttribute(value = "company") @Valid CompanyDto companyDto, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("/company/edit", "user", companyDto);
        }
        companyService.updateCompany(companyDto);
        return new ModelAndView("user/own_companies");
    }

}

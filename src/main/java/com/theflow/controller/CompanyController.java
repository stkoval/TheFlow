package com.theflow.controller;

import com.theflow.domain.Company;
import com.theflow.domain.UserCompany;
import com.theflow.dto.CompanyDto;
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
import validation.CompanyAliasExistsException;
import validation.CompanyExistsException;
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
        model.addObject("company", companyDto);
        return model;
    }
    
    @PreAuthorize("hasRole('Cabinet')")
    @RequestMapping(value = "/company/update", method = RequestMethod.POST)
    public ModelAndView updateCompany(@ModelAttribute(value = "company") @Valid CompanyDto companyDto, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("/company/edit", "user", companyDto);
        }
        try {
            companyService.updateCompany(companyDto);
        } catch (CompanyNotFoundException ex) {
            ModelAndView model = new ModelAndView("redirect:/companies/own");
            model.addObject("error", messageSource.getMessage("message.company.notfound", null, Locale.ENGLISH));
        } catch (CompanyAliasExistsException ex1) {
            ModelAndView model = new ModelAndView("redirect:/companies/own");
            model.addObject("error", messageSource.getMessage("message.company.alias.exists", null, Locale.ENGLISH) + " " + companyDto.getCompanyAlias());
        } catch (CompanyExistsException ex2) {
            ModelAndView model = new ModelAndView("redirect:/companies/own");
            model.addObject("error", messageSource.getMessage("message.company.name.exists", null, Locale.ENGLISH) + " " + companyDto.getCompanyName());
        }
        ModelAndView model = new ModelAndView("redirect:/companies/own");
        return model;
    }
    
    @PreAuthorize("hasRole('Cabinet')")
    @RequestMapping(value = "/company/remove/{companyId}", method = RequestMethod.GET)
    public ModelAndView removeCompany(@PathVariable(value = "companyId") int companyId) {
        companyService.removeCompany(companyId);
        ModelAndView model = new ModelAndView("redirect:/companies/own");
        return model;
    }
    
    //add new company from cabinet
    @PreAuthorize("hasRole('Cabinet')")
    @RequestMapping(value = "user/addcompany", method = RequestMethod.GET)
    public ModelAndView showAddNewCompanyForm() {
        ModelAndView model = new ModelAndView("user/addcompany");
        CompanyDto company = new CompanyDto();
        model.addObject("company", company);
        return model;
    }

    //add new company from cabinet
    @PreAuthorize("hasRole('Cabinet')")
    @RequestMapping(value = "user/savecompany", method = RequestMethod.POST)
    public ModelAndView saveNewCompanyFromCabinet(@ModelAttribute("company") @Valid CompanyDto companyDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("user/addcompany", "company", companyDto);
        }
        try {
            userService.saveNewCompanyFromCabinet(companyDto);
        } catch (CompanyExistsException ex) {
            result.rejectValue("companyName", "message.companyError");
            return new ModelAndView("user/addcompany", "company", companyDto);
        } catch (CompanyAliasExistsException ex) {
            result.rejectValue("companyAlias", "message.companyAliasError");
            return new ModelAndView("user/addcompany", "company", companyDto);
        }
        ModelAndView model = new ModelAndView("redirect:/cabinet");
        model.addObject("message", messageSource.getMessage("message.company.add.success", null, Locale.ENGLISH) + " " + companyDto.getCompanyName());

        return model;
    }
}

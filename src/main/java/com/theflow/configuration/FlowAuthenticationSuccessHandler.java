/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.configuration;

import com.theflow.service.FlowUserDetailsService;
import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 *
 * @author Stas
 */
public class FlowAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(javax.servlet.http.HttpServletRequest request,
                           javax.servlet.http.HttpServletResponse response,
                           Authentication authentication) throws ServletException, IOException {
        request.getSession().setAttribute("subdomain", ((FlowUserDetailsService.User)authentication.getPrincipal()).getCompanyAlias());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

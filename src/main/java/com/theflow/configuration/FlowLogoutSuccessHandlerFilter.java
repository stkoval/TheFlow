package com.theflow.configuration;

import com.theflow.service.FlowUserDetailsService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 *
 * @author Stas
 */
public class FlowLogoutSuccessHandlerFilter implements LogoutSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void logout(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) {

    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        StringBuilder uri = new StringBuilder("login?logout");//new StringBuilder(request.getRequestURI()).append("/");
        StringBuilder redirect = new StringBuilder("");

        FlowUserDetailsService.User principal = (FlowUserDetailsService.User)authentication.getPrincipal();
        String subdomain = principal.getCompanyAlias();
        
        if (subdomain != null && subdomain.length() > 0) {
            redirect.append(subdomain).append("/").append(uri);
        } else { 
            redirect.append("/index");
        }

        redirectStrategy.sendRedirect(request, response, redirect.toString());
    }
}

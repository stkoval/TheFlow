package com.theflow.configuration;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * filter combines username and subdomain for authentication by subdomain
 * 
 * @author Stas
 */
//@Component
public class TwoFactorAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private static final String EXTRA_PARAMETER = "companyAlias";
    private static final String USERNAME_PARAMETER = "username";
    
    private AuthenticationManager authenticationManager;
    
    private String delimiter = ":";
    
    /**
     * Given an {@link HttpServletRequest}, this method extracts the username and subdomain
     * values and returns a combined username string of those values separated by the delimiter
     * string.
     * @param request
     * @return 
     */
    @Override
    protected String obtainUsername(HttpServletRequest request)
    {
        String username = request.getParameter(USERNAME_PARAMETER);
        String subdomain = request.getParameter(EXTRA_PARAMETER);

        String combinedUsername = username + getDelimiter() + subdomain;

        System.out.println("Combined username = " + combinedUsername);
        return combinedUsername;
    }

    public String getDelimiter()
    {
        return this.delimiter;
    }

    public void setDelimiter(String delimiter)
    {
        this.delimiter = delimiter;
    }
}

package com.theflow.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    
    static final Logger log = Logger.getLogger(TwoFactorAuthenticationFilter.class.getName());
    
    private String delimiter = ":";
    
//    public TwoFactorAuthenticationFilter() {
//        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
//    }


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
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        AuthenticationManager manager = this.getAuthenticationManager();
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
    
}

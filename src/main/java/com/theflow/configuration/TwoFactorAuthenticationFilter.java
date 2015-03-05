package com.theflow.configuration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * filter combines username and subdomain for authentication by subdomain
 * 
 * @author Stas
 */
//@Component
public class TwoFactorAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
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
        String username = request.getParameter(getUsernameParameter());
        String subdomain = "";
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("company")) {
                    subdomain = cookie.getName();
                    break;
                }
            }
        }

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

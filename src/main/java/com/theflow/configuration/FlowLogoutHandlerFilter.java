package com.theflow.configuration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 *
 * @author Stas
 */
public class FlowLogoutHandlerFilter implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) {

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

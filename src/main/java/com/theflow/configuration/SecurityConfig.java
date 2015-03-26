package com.theflow.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author Stas
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/index").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/cabinet_login").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers("/signin/registration").permitAll()
                .antMatchers("/home/landing").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/user/saveaccount").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin().defaultSuccessUrl("/home", true)
                .loginPage("/login").loginProcessingUrl("/j_spring_security_check")
                .and()
                .addFilterBefore(authenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .logout().logoutSuccessUrl("/index").logoutUrl("/logout").deleteCookies("filterFlow")
                .and()
                .sessionManagement()
                .maximumSessions(1);
        http
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/cabinet_login").permitAll()
                .and()
                .formLogin().defaultSuccessUrl("/cabinet", true)
                .loginPage("/cabinet_login").loginProcessingUrl("/cabinet_login_process");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TwoFactorAuthenticationFilter authenticationFilter() throws Exception {
        TwoFactorAuthenticationFilter authFilter = new TwoFactorAuthenticationFilter();
        authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/j_spring_security_check", "POST"));
        authFilter.setUsernameParameter("username");
        authFilter.setPasswordParameter("password");
        authFilter.setAuthenticationSuccessHandler(SavedRequestAwareAuthenticationSuccessHandler());
        authFilter.setAuthenticationFailureHandler(SimpleUrlAuthenticationFailureHandler());
        authFilter.setAuthenticationManager(authenticationManager());
        return authFilter;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler SavedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/home");
        handler.setAlwaysUseDefaultTargetUrl(true);
        return handler;
    }
    
    @Bean
    public SimpleUrlAuthenticationFailureHandler SimpleUrlAuthenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl("/login?error");
        return handler;
    }

    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        LoginUrlAuthenticationEntryPoint loginUrl = new LoginUrlAuthenticationEntryPoint("/cabinet_login");
        return loginUrl;
    }
    
    @Bean
    LogoutFilter logoutFilter() {
        LogoutHandler[] handlers = {securityContextLogoutHandler(), logoutHandlerFilter()};
        LogoutFilter filter = new LogoutFilter(logoutSuccessHandlerFilter(), handlers);
        filter.setLogoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        return filter;
    }
    
    @Bean
    SecurityContextLogoutHandler securityContextLogoutHandler() {
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();
        handler.setInvalidateHttpSession(true);
        return handler;
    }

    @Bean
    FlowLogoutSuccessHandlerFilter logoutSuccessHandlerFilter() {
        FlowLogoutSuccessHandlerFilter filter = new FlowLogoutSuccessHandlerFilter();
        return filter;
    }
    
    @Bean
    FlowLogoutHandlerFilter logoutHandlerFilter() {
        FlowLogoutHandlerFilter filter = new FlowLogoutHandlerFilter();
        return filter;
    }
}

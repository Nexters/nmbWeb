package com.teamnexters.nmbweb.security.config;

import com.teamnexters.nmbweb.security.handler.LoginFailureHandler;
import com.teamnexters.nmbweb.security.handler.LoginSuccessHandler;
import com.teamnexters.nmbweb.security.handler.LogoutSuccessHandler;
import com.teamnexters.nmbweb.security.service.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.teamnexters.nmbweb.security.handler.RestAuthenticationEntryPoint;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by limjuhyun on 8/2/16.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // register ApiUserDetailsService as a bean
    @Bean
    public UserDetailsService apiUserDetailsService() {
        return new UserLogin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(apiUserDetailsService()); //.passwordEncoder(new BCryptPasswordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                .and()
                    .formLogin()
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .successHandler(new LoginSuccessHandler())
                        .failureHandler(new LoginFailureHandler())
                .and()
                    .logout()
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler(new LogoutSuccessHandler())
                .and()
                    .csrf()
                        .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint());
    }
}

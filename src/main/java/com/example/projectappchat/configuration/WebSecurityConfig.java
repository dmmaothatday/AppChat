package com.example.projectappchat.configuration;


import com.example.projectappchat.entity.User;
import com.example.projectappchat.service.user.UserService;
import com.example.projectappchat.service.userDetails.UserDetailsServiceImpl;
import com.example.projectappchat.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Set service to find User in database
        //Set PasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //hạn chế được việc tấn công Csrf
        http.csrf().disable();

        //Page dont require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout", "/index")
                .permitAll();

        //Page /userInfo require User have role is ROLE_USER or ROLE_ADMIN
        //If not login yet, it will be redirect to /login
        http.authorizeRequests().antMatchers("/userInfo")
                .permitAll();

        //Config for Login Form
        http.authorizeRequests().and().formLogin()//
                //submit url of login page
                .loginProcessingUrl("/j_spring_security_check") //submit url
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, javax.servlet.ServletException {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        User user = userService.findUserByAccount(userDetails.getUsername());
                        user.setOnline(AppUtils.isOnline);
                        userService.save(user);
                        response.sendRedirect("/");
                    }
                })
                .failureUrl("/login-failed")
                //config for Logout page
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, javax.servlet.ServletException {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        User user = userService.findUserByAccount(userDetails.getUsername());
                        user.setOnline(AppUtils.isOffline);
                        userService.save(user);
                        response.sendRedirect("/logoutSuccessful");
                    }
                })
                .logoutSuccessUrl("/logoutSuccessful");




    }



}

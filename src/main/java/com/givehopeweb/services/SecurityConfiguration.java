package com.givehopeweb.services;

import com.givehopeweb.models.UserWithRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by David on 2/22/17.
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = UserWithRole.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsLoader userDetails;

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
            .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/search",
                        "/register",
                        "/charities",
                        "/donate",
                        "/logout") // anyone can see these pages
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/login?logout") // append a query string value
            .and()
                .authorizeRequests()
                .antMatchers("/profile") // only authenticated users can view their profile
                .authenticated()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }
}

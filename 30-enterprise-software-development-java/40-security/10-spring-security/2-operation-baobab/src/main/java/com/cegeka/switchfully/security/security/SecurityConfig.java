package com.cegeka.switchfully.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfig(AuthenticationEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    /**
     * Question about {noop}? --> https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#pe-dpe
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("ZWANETTA").password("{noop}WORST").roles("CIVILIAN")
                .and()
                .withUser("JMILLER").password("{noop}THANKS").roles("PRIVATE")
                .and()
                .withUser("UNCLE").password("{noop}SAM").roles("HUMAN_RELATIONSHIPS")
                .and()
                .withUser("GENNY").password("{noop}RALLY").roles("GENERAL");
    }

}

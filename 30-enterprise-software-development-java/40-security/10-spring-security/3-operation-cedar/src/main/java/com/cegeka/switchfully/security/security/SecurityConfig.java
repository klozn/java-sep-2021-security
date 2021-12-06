package com.cegeka.switchfully.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
     * (Currently, we're not using this method. If used, call it from inside the configure method, uncomment everything currently in the configure method).
     * One way to fix the authorization problem is using the 'antmatchers' methods to force users to have certain role(s) if they want to access a certain endpoint
     * advantage: able to secure multiple, similar url's at the same time
     * disadvantage: this code is completely decoupled from the Rest-controller code. This makes it easy to forget to adjust it when e.g. adding a new rest-call
     */
    private void configureAntMatchers(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/army").hasRole("CIVILIAN")
                .antMatchers("/army/promote/**").hasRole("HUMAN_RELATIONSHIPS")
                .antMatchers("/army/discharge/**").hasRole("HUMAN_RELATIONSHIPS")
                .antMatchers("/army/nuke").hasRole("GENERAL")
                .antMatchers("/army/**").hasAnyRole("PRIVATE", "GENERAL")
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

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

package com.data.kaveri.dataexchange.security;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();

    }

}
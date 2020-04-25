package org.personal.salesmgmt.config;

import lombok.RequiredArgsConstructor;
import org.personal.salesmgmt.filters.HttpRequestFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final HttpRequestFilter httpRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/sales/users/**").hasRole("USER")
                .antMatchers("/api/sales/admin/**", "/api/sales/**").hasRole("ADMIN");
        http.addFilterBefore(httpRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
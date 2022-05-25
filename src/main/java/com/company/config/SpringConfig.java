package com.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // authentication
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}adminjon").roles("admin")
                .and()
                .withUser("profile").password("{noop}profilejon").roles("profile")
                .and()
                .withUser("bankjon").password("{noop}bankov").roles("bank");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorization
        http.authorizeRequests()
//                .antMatchers("/client/*").permitAll()
//                .antMatchers("/adm/*").hasRole("admin")
//                .antMatchers("/card/*").hasRole("profile")
                .anyRequest().authenticated()
                .and().httpBasic();
        http.csrf().and().cors().disable();

//                .and().formLogin();

    }

}

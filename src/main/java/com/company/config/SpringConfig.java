package com.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class SpringConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // authentication
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}adminjon").roles("admin")
                .and()
                .withUser("profile").password("{noop}profilejon").roles("profile")
                .and()
                .withUser("bank").password("{noop}bankjon").roles("bank");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorization

        http.authorizeRequests()
                .antMatchers("/client/profile/**").hasRole("profile")
//                .antMatchers("/adm/*").hasRole("admin")
                .antMatchers("/card/bank/**").hasRole("bank")
                .antMatchers("/card/**").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();

        http.csrf().disable().cors().disable();

//                .and().formLogin();
    }

}

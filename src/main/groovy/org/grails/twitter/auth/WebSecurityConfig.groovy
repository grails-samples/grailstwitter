package org.grails.twitter.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity

/**
 * This is here temporarily pending the completion of a configurable security plugin which is in the works.
 */
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/status/**", "/updateStatus/**", "/users/**", "/unfollow/**", "/follow/**").authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .permitAll()
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        def inMemory = auth.inMemoryAuthentication()
        inMemory.withUser("jeff").password("password").roles("USER")
        inMemory.withUser("graeme").password("password").roles("USER")
        inMemory.withUser("lari").password("password").roles("USER")
    }
}

package org.softuni.mbnm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/js/**", "/css/**").permitAll()
            .antMatchers("/", "/users/register", "/users/login").anonymous()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/users/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/home")
            .and()
            .logout()
            .logoutSuccessUrl("/");
    }
}

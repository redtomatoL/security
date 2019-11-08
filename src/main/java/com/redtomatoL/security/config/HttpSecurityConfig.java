package com.redtomatoL.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/5
 **/
@Component
public class HttpSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    public static class WebSercurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin()
                    .loginPage("/default-signIn.html")
                    .loginProcessingUrl("/authentication/form")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/default-signIn.html").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .csrf().disable();
        }
    }
}

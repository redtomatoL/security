package com.redtomatoL.security.config;

import com.redtomatoL.security.authentication.BrowserDefaultFailureHandler;
import com.redtomatoL.security.authentication.BrowserDefaultSuccessHandler;
import com.redtomatoL.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private BrowserDefaultSuccessHandler successHandler;

    @Autowired
    private BrowserDefaultFailureHandler failureHandler;

    @Configuration
    public  class WebSercurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin()
                    .loginPage("/authentication/require")
                    .loginProcessingUrl("/authentication/form")
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/authentication/require",securityProperties.getBrowser().getLoginPage()).permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .csrf().disable();
        }
    }
}

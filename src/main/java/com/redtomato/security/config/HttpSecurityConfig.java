package com.redtomato.security.config;

import com.redtomato.security.authentication.BrowserDefaultFailureHandler;
import com.redtomato.security.authentication.BrowserDefaultSuccessHandler;
import com.redtomato.security.properties.SecurityProperties;
import com.redtomato.security.validatecode.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

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

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("myUserDetailService")
    private UserDetailsService userDetailsService;

    /**
     * 配置记住我功能
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


    @Configuration
    public  class WebSercurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
            validateCodeFilter.setAuthenticationFailureHandler(failureHandler);
            validateCodeFilter.setSecurityProperties(securityProperties);
            validateCodeFilter.afterPropertiesSet();
            http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                    .formLogin()
                    .loginPage("/authentication/require")
                    .loginProcessingUrl("/authentication/form")
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .and()
                    .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/authentication/require",
                            "/code/*",
                            securityProperties.getBrowser().getLoginPage()).permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .csrf().disable();
        }
    }
}

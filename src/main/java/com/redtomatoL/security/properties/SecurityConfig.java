package com.redtomatoL.security.properties;

import com.redtomatoL.security.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/9
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {
}

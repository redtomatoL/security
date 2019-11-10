package com.redtomato.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/9
 **/
@ConfigurationProperties(prefix = "redtomato.security")
@Data
public class SecurityProperties {

    BrowserProperties browser = new BrowserProperties();

    ValidateCodeProperties code = new ValidateCodeProperties();

}

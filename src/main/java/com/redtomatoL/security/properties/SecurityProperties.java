package com.redtomatoL.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/9
 **/
@ConfigurationProperties(prefix = "redtomato.security")
public class SecurityProperties {

    BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}

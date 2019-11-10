package com.redtomatoL.security.properties;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/9
 **/
public class BrowserProperties {

    private String loginPage = "/default-signIn.html";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}

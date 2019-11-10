package com.redtomatoL.security.properties;

import lombok.Data;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/9
 **/
@Data
public class BrowserProperties {

    private String loginPage = "/default-signIn.html";

    private LoginType loginType = LoginType.JSON;
}

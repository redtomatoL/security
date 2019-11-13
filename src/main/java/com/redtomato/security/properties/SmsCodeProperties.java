package com.redtomato.security.properties;

import lombok.Data;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/11
 **/
@Data
public class SmsCodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;
    /**
     * 失效时间
     */
    private int expireIn = 60;

    private String url;


}

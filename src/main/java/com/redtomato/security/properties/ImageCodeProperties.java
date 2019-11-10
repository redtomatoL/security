package com.redtomato.security.properties;

import lombok.Data;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/11
 **/
@Data
public class ImageCodeProperties {

    /**
     * 图片宽
     */
    private int width = 67;
    /**
     * 图片高
     */
    private int height = 23;
    /**
     * 验证码长度
     */
    private int length = 4;
    /**
     * 失效时间
     */
    private int expireIn = 60;

    private String url;


}

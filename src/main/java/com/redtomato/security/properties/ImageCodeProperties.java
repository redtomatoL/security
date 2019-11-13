package com.redtomato.security.properties;

import lombok.Data;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/11
 **/
@Data
public class ImageCodeProperties extends SmsCodeProperties{

    public ImageCodeProperties(){
        setLength(4);
    }


    /**
     * 图片宽
     */
    private int width = 67;
    /**
     * 图片高
     */
    private int height = 23;


}

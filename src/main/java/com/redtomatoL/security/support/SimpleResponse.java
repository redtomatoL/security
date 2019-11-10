package com.redtomatoL.security.support;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/9
 **/
public class SimpleResponse {

    private Object content;


    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public SimpleResponse(Object content) {
        this.content = content;
    }
}

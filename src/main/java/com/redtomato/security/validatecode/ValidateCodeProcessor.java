package com.redtomato.security.validatecode;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/15
 **/
public interface ValidateCodeProcessor {

    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    void create(ServletWebRequest request) throws IOException, ServletRequestBindingException;
}

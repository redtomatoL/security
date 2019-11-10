package com.redtomatoL.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redtomatoL.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/9
 **/
@Component
@Slf4j
public class BrowserDefaultSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * springmvc 启动的时候自动注入的工具类
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(authentication)));

    }
}

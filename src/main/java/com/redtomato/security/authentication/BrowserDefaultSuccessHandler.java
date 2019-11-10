package com.redtomato.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redtomato.security.properties.SecurityProperties;
import com.redtomato.security.properties.LoginType;
import com.redtomato.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SavedRequestAwareAuthenticationSuccessHandler spring 默认的处理器
 * @author ljm
 * @version V1.0
 * @date 2019/11/9
 **/
@Component
@Slf4j
public class BrowserDefaultSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * springmvc 启动的时候自动注入的工具类
     */
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(authentication)));
        }else{
            super.onAuthenticationSuccess(request,response,authentication);
        }


    }
}

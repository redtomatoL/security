package com.redtomatoL.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redtomatoL.security.properties.LoginType;
import com.redtomatoL.security.properties.SecurityProperties;
import com.redtomatoL.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
public class BrowserDefaultFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    /**
     * springmvc 启动的时候自动注入的工具类
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败");

        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception)));
        }else{
            super.onAuthenticationFailure(request,response,exception);
        }

    }
}

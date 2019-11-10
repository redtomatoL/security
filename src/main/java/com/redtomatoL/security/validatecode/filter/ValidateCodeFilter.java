package com.redtomatoL.security.validatecode.filter;

import com.redtomatoL.security.validatecode.controller.ValidateController;
import com.redtomatoL.security.validatecode.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/10
 **/
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.equalsIgnoreCase("/authentication/form",request.getRequestURI())
            && StringUtils.equalsIgnoreCase(request.getMethod(),"post")){
            try {
                validate(new ServletWebRequest(request));
            }catch(ValidateCodeException validateCodeException){
                authenticationFailureHandler.onAuthenticationFailure(request,response,validateCodeException);
                return;

            }

        }else{
            filterChain.doFilter(request,response);
        }

    }

    private void validate(ServletWebRequest servletWebRequest) {
        HttpSession session = servletWebRequest.getRequest().getSession();
        String code = (String)session.getAttribute(ValidateController.SESSION_KEY);
        if(StringUtils.isBlank(code)){
            throw new ValidateCodeException("验证码不能为空");
        }

    }
}

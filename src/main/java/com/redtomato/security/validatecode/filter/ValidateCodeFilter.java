package com.redtomato.security.validatecode.filter;

import com.redtomato.security.validatecode.exception.ValidateCodeException;
import com.redtomato.security.validatecode.controller.ValidateController;
import com.redtomato.security.validatecode.image.ImageCode;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
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
@Data
public class ValidateCodeFilter extends OncePerRequestFilter {

//    @Autowired
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

        }
        filterChain.doFilter(request,response);

    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        HttpSession session = servletWebRequest.getRequest().getSession();
        ImageCode codeInSession = (ImageCode)session.getAttribute(ValidateController.SESSION_KEY);

        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(),"imageCode");
        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空");
        }

        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在");
        }

        if(codeInSession.isExpired()){
            throw new ValidateCodeException("验证码过期");
        }

        if(!StringUtils.equalsIgnoreCase(codeInRequest,codeInSession.getCode())){
            throw new ValidateCodeException("验证码不匹配");
        }

        session.removeAttribute(ValidateController.SESSION_KEY);
    }


}

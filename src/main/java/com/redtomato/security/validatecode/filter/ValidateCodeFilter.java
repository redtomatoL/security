package com.redtomato.security.validatecode.filter;

import com.redtomato.security.properties.SecurityProperties;
import com.redtomato.security.validatecode.ValidateCodeProcessor;
import com.redtomato.security.validatecode.exception.ValidateCodeException;
import com.redtomato.security.validatecode.controller.ValidateController;
import com.redtomato.security.validatecode.image.ImageCode;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
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
import java.util.HashSet;
import java.util.Set;

/**
 * InitializingBean spring 初始化bean 完成后
 * @author ljm
 * @version V1.0
 * @date 2019/11/10
 **/
@Data
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

//    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private Set<String> urlSet = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String urlString = securityProperties.getCode().getImage().getUrl();
        if(StringUtils.isNotBlank(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlSet.add(url);
            }
        }

        urlSet.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        for(String url:urlSet){
            if(pathMatcher.match(url,request.getRequestURI())){
                action = true;
            }
        }
        if(action){
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
        ImageCode codeInSession = (ImageCode)session.getAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX);

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

        session.removeAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX);
    }


}

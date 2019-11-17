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
import org.springframework.stereotype.Component;
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
import java.util.Map;
import java.util.Set;

/**
 * InitializingBean spring 初始化bean 完成后
 * @author ljm
 * @version V1.0
 * @date 2019/11/10
 **/
@Data
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private Set<String> urlSet = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

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
        urlSet.add("/authentication/mobile");
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
                validateCodeProcessors.get("imageValidateCodeProcessor").validate(new ServletWebRequest(request));
            }catch(ValidateCodeException validateCodeException){
                authenticationFailureHandler.onAuthenticationFailure(request,response,validateCodeException);
                return;

            }

        }
        filterChain.doFilter(request,response);

    }



    private String getValidateCodeType(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String type = StringUtils.substringAfter(uri,"/code/");
        return type;
    }



}

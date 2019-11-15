package com.redtomato.security.validatecode;

import com.redtomato.security.validatecode.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/15
 **/
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor{

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;


    @Override
    public final void create(ServletWebRequest request) throws IOException, ServletRequestBindingException {
        // 1.生成校验码
        C validateCode = generate(request);
        // 2.保存校验码
        save(request, validateCode);
        // 3.发送校验码
        send(request, validateCode);
    }

    private void save(ServletWebRequest request,ValidateCode validateCode){
        HttpSession session = request.getRequest().getSession(true);
        session.setAttribute(SESSION_KEY_PREFIX+getValidateCodeType(request).toUpperCase(),validateCode);
    }

    protected abstract void send(ServletWebRequest request,C validateCode) throws IOException, ServletRequestBindingException;


    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request.getRequest());
    }

    private String getValidateCodeType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }

    /**
     * 定义钩子方法
     */
    void hook(){}


}

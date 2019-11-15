package com.redtomato.security.validatecode.sms;

import com.redtomato.security.properties.SecurityProperties;
import com.redtomato.security.validatecode.ValidateCode;
import com.redtomato.security.validatecode.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/14
 **/
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public ValidateCode generate(HttpServletRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());

        return new ValidateCode(code,securityProperties.getCode().getSms().getExpireIn());
    }
}

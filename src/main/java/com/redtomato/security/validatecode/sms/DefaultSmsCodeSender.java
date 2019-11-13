package com.redtomato.security.validatecode.sms;


/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/14
 **/
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println(String.format("向%s发送验证码%s",mobile,code));
    }
}

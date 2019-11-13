package com.redtomato.security.validatecode.sms;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/14
 **/
public interface SmsCodeSender {

    void send(String mobile,String code);
}

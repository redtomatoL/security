package com.redtomato.security.social.qq.api;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/18
 **/
public class QQimpl extends AbstractOAuth2ApiBinding implements QQ {


    private static final String URL_GET_OPENID = "";
    private static final String URL_GET_USERINFO = "";

    @Override
    public QQUserInfo getUserInfo() {
        return null;
    }
}

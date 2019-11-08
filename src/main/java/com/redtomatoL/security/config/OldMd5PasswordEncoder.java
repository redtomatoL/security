package com.redtomatoL.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * 一版本的MD5加密器
 * @author andre1989@sina.com
 * @version 2019-09-18
 */
@Component
public class OldMd5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        String salt= DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
        return DigestUtils.md5DigestAsHex((charSequence + salt).getBytes());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String encoded = encode(charSequence);
        return encoded.equals(s);
    }
}

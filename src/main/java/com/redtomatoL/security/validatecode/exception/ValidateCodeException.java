/**
 * 
 */
package com.redtomatoL.security.validatecode.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author ljm
 *
 */

/**
 * AuthenticationException 是个抽象异常，所有的身份认证过程中抛出异常的基类
 */
public class ValidateCodeException extends AuthenticationException {


	public ValidateCodeException(String msg) {
		super(msg);
	}

}

/**
 * 
 */
package com.redtomato.security.validatecode;

import com.redtomato.security.validatecode.image.ImageCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验码生成器
 * @author ljm
 *
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成校验码
	 * @param request
	 * @return
	 */
	ImageCode createImageCode(HttpServletRequest request);
	
}

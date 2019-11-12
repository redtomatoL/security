package com.redtomato.security.validatecode.controller;

import com.redtomato.security.properties.SecurityProperties;
import com.redtomato.security.validatecode.ValidateCodeGenerator;
import com.redtomato.security.validatecode.image.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/10
 **/
@RestController
public class ValidateController {



    public static final String SESSION_KEY="SESSION_KEY_IMAGE_CODE";

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;


    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);

        // 生产验证码
        ImageCode imageCode = imageCodeGenerator.createImageCode(request);
        session.setAttribute(SESSION_KEY,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);

        // 生产验证码
        ImageCode imageCode = smsCodeGenerator.createImageCode(request);
        session.setAttribute(SESSION_KEY,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }



}

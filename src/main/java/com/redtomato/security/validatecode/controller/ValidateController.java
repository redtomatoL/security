package com.redtomato.security.validatecode.controller;

import com.redtomato.security.validatecode.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/10
 **/
@RestController
public class ValidateController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;


    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws IOException, ServletRequestBindingException {
        validateCodeProcessors.get(type+"CodeProcessor").create(new ServletWebRequest(request,response));
    }




}

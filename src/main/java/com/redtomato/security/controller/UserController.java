package com.redtomato.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljm
 * @version V1.0
 * @date 2019/11/5
 **/
@RestController
public class UserController {
    @RequestMapping("/users")
    public String getUser(){
        return "lili";
    }
}

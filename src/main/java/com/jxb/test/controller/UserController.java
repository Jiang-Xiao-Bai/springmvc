package com.jxb.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaohf
 * @date 2020/6/24 17:37
 */
@Controller
@RequestMapping("/springmvc")
public class UserController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request){
        String contextPath = request.getContextPath();
        System.out.println(contextPath);
        return contextPath+"========请求成功！";
    }
}

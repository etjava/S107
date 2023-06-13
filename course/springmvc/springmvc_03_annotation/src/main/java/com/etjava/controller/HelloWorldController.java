package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    @RequestMapping("/say")
    public String hello(Model model){
        model.addAttribute("msg","hello springmvc");
        return "hello";// 这个字符串会被视图解析器处理
    }
}

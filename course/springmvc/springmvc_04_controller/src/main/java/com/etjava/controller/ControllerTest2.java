package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
@Controller 表示被Spring托管
@RequestMapping 定义请求映射器
该类中是任意方法 如果返回值是String类型 并且有对应的页面 那么就会被视图解析器解析
 */
@Controller
@RequestMapping("/hello2")
public class ControllerTest2 {

    @RequestMapping("/say")
    public String say(Model model){
        model.addAttribute("msg","2nd. 注解方式实现controller");
        return "hello";
    }

    @RequestMapping("/t1")
    public String test1(Model model){
        model.addAttribute("msg","测试RequestMapping注解使用");
        return "hello";
    }
}

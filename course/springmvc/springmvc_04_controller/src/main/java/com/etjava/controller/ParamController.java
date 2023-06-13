package com.etjava.controller;

import com.etjava.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
    请求参数和回显
 */
@Controller
@RequestMapping("/param")
public class ParamController {


    // url中参数与controller中参数名相同
    // http://localhost:8080/springmvc_04_controller_war_exploded/param/t1?name=Tom
    @RequestMapping("/t1")
    public String t1(String name, Model model){

        model.addAttribute("msg",name);
        return "hello";
    }

    // url中参数名与controller中参数名不同
    //@RequestParam("username") 通过该注解指定具体的参数名
    // 注意 指定后你的url参数要与其保持一致 否则接收不到
    // 即 http://localhost:8080/springmvc_04_controller_war_exploded/param/t2?username=Tom
    @RequestMapping("/t2")
    public String t2(@RequestParam("username") String name, Model model){
        model.addAttribute("msg",name);
        return "hello";
    }

    // 接收对象作为参数
    // 前端传递的数据会自动封装到对象中对应的字段里面
    @RequestMapping("/t3")
    public String t3(User user, Model model){
        model.addAttribute("msg",user.getName());
        return "hello";
    }
}

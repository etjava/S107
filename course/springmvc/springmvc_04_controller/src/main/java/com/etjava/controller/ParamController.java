package com.etjava.controller;

import com.etjava.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
    ��������ͻ���
 */
@Controller
@RequestMapping("/param")
public class ParamController {


    // url�в�����controller�в�������ͬ
    // http://localhost:8080/springmvc_04_controller_war_exploded/param/t1?name=Tom
    @RequestMapping("/t1")
    public String t1(String name, Model model){

        model.addAttribute("msg",name);
        return "hello";
    }

    // url�в�������controller�в�������ͬ
    //@RequestParam("username") ͨ����ע��ָ������Ĳ�����
    // ע�� ָ�������url����Ҫ���䱣��һ�� ������ղ���
    // �� http://localhost:8080/springmvc_04_controller_war_exploded/param/t2?username=Tom
    @RequestMapping("/t2")
    public String t2(@RequestParam("username") String name, Model model){
        model.addAttribute("msg",name);
        return "hello";
    }

    // ���ն�����Ϊ����
    // ǰ�˴��ݵ����ݻ��Զ���װ�������ж�Ӧ���ֶ�����
    @RequestMapping("/t3")
    public String t3(User user, Model model){
        model.addAttribute("msg",user.getName());
        return "hello";
    }
}

package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
@Controller ��ʾ��Spring�й�
@RequestMapping ��������ӳ����
�����������ⷽ�� �������ֵ��String���� �����ж�Ӧ��ҳ�� ��ô�ͻᱻ��ͼ����������
 */
@Controller
@RequestMapping("/hello2")
public class ControllerTest2 {

    @RequestMapping("/say")
    public String say(Model model){
        model.addAttribute("msg","2nd. ע�ⷽʽʵ��controller");
        return "hello";
    }

    @RequestMapping("/t1")
    public String test1(Model model){
        model.addAttribute("msg","����RequestMappingע��ʹ��");
        return "hello";
    }
}

package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//�����������Ŀ�����
@Controller
public class TestInterceptorController {
    @RequestMapping("/interceptor")
    @ResponseBody
    public String testFunction() {
        System.out.println("�������еķ���ִ����");
        return "hello";
    }
}

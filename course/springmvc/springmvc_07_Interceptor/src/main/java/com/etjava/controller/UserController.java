package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    //��ת����½ҳ��
    @RequestMapping("/jumplogin")
    public String jumpLogin() throws Exception {
        return "login";
    }
    //��ת���ɹ�ҳ��
    @RequestMapping("/jumpSuccess")
    public String jumpSuccess() throws Exception {
        return "success";
    }
    //��½�ύ
    @RequestMapping("/login")
    public String login(HttpSession session, String username, String pwd) throws Exception {
        // ��session��¼�û������Ϣ
        System.out.println("����ǰ��==="+username);
        session.setAttribute("user", username);
        return "success";
    }
    //�˳���½
    @RequestMapping("logout")
    public String logout(HttpSession session) throws Exception {
        // session ����
        session.invalidate();
        return "login";
    }
}

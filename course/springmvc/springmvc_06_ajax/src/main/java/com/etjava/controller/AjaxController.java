package com.etjava.controller;

import com.etjava.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {
    @RequestMapping("/a1")
    public void ajax1(String name , HttpServletResponse response) throws IOException {
        if ("admin".equals(name)){
            response.getWriter().print("true");
        }else{
            response.getWriter().print("false");
        }
    }

    @RequestMapping("/a2")
    public List<User> ajax2(){
        List<User> list = new ArrayList<User>();
        list.add(new User("Tom",3,"��"));
        list.add(new User("Jerry",5,"��"));
        list.add(new User("Andy",7,"��"));
        return list; //����@RestControllerע�⣬��listת��json��ʽ����
    }

    @RequestMapping("/a3")
    public String ajax3(String name,String pwd){
        String msg = "";
        //ģ�����ݿ��д�������
        if (name!=null){
            if ("admin".equals(name)){
                msg = "OK";
            }else {
                msg = "�û����������";
            }
        }
        if (pwd!=null){
            if ("123456".equals(pwd)){
                msg = "OK";
            }else {
                msg = "������������";
            }
        }
        return msg; //����@RestControllerע�⣬��msgת��json��ʽ����
    }
}
package com.etjava.controller;

import com.etjava.model.User;
import com.etjava.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Controller
@RestController
@RequestMapping("/json")
public class JSONController {

    @RequestMapping("/t1")
    public String t1(Model model){
        model.addAttribute("msg",121212);
        return "json";
    }

    //@ResponseBody
    //produces:ָ����Ӧ�巵�����ͺͱ���
    //@RequestMapping(value = "/t2",produces = "application/json;charset=utf-8")
    @RequestMapping("/t2")
    public String json1() throws JsonProcessingException {
        //����һ��jackson�Ķ���ӳ������������������
        ObjectMapper mapper = new ObjectMapper();
        //����һ������
        User user = new User(1, "����", 21);
        //�����ǵĶ��������Ϊjson��ʽ
        String str = mapper.writeValueAsString(user);
        //����@ResponseBodyע�⣬����Ὣstrת��json��ʽ���أ�ʮ�ַ���
        return str;
    }

    @RequestMapping("/t3")
    public String json2() throws JsonProcessingException {
        //����һ��jackson�Ķ���ӳ������������������
        ObjectMapper mapper = new ObjectMapper();
        //����һ������
        User user1 = new User(1, "Tom", 12);
        User user2 = new User(2, "Jerry", 13);
        User user3 = new User(3, "Andy", 14);
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        //�����ǵĶ��������Ϊjson��ʽ
        String str = mapper.writeValueAsString(list);
        return str;
    }

    @RequestMapping("/t4")
    public String json3() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //����ʱ��һ������java.util.Date
        Date date = new Date();
        //�����ǵĶ��������Ϊjson��ʽ
        String str = mapper.writeValueAsString(date);
        return str;
    }

    @RequestMapping("/t5")
    public String json4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //��ʹ��ʱ����ķ�ʽ
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //�Զ������ڸ�ʽ����
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //ָ�����ڸ�ʽ
        mapper.setDateFormat(sdf);
        Date date = new Date();
        String str = mapper.writeValueAsString(date);
        return str;
    }

    @RequestMapping("/t6")
    public String json5() throws JsonProcessingException {
        Date date = new Date();
        String json = JsonUtils.getJson(date);
        return json;
    }
}

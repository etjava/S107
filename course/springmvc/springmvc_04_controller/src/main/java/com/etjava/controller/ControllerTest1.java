package com.etjava.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 ͨ��ʵ��Controller�ӿڷ�ʽ ʵ��Controller������
 */
public class ControllerTest1 implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
       ModelAndView mav = new ModelAndView();
       mav.addObject("msg","1st. ͨ��ʵ��controller�ӿڵķ�ʽʵ��Controller");
        return mav;
    }
}

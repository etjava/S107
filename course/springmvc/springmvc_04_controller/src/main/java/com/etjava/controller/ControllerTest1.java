package com.etjava.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 通过实现Controller接口方式 实现Controller控制器
 */
public class ControllerTest1 implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
       ModelAndView mav = new ModelAndView();
       mav.addObject("msg","1st. 通过实现controller接口的方式实现Controller");
        return mav;
    }
}

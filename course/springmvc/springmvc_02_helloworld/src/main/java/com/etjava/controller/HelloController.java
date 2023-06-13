package com.etjava.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        // ��װ����
        mav.addObject("msg","hello SpringMVC");
        // ��װҪ��ת����ͼ �����Ӧ����/WEB-INF/jsp/hello.jsp
        mav.setViewName("hello");
        return mav;
    }
}

package com.etjava.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        // 封装数据
        mav.addObject("msg","hello SpringMVC");
        // 封装要跳转的视图 这里对应的是/WEB-INF/jsp/hello.jsp
        mav.setViewName("hello");
        return mav;
    }
}

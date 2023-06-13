package com.etjava.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloWorld extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置字符集
        req.setCharacterEncoding("UTF-8");
        // 获取前端参数
        String ac = req.getParameter("action");
        if(ac.equals("add")){
            req.getSession().setAttribute("msg","调用了add方法");
        }else if(ac.equals("delete")){
            req.getSession().setAttribute("msg","调用了delete方法");
        }
        // 调用业务层
        // 转发到页面
        // 请求转发  可以携带参数
        req.getRequestDispatcher("/WEB-INF/pages/hello.jsp").forward(req,resp);
        // 重定向到页面 URL路径会发生改变
        //resp.sendRedirect("/WEB-INF/pages/hello.jsp");
    }
}

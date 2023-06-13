package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/item")
public class RestfulController {

    // 传统方式
    // http://localhost:8080/springmvc_04_controller_war_exploded/item/add?a=1&b=2
    @RequestMapping("/add")
    public String test1(int a, int b, Model model){
        model.addAttribute("msg","result = "+(a+b));
        return "hello";
    }

    /*
    restful 风格方式
    参数列表中给参数添加PathVariable注解 然后就可以使用/{a}/{b} 方式传参
    请求路径中{a}指的参数a
    http://localhost:8080/springmvc_04_controller_war_exploded/item/add2/1/2
     */
    @RequestMapping("/add2/{a}/{b}")
    public String test2(@PathVariable int a, @PathVariable int b, Model model){
        model.addAttribute("msg","result = "+(a+b));
        return "hello";
    }


    /*
        可以通过method指定请求是get还是post等
        也可以简化写法 直接使用GetMappint注解
     */
    //@RequestMapping(value="/add3/{a}/{b}",method = RequestMethod.GET)
    @GetMapping("/add3/{a}/{b}")
    //@RequestMapping(value="/add3/{a}/{b}",method = RequestMethod.POST)
    //@PostMapping("/add3/{a}/{b}")
    //@RequestMapping(value="/add3/{a}/{b}",method = RequestMethod.DELETE)
    //@DeleteMapping("/add3/{a}/{b}")
    //@RequestMapping(value="/add3/{a}/{b}",method = RequestMethod.PUT)
    //@PutMapping("/add3/{a}/{b}")
    public String test3(@PathVariable int a, @PathVariable int b, Model model){
        model.addAttribute("msg","result = "+(a+b));
        return "hello";
    }


    @GetMapping("/test/{a}/{b}")
    public String test4(@PathVariable int a, @PathVariable int b, Model model){
        model.addAttribute("msg","result(get) = "+(a+b));
        return "hello";
    }

    @PostMapping("/test/{a}/{b}")
    public String test5(@PathVariable int a, @PathVariable int b, Model model){
        model.addAttribute("msg","result(post) = "+(a+b));
        return "hello";
    }


    @RequestMapping("/test6")
    public String test(HttpServletRequest request, HttpServletResponse response
            ,Model model){

        String id = request.getSession().getId();

        model.addAttribute("msg",id);

        return "hello";
    }

    @RequestMapping("/t1")
    public String test7(Model model){
        model.addAttribute("msg","请求转发方式1 return \"/index.jsp\"");
        // 转发
        return "/index.jsp";
    }

    @RequestMapping("/t2")
    public String test8(Model model){
        model.addAttribute("msg","请求转发方式2 return \"forward:/index.jsp\"");
        // 转发方式2
        return "forward:/index.jsp";
    }

    @RequestMapping("/t3")
    public String test9(Model model){
        model.addAttribute("msg","return \"redirect:/index.jsp\"");
        // 重定向
        return "redirect:/index.jsp";
    }

}

package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/item")
public class RestfulController {

    // ��ͳ��ʽ
    // http://localhost:8080/springmvc_04_controller_war_exploded/item/add?a=1&b=2
    @RequestMapping("/add")
    public String test1(int a, int b, Model model){
        model.addAttribute("msg","result = "+(a+b));
        return "hello";
    }

    /*
    restful ���ʽ
    �����б��и��������PathVariableע�� Ȼ��Ϳ���ʹ��/{a}/{b} ��ʽ����
    ����·����{a}ָ�Ĳ���a
    http://localhost:8080/springmvc_04_controller_war_exploded/item/add2/1/2
     */
    @RequestMapping("/add2/{a}/{b}")
    public String test2(@PathVariable int a, @PathVariable int b, Model model){
        model.addAttribute("msg","result = "+(a+b));
        return "hello";
    }


    /*
        ����ͨ��methodָ��������get����post��
        Ҳ���Լ�д�� ֱ��ʹ��GetMappintע��
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
        model.addAttribute("msg","����ת����ʽ1 return \"/index.jsp\"");
        // ת��
        return "/index.jsp";
    }

    @RequestMapping("/t2")
    public String test8(Model model){
        model.addAttribute("msg","����ת����ʽ2 return \"forward:/index.jsp\"");
        // ת����ʽ2
        return "forward:/index.jsp";
    }

    @RequestMapping("/t3")
    public String test9(Model model){
        model.addAttribute("msg","return \"redirect:/index.jsp\"");
        // �ض���
        return "redirect:/index.jsp";
    }

}

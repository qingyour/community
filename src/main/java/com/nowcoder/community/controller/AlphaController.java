package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    //@ResponseBody想得到 html类型的就加


    @Autowired
    AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String find() {
        return alphaService.find();
    }

    //普通请求获取

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //获取请求数据
        //请求路径
        System.out.println(request.getRequestURL());
        //请求方法
        System.out.println(request.getMethod());
        //请求元素
        Enumeration<String> enumeration = request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name+";"+value);
        }
        System.out.println("获取收到的属性数据："+request.getParameter("code"));

        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();
                ){
            writer.write("<h3>牛客网</h3>");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    // GET 请求
    //两种传参方式

    //1. /students?current=1&limit=20

    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current",required = false,defaultValue = "1") int current,
            @RequestParam(name = "limit",required = false,defaultValue = "10") int limit){

        //获取当前页和限制页 当碰到不传参数的时候给予默认值
        System.out.println(current);
        System.out.println(limit);
        return "some Students";
    }


    //2. /students/123

    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "one Student";
    }


    //Post请求

    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String geiStudent(String name, int age){
        System.out.println("获取的姓名:"+name);
        System.out.println("获取的年龄:"+age);
        return "success";
    }

    //响应http数据 MVC的内容

    //1.

    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age","18");
        //需要返回的动态网页
        mav.setViewName("/demo/view");
        return  mav;
    }

    //2.常用

    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","民师院");
        model.addAttribute("age","83");

        return  "/demo/view";
    }

    //JSON字符串

    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",23);
        emp.put("salary",8000.0);

        //得到 {"name":"张三","salary":8000.0,"age":23}

        return emp;
    }

    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",23);
        emp.put("salary",8000.0);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","李四");
        emp.put("age",24);
        emp.put("salary",9000.0);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","王五");
        emp.put("age",25);
        emp.put("salary",08000.0);
        list.add(emp);


        /*
        得到
        [
             {"name":"张三","salary":8000.0,"age":23},
             {"name":"李四","salary":9000.0,"age":24},
             {"name":"王五","salary":8000.0,"age":25}
         ]

         */

        return list;
    }





}

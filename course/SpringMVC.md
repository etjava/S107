# SpringMVC 5.x

## 概述

```
什么是MVC
	MVC是 Model(模型)、View（视图）、Controller(控制器)的简写，是一种设计规范
	是将业务逻辑，数据和展示分离的方式来组织我们的代码
	MVC主要作用是降低了视图和业务层逻辑间的双向耦合
	MVC不是一种设计模式 而是一种架构模式，当然不同的MVC存在差异
	
	Model 模型：
		数据模型，提供要展示的数据 因此包含数据和行为，可以认为是领域模型或JavaBean组件 
		不过现在一般都分离开来 数据（dao） 和 业务 （Service） 
		也就是说模型层提供了数据查询和状态更新等功能 包括数据和业务
		
		
	View 视图：
		负责进行模型是展示，一般就是我们见到的用户界面 也就是客户想看到的东西
		
	Controller 控制层：
		接收用户请求，委托给模型进行处理(状态更新) 
		处理完成后把返回的模型数据 返回给驶入，然后由视图进行展示 控制层就是做了调度员的工作
	
Spring MVC属于SpringFrameWork的后续产品,Spring 框架提供了构建 Web 应用程序的全功能 MVC 模块。使用 Spring 可插入的 MVC 架构，从而在使用Spring进行WEB开发时，可以选择使用Spring的Spring MVC框架或集成其他MVC开发框架
官方文档
https://docs.spring.io/spring-framework/docs/current/reference/html/web.html

JavaSE：	Java基础阶段
JavaWeb: Java基础阶段
框架：	偏实战

面试常问：
	Spring的IOC和AOP
	SpringMVC的执行流程
```

![image-20230424211217889](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210744984.png)

![image-20230424212127411](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210745797.png)

## 回顾Servlet

采用模块化 循序渐进的方式学习

### 新建父项目

创建普通maven项目 然后删掉src目录 在导入下面依赖

```xml
<dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.23</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp.jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        
    </dependencies>
```

### Servlet基本使用

#### 新建模块项目

选中项目右键 new -> Module

![image-20230424213832900](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210746425.png)

![image-20230424213915893](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210746329.png)

#### 添加web框架支持

新建的模块是一个普通Java项目 我们回顾Servlet需要添加web支持才能将其转成web项目

选中模块 右键 -> Add Framework Support...

![image-20230424214111741](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210746186.png)

勾选WebApplication

![image-20230424214159238](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210746323.png)

#### 项目结构

![image-20230424214337040](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210746017.png)

#### 导入servlet相关依赖

其实可以继承自父项目中的依赖 如果是分开建立的项目(非模块)就需要自己导入依赖了

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
</dependency>
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.2</version>
</dependency>
```

#### 编写Servlet处理用户请求

```java
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

```

#### 配置web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <servlet>
        <servlet-name>hello</servlet-name>
        <servlet-class>com.etjava.servlet.HelloWorld</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>hello</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>

    <!--配置session超时时间 单位分钟-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>
    
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
</web-app>
```

#### 编写hello.jsp

```jsp
<%--
  Created by IntelliJ IDEA.
  User: etjav
  Date: 2023-04-24
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>${msg}</h1>
</body>
</html>

```

#### 编写index.jsp

index页面用来提交请求

```jsp
<%--
  Created by IntelliJ IDEA.
  User: etjav
  Date: 2023-04-24
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form method="post" action="/hello">
    <input name="action" type="text" />
    <input type="submit" value="提交">
  </form>
  </body>
</html>

```

#### 配置tomcat

![image-20230424220051494](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210746161.png)

#### 启动测试

![image-20230424220938123](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210746990.png)

#### 小结

```
MVC框架做了哪些事情？
    1. 将url映射到Java类或方法
    2. 封装用户提交的数据
    3. 处理请求 调用相关业务逻辑 封装响应的数据
    4. 将响应的数据进行渲染 jsp/html等表示层数据
```

## SpringMVC

```
什么是SpringMVC?
SpringMVC是SpringFramework的一部分，是基于Java实现MVC的轻量级web框架
帮助文档：
https://docs.spring.io/spring-framework/docs/5.2.0.RELEASE/spring-framework-reference/web.html#spring-web
老版本文档：
https://docs.spring.io/spring-framework/docs/4.3.24.RELEASE/spring-framework-reference/html   [/mvc.html]
为什么需要SpringMVC?
	1. 轻量级  简单易学
	2. 高效  请求基于响应的MVC框架
			接收请求并返回视图
	3. 与Spring可以无缝结合
	4. 约定优于配置
	5. 功能强大 RestFul、数据验证、格式化、本地化、主题等
	6. 简洁灵活
	Spring的web框架围绕DispatcherServlet[调度Servlet]设计
	DispatcherServlet的作用是将请求分发到不同的处理器，从Spring2.5开始 使用Java5.0或更高版本的用户可以
	采用基于注解形式进行开发
	正因为SpringMVC的简洁灵活 天生与Spring无缝集成 所以使用SpringMVC可以提高我们的开发效率
	
SpringMVC工作流程
	
	
```

![image-20230521080846924](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210828105.png)

### SpringMVC的执行原理

```
Incoming request 发起请求 通过前端控制器（Delegate request） 调用请求处理器(Handle request)
Handle request调用业务层进程处理并返回模型数据(Delegate rendering of response) 
最后前端控制器对页面(View Template)进行渲染 返回响应给到用户
```

![image-20230521080151526](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210801479.png)

![image-20230521103158256](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211032948.png)

```
1. DispatcherServlet 表示前置控制器(请求分发器) 是整个SpringMVC的控制中心，
	用户发出请求 DispatcherServlet接收并拦截请求
2. HandlerMapping 请求映射处理器，相当于Servlet-mapping  
	DisPatcherServlet调用HandlerMapping,然后HandlerMapping根据请求的URL找对应的Handler
3. HandlerExecution 表示具体的Handler 例如 hello
4. HandlerExecution将解析后的信息传递给DispatcherServlet 例如解析映射器等
5. HandlerAdapter 表示请求适配器 会按照指定的规则去执行Handler
6. Handler适配器会去找到对应的Controller
7. Controller将具体的执行信息返回给HandlerAdapter适配器 例如 ModelAndView
8. HandlerAdapter 将视图逻辑名称或模型传递给DispatcherServlet
9. DispatcherServlet调用视图解析器(ViewResolver)来解析HandlerAdapter传递的逻辑实体名称
10.视图解析器将解析到的视图传递给DispatcherServlet
11.DispatcherServlet根据视图解析器解析视图并进行视图渲染
12. 返回最终效果给用户
```

### HelloWord

#### 1、新建项目 添加web支持

![image-20230521084128808](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210841545.png)

pom

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.etjava</groupId>
    <artifactId>springmvc5</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.23</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp.jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
    </dependencies>

</project>
```

添加web支持

项目右键AddFramework Support...

![image-20230521084530735](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210845340.png)

![image-20230521084624894](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210846371.png)



#### 2、配置web.xml 注册DispatcherServlet

```xml
 <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--
        配置需要拦截的请求
        / 匹配所有请求 但不包括.jsp
        /* 匹配所有请求 包括.jsp
    -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

#### 3、 添加SpringMVC配置

resouces目录下

springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--添加处理映射器-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping" />
    <!--添加请求处理（适配）器-->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter" />
    <!--添加视图解析器-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <!--配置前缀-->
            <property name="prefix" value="/WEB-INF/jsp/"/>
            <!--后缀-->
            <property name="suffix" value=".jsp"/>
    </bean>
    <!--注册处理请求的bean-->
    <bean id="/hello" class="com.etjava.controller.HelloController"/>

</beans>
```

#### 4、创建请求处理Controller

```java
package com.etjava.controller;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

// 通过实现Controller接口方式测试SpringMVC
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

```

#### 5、新建jsp

WEB-INF/jsp/hello.jsp

```jsp
<%--
  Created by IntelliJ IDEA.
  User: etjav
  Date: 2023-05-21
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${msg}
</body>
</html>

```

![image-20230521100755801](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211008895.png)

如果出现404先看下是否缺少jar包 如果不缺少 就在idea项目发布中添加lib依赖

ctrl+shift+alt+S

如果添加后还是404或报DispatcherServlet 类加载异常错误 将tomcat删除 然后重新引入即可

![image-20230521092222911](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210922215.png)

![image-20230521092710859](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305210927296.png)

### 注解方式开发SpringMVC

```
实现步骤
1. 新建web项目或模块
2. 导入相关依赖
3. 编写web.xml 注册DispatcherServlet
4. 编写SpringMvc配置文件
5. 创建Controller
6. 创建页面
7. 测试
```



#### 1、新建模块

![image-20230521142638897](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211426262.png)

#### 2、完善maven资源过滤问题 添加如下依赖

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

#### 3、pom中添加相关依赖

由于我们使用的是模块化项目 而我们已经在父项目中引入了相关依赖 因此这里不需要再次引入了

#### 4、配置web.xml

```
注意 
web.xml必须使用4.0或最新的版本
```

![image-20230521143821246](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211438517.png)

#### 5、添加Spring-mvc配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--自动扫描包 让指定包下的注解生效-->
    <context:component-scan base-package="com.etjava.controller" />

    <!--不处理静态资源-->
    <mvc:default-servlet-handler />
    <!--
        支持MVC注解驱动
        在Spring中一般采用@RequestMapping注解来完成映射关系
        要想使@RequestMapping生效 必须在Spring的上下文中进行注册DefaultAnnotationHandlerMapping
        和AnnotationMethodHandlerAdapter实例
        这两个实例分别在类级别和方法级别处理
        而 annotation-driver可以帮助我们自动完成上述两个实例的注入
    -->
    <mvc:annotation-driven />

    <!--添加视图解析器-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--配置前缀-->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!--后缀-->
        <property name="suffix" value=".jsp" />
    </bean>

</beans>
```

#### 6、创建Controller

```java
package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    @RequestMapping("/say")
    public String hello(Model model){
        model.addAttribute("msg","hello springmvc");
        return "hello";// 这个字符串会被视图解析器处理
    }
}
```

#### 7、测试

![image-20230521151641331](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211516292.png)

### Controller配置总结

```
Controller 控制器
负责提供访问应用程序的行为，通常通过接口定义或注解定义两种方式实现
负责解析用户的请求并将其转换为一个模型
在SpringMVC中一个控制器类可以包含多个方法 对于Controller的配置方式有很多种

常见的配置有
1、 实现Controller接口 在org.springframework.web.servlet.mvc包下 该接口只有一个方法
	不推荐使用 原因是一个Controller中只能写一个处理方法
2、 使用注解方式实现
	
```

#### 1、新建模块项目

#### 2、添加web支持并注册DispatcherServlet

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--
        配置需要拦截的请求
        / 匹配所有请求 但不包括.jsp
        /* 匹配所有请求 包括.jsp
    -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

#### 3、添加springmvc配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--自动扫描包 让指定包下的注解生效-->
    <context:component-scan base-package="com.etjava.controller" />

    <!--不处理静态资源-->
    <mvc:default-servlet-handler />
    <!--
        支持MVC注解驱动
        在Spring中一般采用@RequestMapping注解来完成映射关系
        要想使@RequestMapping生效 必须在Spring的上下文中进行注册DefaultAnnotationHandlerMapping
        和AnnotationMethodHandlerAdapter实例
        这两个实例分别在类级别和方法级别处理
        而 annotation-driver可以帮助我们自动完成上述两个实例的注入
    -->
    <mvc:annotation-driven />

    <!--添加视图解析器-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--配置前缀-->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!--后缀-->
        <property name="suffix" value=".jsp" />
    </bean>

</beans>
```

#### 4、创建jsp页面

WEB-INF/jsp下创建hello.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${msg}
</body>
</html>

```

#### 方式1 接口方式

##### 编写Controller

```java
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
```

##### 修改springmvc配置文件

```xml
使用接口的方式需要注册bean
<bean class="com.etjava.controller.ControllerTest1" id="/hello"/>
```

##### 测试

![image-20230521155359442](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211554549.png)

#### 方式2 使用注解

##### 编写Controller

```java
package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
@Controller 表示被Spring托管
@RequestMapping 定义请求映射器
该类中是任意方法 如果返回值是String类型 并且有对应的页面 那么就会被视图解析器解析
 */
@Controller
@RequestMapping("/hello2")
public class ControllerTest2 {

    @RequestMapping("/say")
    public String say(Model model){
        model.addAttribute("msg","2nd. 注解方式实现controller");
        return "hello";
    }
}
```

##### 测试

![image-20230521160134420](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211601886.png)

### RequestMapping

沿用Controller模块

```
RequestMapping注解是用来映射请求url到Controller的，可以用在类或方法上
用于类上 表示作为该类中所有请求的url父路径 即 localhost:8080/hello/xxx
用于方法上则会具体请求到的方法
如果类和方法上同时存在 则访问的url中需要先拼接类上的请求映射在拼接方法上的请求映射
例如 类上是hello 方法上是t1 则 http://localhost:8080/hello/t1
```

#### 编写Controller

```java
package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
@Controller 表示被Spring托管
@RequestMapping 定义请求映射器
该类中是任意方法 如果返回值是String类型 并且有对应的页面 那么就会被视图解析器解析
 */
@Controller
@RequestMapping("/hello2")
public class ControllerTest2 {

    @RequestMapping("/say")
    public String say(Model model){
        model.addAttribute("msg","2nd. 注解方式实现controller");
        return "hello";
    }

    @RequestMapping("/t1")
    public String test1(Model model){
        model.addAttribute("msg","测试RequestMapping注解使用");
        return "hello";
    }
}

```

![image-20230521164050448](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211643327.png)

### RESTFull风格

#### 概念

```
Restful就是一个资源定位及资源操作的风格，不是标准也不是协议，只是一种风格，基于restful风格设计的软件可以使url更加简洁 更有层次 易于实现缓存等机制

例如
	不使用Restful风格 我们的url地址会以xxx.do或xxx.action等结尾，并且在使用get方式请求时 参数的拼接以等于号进行传值 例如 id=1&name=tom
	localhost:8080/user.action?id=1
	使用restful风格则更加易于阅读
	localhot:8080/user/1

传统操作资源的方式：
	通过不同的参数实现不同的效果，方法单一
	例如
		localhost:8080/item/query.action?id=1	GET方式查询
		localhost:8080/item/save.action			POST方式新增
		localhost:8080/item/update.action		POST方式更新
		localhost:8080/item/delete.action?id=1	GET方式删除
RESTFul风格操作资源方式：
	通过不同的请求方式来实现不同的效果
	例如
		localhost:8080/item/q/1		GET方式查询
		localhost:8080/item/		POST方式新增
		localhost:8080/item/		POST方式更新
		localhost:8080/item/1		GET方式删除
```

#### 编写Controller

沿用Controller模块

##### 1、传统方式

```java
package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class RestfulController {

    // 传统方式
    @RequestMapping("/add")
    public String test1(int a, int b, Model model){
        model.addAttribute("msg","result = "+(a+b));
        return "hello";
    }

}

```

![image-20230521183527879](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211835008.png)

##### 2、Restful风格方式

###### RequestMapping普通模式

```java
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
```

![image-20230521184210122](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211842108.png)

###### RequestMapping通过method属性指定请求方式

```java
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
```

![image-20230521185304607](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211853306.png)

###### 请求路径相同实现功能不同

```java
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
```

![image-20230521185749443](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211857811.png)

![image-20230521190206919](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211902383.png)

### 重定向和请求转发

```
SpringMVC本质也是一个Servlet 我们写的Servlet需要继承HttpServlet 然后去重写doGet和doPost方法
SpringMVC则是通过doService方法进行处理请求的
在SpringMVC中 HttpServletRequest,HttpServletResponse依旧可以直接使用

```

#### Controller测试

##### 1、使用Servlet

测试Servlet是否可直接使用

```java
    @RequestMapping("/test6")
    public String test(HttpServletRequest request, HttpServletResponse response
            ,Model model){

        String id = request.getSession().getId();

        model.addAttribute("msg",id);

        return "hello";
    }
```

![image-20230521191806868](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211918094.png)

##### 2、请求转发和重定向

如果我们不使用视图解析器 - 注释掉springmvc配置文件中的视图解析器

那么要通过重定向和请求转发的方式跳转到页面

controller

```java
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
    model.addAttribute("msg","重定向 return \"redirect:/index.jsp\"");
    // 重定向
    return "redirect:/index.jsp";
}
```

![image-20230521193026120](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211930429.png)

![image-20230521193039078](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211930308.png)



![image-20230521193424980](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305211934325.png)

#### 小结

```
在不使用视图解析器的情况下 通过重定向和请求转发方式同样可以跳转到页面
如果视图解析器和请求转发、重定向同时存在 则转发和重定向优先级高于视图解析器 因为是直接嵌入到代码中的
```

### 请求参数及回显

沿用controller模块

#### 父项目中添加lombok依赖

```
 <dependency>
 <groupId>org.projectlombok</groupId>
 <artifactId>lombok</artifactId>
 <version>1.18.26</version>
 </dependency>
```

#### 创建User实体类

```java
package com.etjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
}

```

#### 创建Controller

```java
package com.etjava.controller;

import com.etjava.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
    请求参数和回显
 */
@Controller
@RequestMapping("/param")
public class ParamController {


    // url中参数与controller中参数名相同
    // http://localhost:8080/springmvc_04_controller_war_exploded/param/t1?name=Tom
    @RequestMapping("/t1")
    public String t1(String name, Model model){
        model.addAttribute("msg",name);
        return "hello";
    }

    // url中参数名与controller中参数名不同
    //@RequestParam("username") 通过该注解指定具体的参数名
    // 注意 指定后你的url参数要与其保持一致 否则接收不到
    // 即 http://localhost:8080/springmvc_04_controller_war_exploded/param/t2?username=Tom
    @RequestMapping("/t2")
    public String t2(@RequestParam("username") String name, Model model){
        model.addAttribute("msg",name);
        return "hello";
    }

    // 接收对象作为参数
    // 前端传递的数据会自动封装到对象中对应的字段里面
    // 例如 http://localhost:8080/springmvc_04_controller_war_exploded/param/t3?name=Tom
    @RequestMapping("/t3")
    public String t3(User user, Model model){
        model.addAttribute("msg",user.getName());
        return "hello";
    }
}

```

#### 测试

![image-20230521201130358](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305212011504.png)

![image-20230521201207190](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305212012458.png)

![image-20230521201244717](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305212012251.png)

### 中文乱码问题

#### 过滤器方式解决乱码

web.xml

```java
 <!--编码过滤器 springmvc提供的-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

```

如果还是出现中文乱码 则使用自定义编码过滤器

```java
package com.etjava.controller;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
/**
 * 解决get和post请求 全部乱码的过滤器
 */
public class GenericEncodingFilter implements Filter {
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //处理response的字符编码
        HttpServletResponse myResponse=(HttpServletResponse) response;
        myResponse.setContentType("text/html;charset=UTF-8");
        // 转型为与协议相关对象
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 对request包装增强
        HttpServletRequest myrequest = new MyRequest(httpServletRequest);
        chain.doFilter(myrequest, response);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
//自定义request对象，HttpServletRequest的包装类
class MyRequest extends HttpServletRequestWrapper {
    private HttpServletRequest request;
    //是否编码的标记
    private boolean hasEncode;
    //定义一个可以传入HttpServletRequest对象的构造函数，以便对其进行装饰
    public MyRequest(HttpServletRequest request) {
        super(request);// super必须写
        this.request = request;
    }
    // 对需要增强方法 进行覆盖
    @Override
    public Map getParameterMap() {
        // 先获得请求方式
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post")) {
            // post请求
            try {
                // 处理post乱码
                request.setCharacterEncoding("utf-8");
                return request.getParameterMap();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (method.equalsIgnoreCase("get")) {
            // get请求
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (!hasEncode) { // 确保get手动编码逻辑只运行一次
                for (String parameterName : parameterMap.keySet()) {
                    String[] values = parameterMap.get(parameterName);
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            try {
                                // 处理get乱码
                                values[i] = new String(values[i]
                                        .getBytes("ISO-8859-1"), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                hasEncode = true;
            }
            return parameterMap;
        }
        return super.getParameterMap();
    }
    //取一个值
    @Override
    public String getParameter(String name) {
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(name);
        if (values == null) {
            return null;
        }
        return values[0]; // 取回参数的第一个值
    }
    //取所有值
    @Override
    public String[] getParameterValues(String name) {
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(name);
        return values;
    }
}
```

web.xml中配置filter

```xml
<filter>
    <filter-name>encoding</filter-name>
    <filter-class>com.etjava.controller.GenericEncodingFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>encoding</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

![image-20230521205513263](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305212055548.png)

### JSON

```
什么是JSON?
	JSON(JavaScript Object Notation, JS 对象标记) 是一种轻量级的数据交换格式，目前使用特别广泛
	采用完全独立于编程语言的文本格式来存储和表示数据。
    简洁和清晰的层次结构使得 JSON 成为理想的数据交换语言。
    易于人阅读和编写，同时也易于机器解析和生成，并有效地提升网络传输效率。
    在 JavaScript 语言中，一切都是对象。因此，任何JavaScript 支持的类型都可以通过 JSON 来表示
    例如字符串、数字、对象、数组等
        {"name": "QinJiang"}
        {"age": "3"}
        {"sex": "男"}
JSON在前后端分离的情况下 传输数据
在SpringMVC中通过@RestController 或在方法上添加@ResponseBody注解可以将数据直接转成JSON

```

#### 新建模块

![image-20230523182807198](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305231828459.png)

#### Controller返回JSON数据

导入JSON的依赖包

这里使用Jackson 除此之外json的工具包还有alibaba的fastJson和org.json 我们这里使用Jackson

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>
</dependency>
```

#### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--编码过滤器 springmvc提供的-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--<filter>
        <filter-name>encoding</filter-name>
        <filter-class>com.etjava.controller.GenericEncodingFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>-->

    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--
        配置需要拦截的请求
        / 匹配所有请求 但不包括.jsp
        /* 匹配所有请求 包括.jsp
    -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

#### springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--自动扫描包 让指定包下的注解生效-->
    <context:component-scan base-package="com.etjava.controller" />

    <!--不处理静态资源-->
    <mvc:default-servlet-handler />
    <!--
        支持MVC注解驱动
        在Spring中一般采用@RequestMapping注解来完成映射关系
        要想使@RequestMapping生效 必须在Spring的上下文中进行注册DefaultAnnotationHandlerMapping
        和AnnotationMethodHandlerAdapter实例
        这两个实例分别在类级别和方法级别处理
        而 annotation-driver可以帮助我们自动完成上述两个实例的注入
    -->
    <mvc:annotation-driven />

    <!--添加视图解析器-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--配置前缀-->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!--后缀-->
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

#### 创建实体类

用于测试json

```java
package com.etjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
}
```

#### JSONController测试json

```java
package com.etjava.controller;

import com.etjava.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/json")
public class JSONController {

    @RequestMapping("/t1")
    public String t1(Model model){
        model.addAttribute("msg",121212);
        return "json";
    }

    @RequestMapping("/t2")
	@ResponseBody
    public String json1() throws JsonProcessingException {
        //创建一个jackson的对象映射器，用来解析数据
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user = new User(1, "张三", 21);
        //将我们的对象解析成为json格式
        String str = mapper.writeValueAsString(user);
        //由于@ResponseBody注解，这里会将str转成json格式返回；十分方便
        return str;
    }
}

```

##### 异常解决

```
NoSuchMethodError: javax.servlet.http.HttpServletResponse
javax.servlet.http.HttpServletResponse.setContentLengthLong(J)V
如果出现上述异常 是由于Spring版本过高导致 需要降低到5.3版本以下 
例如
<dependency>
<groupId>org.springframework</groupId>
<artifactId>spring-webmvc</artifactId>
<version>5.2.9.RELEASE</version>
</dependency>
```

![image-20230523185818593](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305231858831.png)

##### 乱码解决

```
在requestmapping上添加返回的字符集编码即可
//produces:指定响应体返回类型和编码
@RequestMapping(value = "/t2",produces = "application/json;charset=utf-8")
```

![image-20230523190957783](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305231909533.png)

##### 乱码统一解决

```
上边那种在@RequestMapping中解决中文乱码的方式比较low 如果有多个地方都涉及到中文 则每个地方都要写一次
下边我们就统一解决乱码问题
在springmvc的配置文件上添加一段消息StringHttpMessageConverter转换配置
```

springmvc-servlet.xml

```xml
<mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```

##### 返回JSON窜统一解决

```
如果使用上述@ResponseBody 只能作用域一个请求的方法上 如果是多个方法都需要返回json格式 那么每个都需要添加
可以使用@RestController替代Controller来解决这个问题
```

JSONController

```java
package com.etjava.controller;

import com.etjava.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    //produces:指定响应体返回类型和编码
    //@RequestMapping(value = "/t2",produces = "application/json;charset=utf-8")
    @RequestMapping("/t2")
    public String json1() throws JsonProcessingException {
        //创建一个jackson的对象映射器，用来解析数据
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user = new User(1, "张三", 21);
        //将我们的对象解析成为json格式
        String str = mapper.writeValueAsString(user);
        //由于@ResponseBody注解，这里会将str转成json格式返回；十分方便
        return str;
    }
}

```

##### 测试集合输出

```java
 @RequestMapping("/t3")
    public String json2() throws JsonProcessingException {
        //创建一个jackson的对象映射器，用来解析数据
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user1 = new User(1, "Tom", 12);
        User user2 = new User(2, "Jerry", 13);
        User user3 = new User(3, "Andy", 14);
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        //将我们的对象解析成为json格式
        String str = mapper.writeValueAsString(list);
        return str;
    }
```

![image-20230523192033717](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305231920934.png)

##### 输出时间对象

```java
@RequestMapping("/json3")
public String json3() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    //创建时间一个对象，java.util.Date
    Date date = new Date();
    //将我们的对象解析成为json格式
    String str = mapper.writeValueAsString(date);
    return str;
}
```

![image-20230523192218863](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305231922139.png)

```
默认日期格式会变成一个数字，是1970年1月1日到当前日期的毫秒数！
Jackson 默认是会把时间转成timestamps形式
取消timestamps形式 ， 自定义时间格式

```

```java
@RequestMapping("/t5")
    public String json4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //不使用时间戳的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //自定义日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //指定日期格式
        mapper.setDateFormat(sdf);
        Date date = new Date();
        String str = mapper.writeValueAsString(date);
        return str;
    }
```

![image-20230523192356055](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305231923278.png)

##### 将时间抽取为工具类

```
如果要经常使用的话，这样是比较麻烦的，我们可以将这些代码封装到一个工具类中
```

```java
package com.etjava.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
public class JsonUtils {
    public static String getJson(Object object) {
        return getJson(object,"yyyy-MM-dd HH:mm:ss");
    }
    public static String getJson(Object object,String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        //不使用时间差的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //自定义日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        //指定日期格式
        mapper.setDateFormat(sdf);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

##### JSONController中使用工具类

```java
@RequestMapping("/t6")
    public String json5() throws JsonProcessingException {
        Date date = new Date();
        String json = JsonUtils.getJson(date);
        return json;
    }
```

![image-20230523192703768](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305231927312.png)

#### FastJson

```
fastjson是阿里开发的一款专门用于Java开发的包，可以方便的实现json对象与JavaBean对象的转换，实现JavaBean对象与json字符串的转换，实现json对象与json字符串的转换。实现json的转换方法很多，最后的实现结果都是一样的
```

##### 添加依赖

要用FastJson肯定要先引入依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.60</version>
</dependency>
```

```
Fasejson的三个主要类

	JSONObject 表示 json 对象
		JSONObject实现了Map接口, JSONObject底层操作是由Map实现的。
		JSONObject对应json对象，通过各种形式的get()方法可以获取json对象中的数据，
		也可利用诸如size()，isEmpty()等方法获取”键：值”对的个数和判断是否为空。
		其本质是通过实现Map接口并调用接口中的方法完成的

	JSONArray 表示 json 对象数组
		内部是有List接口中的方法来完成操作的
		
	JSON 表示 JSONObject和JSONArray的转化
		JSON类源码分析与使用
		仔细观察这些方法，主要是实现json对象，json对象数组，javabean对象，json字符串之间的相互转化
```

测试demo

也可封装为当作工具类使用

```java
package com.etjava.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etjava.model.User;

import java.util.ArrayList;
import java.util.List;

public class FastJsonDemo {
    public static void main(String[] args) {
        //创建一个对象
        User user1 = new User(1, "Tom", 21);
        User user2 = new User(2, "Jerry", 21);
        User user3 = new User(3, "Lily", 21);
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        System.out.println("*******Java对象 转 JSON字符串*******");
        String str1 = JSON.toJSONString(list);
        System.out.println("JSON.toJSONString(list)==>"+str1);
        String str2 = JSON.toJSONString(user1);
        System.out.println("JSON.toJSONString(user1)==>"+str2);
        System.out.println("\n****** JSON字符串 转 Java对象*******");
        User jp_user1=JSON.parseObject(str2,User.class);
        System.out.println("JSON.parseObject(str2,User.class)==>"+jp_user1);
        System.out.println("\n****** Java对象 转 JSON对象 ******");
        JSONObject jsonObject1 = (JSONObject) JSON.toJSON(user2);
        System.out.println("(JSONObject) JSON.toJSON(user2)==>"+jsonObject1.getString("name"));
        System.out.println("\n****** JSON对象 转 Java对象 ******");
        User to_java_user = JSON.toJavaObject(jsonObject1, User.class);
        System.out.println("JSON.toJavaObject(jsonObject1, User.class)==>"+to_java_user);
    }
}
```

![image-20230523194310368](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305231943653.png)

### Ajax收发请求

```
AJAX = Asynchronous JavaScript and XML（异步的 JavaScript 和 XML）
AJAX 是一种在无需重新加载整个网页的情况下，能够更新部分网页的技术
Ajax 不是一种新的编程语言，而是一种用于创建更好更快以及交互性更强的Web应用程序的技术
在 2005 年，Google 通过其 Google Suggest 使 AJAX 变得流行起来。Google Suggest能够自动帮你完成搜索单词
传统的网页(即不用ajax技术的网页)，想要更新内容或者提交一个表单，都需要重新加载整个网页。
使用ajax技术的网页，通过在后台服务器进行少量的数据交换，就可以实现异步局部更新。
使用Ajax，用户可以创建接近本地桌面应用的直接、高可用、更丰富、更动态的Web用户界面。

利用AJAX可以做什么？
    注册时，输入用户名自动检测用户是否已经存在
    登陆时，提示用户名密码错误
    删除数据行时，将行ID发送到后台，后台在数据库中删除，数据库删除成功后，在页面DOM中将数据行也删除 等等

```

#### 新建模块

![image-20230523205110494](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305232051251.png)

#### 伪Ajax测试

```
我们可以使用前端的一个标签来伪造一个ajax的样子使用iframe标签
新建一个module sspringmvc-06-ajax ， 导入web支持！
编写一个 ajax-frame.html 使用 iframe 测试，体验下效果
```

```html
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>伪Ajax</title>
</head>
<body>
<script type="text/javascript">
    window.onload = function(){
        var myDate = new Date();
        document.getElementById('currentTime').innerText = myDate.getTime();
    };
    function LoadPage(){
        var targetUrl =  document.getElementById('url').value;
        console.log(targetUrl);
        document.getElementById("iframePosition").src = targetUrl;
    }
</script>
<div>
    <p>请输入要加载的地址：<span id="currentTime"></span></p>
    <p>
        <input id="url" type="text" value="https://spring.io/"/>
        <input type="button" value="提交" onclick="LoadPage()">
    </p>
</div>
<div>
    <h3>加载页面位置：</h3>
    <iframe id="iframePosition" style="width: 100%;height: 500px;"></iframe>
</div>
</body>
</html>
```

![image-20230523210223690](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305232102091.png)

#### jQuery.ajax

```
纯JS原生实现Ajax我们不在过多赘述，直接使用jquery提供的，方便学习和使用，避免重复造轮子，有兴趣可以去回顾下JS原生XMLHttpRequest
Ajax的核心是XMLHttpRequest对象(XHR)。XHR为向服务器发送请求和解析服务器响应提供了接口。能够以异步方式从服务器获取新数据。
jQuery 提供多个与 AJAX 有关的方法。
通过 jQuery AJAX 方法，您能够使用 HTTP Get 和 HTTP Post 从远程服务器上请求文本、HTML、XML 或 JSON – 同时您能够把这些外部数据直接载入网页的被选元素中。
jQuery 不是生产者，而是大自然搬运工。
jQuery Ajax本质就是 XMLHttpRequest，对他进行了封装，方便调用
```

##### jquery方式ajax解释

```js
jQuery.ajax(...)
       部分参数：
              url：请求地址
             type：请求方式，GET、POST（1.9.0之后用method）
          headers：请求头
             data：要发送的数据
      contentType：即将发送信息至服务器的内容编码类型(默认: "application/x-www-form-urlencoded; charset=UTF-8")
            async：是否异步
          timeout：设置请求超时时间（毫秒）
       beforeSend：发送请求前执行的函数(全局)
         complete：完成之后执行的回调函数(全局)
          success：成功之后执行的回调函数(全局)
            error：失败之后执行的回调函数(全局)
          accepts：通过请求头发送给服务器，告诉服务器当前客户端课接受的数据类型
         dataType：将服务器端返回的数据转换成指定类型
            "xml": 将服务器端返回的内容转换成xml格式
           "text": 将服务器端返回的内容转换成普通文本格式
           "html": 将服务器端返回的内容转换成普通文本格式，在插入DOM中时，如果包含JavaScript标签，则会尝试去执行。
         "script": 尝试将返回值当作JavaScript去执行，然后再将服务器端返回的内容转换成普通文本格式
           "json": 将服务器端返回的内容转换成相应的JavaScript对象
          "jsonp": JSONP 格式使用 JSONP 形式调用函数时，如 "myurl?callback=?" jQuery 将自动替换 ? 为正确的函数名，以执行回调函数
```

###### 原始HttpServletResponse处理

直接写一个数据流出去

###### 配置web.xml

在之前的模块中copy一份即可 不需要改动

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--编码过滤器 springmvc提供的-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--<filter>
        <filter-name>encoding</filter-name>
        <filter-class>com.etjava.controller.GenericEncodingFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>-->

    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--
        配置需要拦截的请求
        / 匹配所有请求 但不包括.jsp
        /* 匹配所有请求 包括.jsp
    -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

###### 配置springmvc.xml

需要注意开启注解、静态资源过滤、扫描包

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 自动扫描指定的包，下面所有注解类交给IOC容器管理 -->
    <context:component-scan base-package="com.etjava.controller"/>
    <mvc:default-servlet-handler />
    <mvc:annotation-driven />
    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id="internalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

###### 编写测试controller

```java
package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AjaxController {
    @RequestMapping("/a1")
    public void ajax1(String name , HttpServletResponse response) throws IOException {
        if ("admin".equals(name)){
            response.getWriter().print("true");
        }else{
            response.getWriter().print("false");
        }
    }
}
```

###### 导入jquery

```
导入jquery 可以使用在线的CDN 也可以下载导入
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/statics/js/jquery-3.1.1.min.js"></script>
```

###### 修改index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <%--<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>--%>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.1.1.min.js"></script>
    <script>
        function a1(){
            $.post({
                url:"${pageContext.request.contextPath}/a1",
                data:{'name':$("#txtName").val()},
                success:function (data,status) {
                    alert(data);
                    alert(status);
                }
            });
        }
    </script>
  </head>
  <body>
  <%--onblur：失去焦点触发事件--%>
  用户名:<input type="text" id="txtName" onblur="a1()"/>
  </body>
</html>
```

###### 测试

当鼠标离开输入框时会触发调用后台请求

![image-20230524204559436](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305242047568.png)

##### SpringMVC实现ajax调用后台

###### 添加Jackson依赖

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>
</dependency>
```



###### 创建User实体类

```java
package com.etjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private int age;
    private String gender;
}
```

###### 修改Controller 

添加返回List方法

```java
@RequestMapping("/a2")
public List<User> ajax2(){
    List<User> list = new ArrayList<User>();
    list.add(new User("Tom",3,"男"));
    list.add(new User("Jerry",5,"男"));
    list.add(new User("Andy",7,"男"));
    return list; //由于@RestController注解，将list转成json格式返回
}
```

###### 编写前端页面

showUser.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<input type="button" id="btn" value="获取数据"/>
<table width="80%" align="center">
  <tr>
    <td>姓名</td>
    <td>年龄</td>
    <td>性别</td>
  </tr>
  <tbody id="content">
  </tbody>
</table>
<script src="${pageContext.request.contextPath}/statics/js/jquery-3.1.1.min.js"></script>
<script>
  $(function () {
    $("#btn").click(function () {
      $.post("${pageContext.request.contextPath}/a2",function (data) {
        console.log(data)
        var html="";
        for (var i = 0; i <data.length ; i++) {
          html+= "<tr>" +
                  "<td>" + data[i].name + "</td>" +
                  "<td>" + data[i].age + "</td>" +
                  "<td>" + data[i].gender + "</td>" +
                  "</tr>"
        }
        $("#content").html(html);
      });
    })
  })
</script>
</body>
</html>
```

###### 测试

![image-20230524210326652](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305242103236.png)

##### Ajax实现注册提示效果

###### 编写登录页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ajax</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.1.1.min.js"></script>
    <script>
        function a1(){
            $.post({
                url:"${pageContext.request.contextPath}/a3",
                data:{'name':$("#name").val()},
                success:function (data) {
                    if (data.toString()=='OK'){
                        $("#userInfo").css("color","green");
                    }else {
                        $("#userInfo").css("color","red");
                    }
                    $("#userInfo").html(data);
                }
            });
        }
        function a2(){
            $.post({
                url:"${pageContext.request.contextPath}/a3",
                data:{'pwd':$("#pwd").val()},
                success:function (data) {
                    if (data.toString()=='OK'){
                        $("#pwdInfo").css("color","green");
                    }else {
                        $("#pwdInfo").css("color","red");
                    }
                    $("#pwdInfo").html(data);
                }
            });
        }
    </script>
</head>
<body>
<p>
    用户名:<input type="text" id="name" onblur="a1()"/>
    <span id="userInfo"></span>
</p>
<p>
    密码:<input type="text" id="pwd" onblur="a2()"/>
    <span id="pwdInfo"></span>
</p>
</body>
</html>
```

###### Controller中添加验证请求

```java
@RequestMapping("/a3")
public String ajax3(String name,String pwd){
    String msg = "";
    //模拟数据库中存在数据
    if (name!=null){
        if ("admin".equals(name)){
            msg = "OK";
        }else {
            msg = "用户名输入错误";
        }
    }
    if (pwd!=null){
        if ("123456".equals(pwd)){
            msg = "OK";
        }else {
            msg = "密码输入有误";
        }
    }
    return msg; //由于@RestController注解，将msg转成json格式返回
}
```

###### 处理中文乱码问题

springmvc-servlet.xml

```xml
<mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```

![image-20230524211015374](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305242112038.png)

##### 扩展 百度搜索

baidu.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>JSONP百度搜索</title>
  <style>
    #q{
      width: 500px;
      height: 30px;
      border:1px solid #ddd;
      line-height: 30px;
      display: block;
      margin: 0 auto;
      padding: 0 10px;
      font-size: 14px;
    }
    #ul{
      width: 520px;
      list-style: none;
      margin: 0 auto;
      padding: 0;
      border:1px solid #ddd;
      margin-top: -1px;
      display: none;
    }
    #ul li{
      line-height: 30px;
      padding: 0 10px;
    }
    #ul li:hover{
      background-color: #f60;
      color: #fff;
    }
  </style>
  <script>
    // 2.步骤二
    // 定义demo函数 (分析接口、数据)
    function demo(data){
      var Ul = document.getElementById('ul');
      var html = '';
      // 如果搜索数据存在 把内容添加进去
      if (data.s.length) {
        // 隐藏掉的ul显示出来
        Ul.style.display = 'block';
        // 搜索到的数据循环追加到li里
        for(var i = 0;i<data.s.length;i++){
          html += '<li>'+data.s[i]+'</li>';
        }
        // 循环的li写入ul
        Ul.innerHTML = html;
      }
    }
    // 1.步骤一
    window.onload = function(){
      // 获取输入框和ul
      var Q = document.getElementById('q');
      var Ul = document.getElementById('ul');
      // 事件鼠标抬起时候
      Q.onkeyup = function(){
        // 如果输入框不等于空
        if (this.value != '') {
          // ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆JSONPz重点☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
          // 创建标签
          var script = document.createElement('script');
          //给定要跨域的地址 赋值给src
          //这里是要请求的跨域的地址 我写的是百度搜索的跨域地址
          script.src = 'https://sp0.baidu.com/5a1Fazu8AA54nxGko9WTAnF6hhy/su?wd='+this.value+'&cb=demo';
          // 将组合好的带src的script标签追加到body里
          document.body.appendChild(script);
        }
      }
    }
  </script>
</head>
<body>
<input type="text" id="q" />
<ul id="ul">
</ul>
</body>
</html>

```

![image-20230524211320276](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305242113811.png)

### 拦截器

```
SpringMVC的处理器拦截器类似于Servlet开发中的过滤器Filter,用于对处理器进行预处理和后处理。我们可以自己定义一些拦截器来实现特定的功能。


过滤器（Filter）
	servlet规范中的一部分，任何java web工程都可以使用
	在url-pattern中配置了/*之后，可以对所有要访问的资源进行拦截
拦截器
	拦截器是SpringMVC框架自己的，只有使用了SpringMVC框架的工程才能使用
	拦截器只会拦截访问的控制器方法， 如果访问的是jsp/html/css/image/js是不会进行拦截的
	
过滤器与拦截器的区别：拦截器是AOP思想的具体应用。

```

#### 自定义拦截器

想要自定义拦截器，必须实现 HandlerInterceptor 接口

##### 新建模块项目

![image-20230524212033915](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305242120394.png)

##### 配置web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--编码过滤器 springmvc提供的-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--<filter>
        <filter-name>encoding</filter-name>
        <filter-class>com.etjava.controller.GenericEncodingFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>-->

    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--
        配置需要拦截的请求
        / 匹配所有请求 但不包括.jsp
        /* 匹配所有请求 包括.jsp
    -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

##### springmvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 自动扫描指定的包，下面所有注解类交给IOC容器管理 -->
    <context:component-scan base-package="com.etjava.controller"/>
    <mvc:default-servlet-handler />
    <mvc:annotation-driven />
    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id="internalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>

    <!--处理中文请求乱码-->
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
</beans>
```

##### 创建拦截器

```java
package com.etjava.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class MyInterceptor implements HandlerInterceptor {
    //在请求处理的方法之前执行
    //如果返回true执行下一个拦截器
    //如果返回false就不执行下一个拦截器
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("------------处理前------------");
        return true;
    }
    //在请求处理方法执行之后执行
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("------------处理后------------");
    }
    //在dispatcherServlet处理后执行,做清理工作.
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("------------清理------------");
    }
}
```

##### springmvc中配置拦截器

修改springmvc-servlet.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 自动扫描指定的包，下面所有注解类交给IOC容器管理 -->
    <context:component-scan base-package="com.etjava.controller"/>
    <mvc:default-servlet-handler />
    <mvc:annotation-driven />
    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id="internalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>

    <!--配置拦截器-->
    <!--关于拦截器的配置-->
    <mvc:interceptors>
        <mvc:interceptor>
            <!--/** 包括路径及其子路径-->
            <!--/admin/* 拦截的是/admin/add等等这种 , /admin/add/user不会被拦截-->
            <!--/admin/** 拦截的是/admin/下的所有-->
            <mvc:mapping path="/**"/>
            <!--bean配置的就是拦截器-->
            <bean class="com.etjava.interceptor.MyInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>

    <!--处理中文请求乱码-->
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
</beans>
```

##### Controller处理请求

```java
package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//测试拦截器的控制器
@Controller
public class TestInterceptorController {
    @RequestMapping("/interceptor")
    @ResponseBody
    public String testFunction() {
        System.out.println("控制器中的方法执行了");
        return "hello";
    }
}

```

##### index.jsp

添加测试拦截器链接

```jsp
<a href="${pageContext.request.contextPath}/interceptor">拦截器测试</a>
```

##### 测试

![image-20230524213732519](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305242137036.png)

#### 用户认证

使用拦截器方式实现用户登录认证

```
实现思路
	有一个登陆页面，需要写一个controller访问页面。
    登陆页面有一提交表单的动作。需要在controller中处理。判断用户名密码是否正确。
    如果正确，向session中写入用户信息。返回登陆成功。
    拦截用户请求，判断用户是否登陆。如果用户已经登陆。放行， 如果用户未登陆，跳转到登陆页面
```

##### 编写登录页面

login.jsp 该页面在WEB-INF/jsp目录下

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<h1>登录页面</h1>
<hr>
<body>
<form action="${pageContext.request.contextPath}/user/login">
    用户名：<input type="text" name="username"> <br>
    密码： <input type="password" name="pwd"> <br>
    <input type="submit" value="提交">
</form>
</body>
</html>
```

##### UserController

处理用户请求

```java
package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    //跳转到登陆页面
    @RequestMapping("/jumplogin")
    public String jumpLogin() throws Exception {
        return "login";
    }
    //跳转到成功页面
    @RequestMapping("/jumpSuccess")
    public String jumpSuccess() throws Exception {
        return "success";
    }
    //登陆提交
    @RequestMapping("/login")
    public String login(HttpSession session, String username, String pwd) throws Exception {
        // 向session记录用户身份信息
        System.out.println("接收前端==="+username);
        session.setAttribute("user", username);
        return "success";
    }
    //退出登陆
    @RequestMapping("logout")
    public String logout(HttpSession session) throws Exception {
        // session 过期
        session.invalidate();
        return "login";
    }
}

```

##### 登录成功页面

登录成功时进行跳转的页面 该页面在WEB-INF/jsp目录下

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>登录成功页面</h1>
<hr>
${user}
<a href="${pageContext.request.contextPath}/user/logout">注销</a>
</body>
</html>
```

##### index页面测试跳转

未登录也可以进入到登录成功页面

```jsp
<a href="${pageContext.request.contextPath}/user/jumplogin">登录</a>
<a href="${pageContext.request.contextPath}/user/jumpSuccess">成功页面
```

![image-20230524224456386](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305242254006.png)

##### 登录拦截器

```java
package com.etjava.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        // 如果是登陆页面则放行
        System.out.println("uri: " + request.getRequestURI());
        if (request.getRequestURI().contains("login")) {
            return true;
        }
        HttpSession session = request.getSession();
        // 如果用户已登陆也放行
        if(session.getAttribute("user") != null) {
            return true;
        }
        // 用户没有登陆跳转到登陆页面
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return false;
    }
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
```

##### springmvc-servlet.xml修改

Springmvc的配置文件中注册拦截器

```xml
<!--注册登录拦截器-->
<mvc:interceptors>
    <mvc:interceptor>
        <mvc:mapping path="/**"/>
        <bean id="loginInterceptor" class="com.etjava.interceptor.LoginInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

##### 测试

直接点击登录成功页面会被拦截到登录页面

只有登录后才可以访问登录成功页面

![image-20230524225044111](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305242254245.png)

### 文件上传下载

```
文件上传是项目开发中最常见的功能之一 ,springMVC 可以很好的支持文件上传，但是SpringMVC上下文中默认没有装配MultipartResolver，因此默认情况下其不能处理文件上传工作。如果想使用Spring的文件上传功能，则需要在上下文中配置MultipartResolver
前端表单要求：为了能上传文件，必须将表单的method设置为POST，并将enctype设置为multipart/form-data。只有在这样的情况下，浏览器才会把用户选择的文件以二进制数据发送给服务器；

对表单中的 enctype 属性做个详细的说明：
application/x-www=form-urlencoded：默认方式，只处理表单域中的 value 属性值，采用这种编码方式的表单会将表单域中的值处理成 URL 编码方式。
multipart/form-data：这种编码方式会以二进制流的方式来处理表单数据，这种编码方式会把文件域指定文件的内容也封装到请求参数中，不会对字符编码。
text/plain：除了把空格转换为 “+” 号外，其他字符都不做编码处理，这种方式适用直接通过表单发送邮件。
例如
<form action="" enctype="multipart/form-data" method="post">
    <input type="file" name="file"/>
    <input type="submit">
</form>
一旦设置了enctype为multipart/form-data，浏览器即会采用二进制流的方式来处理表单数据，而对于文件上传的处理则涉及在服务器端解析原始的HTTP响应。在2003年，Apache Software Foundation发布了开源的Commons FileUpload组件，其很快成为Servlet/JSP程序员上传文件的最佳选择。
Servlet3.0规范已经提供方法来处理文件上传，但这种上传需要在Servlet中完成。
而Spring MVC则提供了更简单的封装。
Spring MVC为文件上传提供了直接的支持，这种支持是用即插即用的MultipartResolver实现的。
Spring MVC使用Apache Commons FileUpload技术实现了一个MultipartResolver实现类：CommonsMultipartResolver。因此 SpringMVC的文件上传还需要依赖Apache Commons FileUpload的组件
```



#### 新建模块

![image-20230527162257580](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305271623311.png)



#### 文件上传

##### 添加依赖

```xml
<!--文件上传-->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.3.3</version>
</dependency>
<!--servlet-api导入高版本的-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
</dependency>
// 如果用到了json 需要引入json相关的依赖  springmvc配置文件中引用了
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.9.0</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.0</version>
</dependency>
```

##### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--编码过滤器 springmvc提供的-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--<filter>
        <filter-name>encoding</filter-name>
        <filter-class>com.etjava.controller.GenericEncodingFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>-->

    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联SpringMVC的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--
        配置需要拦截的请求
        / 匹配所有请求 但不包括.jsp
        /* 匹配所有请求 包括.jsp
    -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

##### 配置bean

```
配置bean：multipartResolver
注意 这个bena的id必须为：multipartResolver ， 否则上传文件会报400的错误！在这里栽过坑,教训
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 自动扫描指定的包，下面所有注解类交给IOC容器管理 -->
    <context:component-scan base-package="com.etjava.controller"/>
    <mvc:default-servlet-handler />
    <mvc:annotation-driven />
    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id="internalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>

    <!--文件上传配置-->
    <bean id="multipartResolver"  class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <!-- 请求的编码格式，必须和jSP的pageEncoding属性一致，以便正确读取表单的内容，默认为ISO-8859-1 -->
        <property name="defaultEncoding" value="utf-8"/>
        <!-- 上传文件大小上限，单位为字节（10485760=10M） -->
        <property name="maxUploadSize" value="10485760"/>
        <property name="maxInMemorySize" value="40960"/>
    </bean>
    <!--处理JSON中文请求乱码-->
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
</beans>
```

##### index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form action="/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file"/>
    <input type="submit" value="upload">
  </form>
  </body>
</html>

```

##### FileController

```java
package com.etjava.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
@Controller
public class FileController {
    //@RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
    //批量上传CommonsMultipartFile则为数组即可
    @RequestMapping("/upload")
    public String fileUpload(@RequestParam("file") CommonsMultipartFile file , HttpServletRequest request) throws IOException {
        //获取文件名 : file.getOriginalFilename();
        String uploadFileName = file.getOriginalFilename();
        //如果文件名为空，直接回到首页！
        if ("".equals(uploadFileName)){
            return "redirect:/show.jsp";
        }
        System.out.println("上传文件名 : "+uploadFileName);
        //上传路径保存设置
        String path = request.getServletContext().getRealPath("/upload");
        //如果路径不存在，创建一个
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        System.out.println("上传文件保存地址："+realPath);
        InputStream is = file.getInputStream(); //文件输入流
        OutputStream os = new FileOutputStream(new File(realPath,uploadFileName)); //文件输出流
        //读取写出
        int len=0;
        byte[] buffer = new byte[1024];
        while ((len=is.read(buffer))!=-1){
            os.write(buffer,0,len);
            os.flush();
        }
        os.close();
        is.close();
        return "redirect:/index.jsp";
    }
}
```

##### 测试

![image-20230527164514226](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305271645038.png)

##### 保存上传的文件

使用file.Transto 来保存上传的文件

修改controller

```java
 /*
     * 使用file.Transto 来保存上传的文件
     */
    @RequestMapping("/upload2")
    public String  fileUpload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        //上传路径保存设置
        String path = request.getServletContext().getRealPath("/upload");
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        //上传文件地址
        System.out.println("上传文件保存地址："+realPath);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new File(realPath +"/"+ file.getOriginalFilename()));
        return "redirect:/index.jsp";
    }
```

##### 测试保存上传的文件

前端上传页面修改请求路径 人后进行测试

![image-20230527165011506](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305271650080.png)

#### 文件下载

```
文件下载步骤：

设置 response 响应头
读取文件 — InputStream
写出文件 — OutputStream
执行操作
关闭流 （先开后关）
```

##### DownloadController

```java
package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
public class DownloadController {

    @RequestMapping(value="/download")
    public String downloads(HttpServletResponse response , HttpServletRequest request) throws Exception{
        //要下载的图片地址
        String  path = request.getServletContext().getRealPath("/upload");
        String  fileName = "sonwy.jar";
        //1、设置response 响应头
        response.reset(); //设置页面不缓存,清空buffer
        response.setCharacterEncoding("UTF-8"); //字符编码
        response.setContentType("multipart/form-data"); //二进制传输数据
        //设置响应头
        response.setHeader("Content-Disposition",
                "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));
        File file = new File(path,fileName);
        //2、 读取文件--输入流
        InputStream input=new FileInputStream(file);
        //3、 写出文件--输出流
        OutputStream out = response.getOutputStream();
        byte[] buff =new byte[1024];
        int index=0;
        //4、执行 写出操作
        while((index= input.read(buff))!= -1){
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
        return null;
    }
}

```

##### index.jsp

```jsp
<a href="/download">点击下载</a>
```

##### 测试

<img src="C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230527182628999.png" alt="image-20230527182628999" style="zoom:50%;" />


















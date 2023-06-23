package com.etjava.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.etjava.bean.Blog;
import com.etjava.bean.BlogType;
import com.etjava.bean.Link;
import com.etjava.bean.Users;
import com.etjava.service.BlogService;
import com.etjava.service.BlogTypeService;
import com.etjava.service.LinkService;
import com.etjava.service.UsersService;

/**
 * 初始化组件
 *项目启动时先加载一部分数据到application中
 * @author etjav
 *
 */
@Component
public class InitComponent implements ApplicationContextAware,ServletContextListener{
	
	private Logger logger = LoggerFactory.getLogger(InitComponent.class);

	
	private static ApplicationContext ac;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ac = applicationContext;
		System.out.println(ac);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("contextInitialized............ ");
		ServletContext application=sce.getServletContext();
		
		// Blogger 
		UsersService bloggerService=(UsersService) ac.getBean("userService");
		Users blogger=bloggerService.findById(1); // 先固定查询id是1的用户信息
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		// Link
		LinkService linkService=(LinkService) ac.getBean("linkService");
		List<Link> linkList=linkService.list(null); // 查询所有的链接信息
		application.setAttribute("linkList", linkList);
		
		// By Category
		BlogTypeService blogTypeService = (BlogTypeService)ac.getBean("blogTypeService");
		List<BlogType> blogTypeList = blogTypeService.countList();
		application.setAttribute("blogTypeList", blogTypeList);
		
		// By Date
		BlogService blogService = (BlogService)ac.getBean("blogService");
		List<Blog> blogList = blogService.countList();
		application.setAttribute("blogCountList", blogList);
		logger.info("初始化组件加载完成............ ");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("applicaion destoryed......");
	}
	
	
	

	
}

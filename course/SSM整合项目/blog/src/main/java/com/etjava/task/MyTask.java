package com.etjava.task;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.etjava.crawler.CNCrawler;
import com.etjava.service.CrawlerBlogService;

public class MyTask {

	@Resource
	private CrawlerBlogService crawlerBlogService;
	
	
	public void show() { 
		// 获取项目根目录  File.separator 选择当前系统的分隔符
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String realPath = servletContext.getRealPath(File.separator);
			
		new CNCrawler(crawlerBlogService,realPath);
	} 
	 
//	  public void print() { 
//	    System.out.println("print method 1"); 
//	  } 
}

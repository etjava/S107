package com.etjava.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.etjava.bean.Blog;
import com.etjava.bean.BlogType;
import com.etjava.bean.Link;
import com.etjava.bean.Users;
import com.etjava.service.BlogService;
import com.etjava.service.BlogTypeService;
import com.etjava.service.LinkService;
import com.etjava.service.UsersService;
import com.etjava.util.ResponseUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sys")
public class SystemController {

	@Resource
	private UsersService userService;
	@Resource
	private LinkService linkService;
	@Resource
	private BlogTypeService blogTypeService;
	@Resource
	private BlogService blogService;
	
	private Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	@RequestMapping("/refresh")
	public void refesh(HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.info("重新加载缓存数据开始............ ");
		// 获取application
		ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		Users users = userService.findById(1);
		users.setPassword(null);
		application.setAttribute("blogger", users);
		
		List<Link> linkList=linkService.list(null); // 查询所有的链接信息
		application.setAttribute("linkList", linkList);
		
		List<BlogType> blogTypeList = blogTypeService.countList();
		application.setAttribute("blogTypeList", blogTypeList);
		
		List<Blog> blogList = blogService.countList();
		application.setAttribute("blogCountList", blogList);
		
		logger.info("缓存数据重新加载完毕............ ");
		
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
}

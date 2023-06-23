package com.etjava.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.etjava.bean.CrawlerBlog;
import com.etjava.service.CrawlerBlogService;
import com.etjava.util.StringUtil;

@Controller
@RequestMapping("/crawler/blog")
public class CrawlerBlogController {

	private Logger logger = LoggerFactory.getLogger(CrawlerBlogController.class);
	
	@Resource
	private CrawlerBlogService crawlerBlogService;
	
	@RequestMapping("/articles/{id}")
	public ModelAndView articles(@PathVariable("id") Integer id,HttpServletRequest request)throws Exception{
		ModelAndView mav = new ModelAndView();
		CrawlerBlog blog = crawlerBlogService.findById(id);
		
		if(blog==null) {
			request.setAttribute("errorMsg", "java.lang.NullPointerException");
			mav.addObject("pageTitle","ERROR - ETJAVA Blog");
		}else {
			logger.info("查看 crawlerBlog  id = "+id+" title = "+blog.getTitle()+" 的博客内容 ");
			String keyword = blog.getKeyword();
			if(StringUtil.isNotEmpty(keyword)) {
				String[] data = keyword.split(" ");
				List<String> keywordList = StringUtil.filterWhite(Arrays.asList(data));// 剔除数组内多余的空格元素
				mav.addObject("keywords",keywordList);
			}else {
				mav.addObject("keywords",null);
			}
//			blog.setClickHit(blog.getClickHit()+1);
//			crawlerBlogService.update(blog);
			
			
			mav.addObject("blog",blog);
			mav.addObject("pageCode", this.getUpAndDownPageCode(crawlerBlogService.lastBlog(id), crawlerBlogService.nextBlog(id), request.getServletContext().getContextPath()));
			mav.addObject("pageTitle",blog.getTitle()+" - ETJAVA Blog");
		}
		
		
		

		mav.addObject("mainPage","foreground/crawler/view.jsp");
		mav.setViewName("template");
		return mav;
	}
	
	/**
	 * 获取上一篇和下一篇博客
	 * @param lastBlog 上一篇
	 * @param nextBlog 下一篇
	 * @param projectContext  项目的根路径 发布后会映射成域名
	 * @return
	 */
	private String getUpAndDownPageCode(CrawlerBlog lastBlog,CrawlerBlog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>Previous:&nbsp;&nbsp;&nbsp;&nbsp;Nothing</p>");
		}else{
			pageCode.append("<p>Previous:&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+projectContext+"/crawler/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
			
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>Next:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nothing</p>");
		}else{
			pageCode.append("<p>Next:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+projectContext+"/crawler/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
}

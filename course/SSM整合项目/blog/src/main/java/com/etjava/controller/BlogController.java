package com.etjava.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.etjava.bean.Blog;
import com.etjava.bean.Comment;
import com.etjava.lucene.ContentLucene;
import com.etjava.service.BlogService;
import com.etjava.service.CommentService;
import com.etjava.util.StringUtil;

@Controller
@RequestMapping("/blog")
public class BlogController {
	
	

	@Resource
	private BlogService blogService;
	@Resource
	private CommentService commentService;
	
	private ContentLucene index = new ContentLucene();
	
	private Logger logger = LoggerFactory.getLogger(BlogController.class);
	
	
	/*
	 * /articles/{id} 请求后边的id 是获取的@PathVariable("id")里面的值  这个值是点击链接访问的时候传递过来的
	 */
	@RequestMapping("/articles/{id}")
	public ModelAndView articles(@PathVariable("id") Integer id,HttpServletRequest request)throws Exception{
		ModelAndView mav = new ModelAndView();
		Blog blog = blogService.findbyId(id);
		
		if(blog==null) {
			request.setAttribute("errorMsg", "java.lang.NullPointerException");
		}
		
		String keyword = blog.getKeyword();
		if(StringUtil.isNotEmpty(keyword)) {
			String[] data = keyword.split(" ");
			List<String> keywordList = StringUtil.filterWhite(Arrays.asList(data));// 剔除数组内多余的空格元素
			mav.addObject("keywords",keywordList);
		}else {
			mav.addObject("keywords",null);
		}
		
		blog.setClickHit(blog.getClickHit()+1);
		blogService.update(blog);
		Map<String,Object> map = new HashMap<>();
		map.put("state", 1);
		map.put("blogId", blog.getId());
		List<Comment> commentList = commentService.list(map);
		mav.addObject("commentList",commentList);
		mav.addObject("blog",blog);
		mav.addObject("pageCode", this.getUpAndDownPageCode(blogService.lastBlog(id), blogService.nextBlog(id), request.getServletContext().getContextPath()));
		mav.addObject("pageTitle",blog.getTitle()+" - ETJAVA Blog");
		mav.addObject("mainPage","foreground/blog/view.jsp");
		mav.setViewName("template");
		
		logger.info("查看 id = "+id+" title = "+blog.getTitle()+" 的博客内容 ");
		return mav;
	}
	
	/**
	 * 获取上一篇和下一篇博客
	 * @param lastBlog 上一篇
	 * @param nextBlog 下一篇
	 * @param projectContext  项目的根路径 发布后会映射成域名
	 * @return
	 */
	private String getUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>Previous:&nbsp;&nbsp;&nbsp;&nbsp;Nothing</p>");
		}else{
			pageCode.append("<p>Previous:&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
			
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>Next:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nothing</p>");
		}else{
			pageCode.append("<p>Next:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	/**
	 * 根据关键字查询相关博客信息
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="page",required=false) String page,HttpServletRequest request)throws Exception{
		int pageSize=10;
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "search '"+keyword+"' - ETJAVA BLOG");
		mav.addObject("mainPage", "foreground/blog/search.jsp");
		List<Blog> blogList=index.search(keyword);
		/*
		 toIndex = blogList.size()>=Integer.parseInt(page)*pageSize?Integer.parseInt(page)*pageSize:blogList.size(); 
		 例如 25条数据 每页显示10条
		 第一页 toIndex = 25>=(1*10)?:(1*10):25
		 第二页 toIndex = 25>=(2*10)?:(2*10):25
		 第三页 toIndex = 25>=(3*10)?:(3*10):25
		 */
		Integer toIndex=blogList.size()>=Integer.parseInt(page)*pageSize?Integer.parseInt(page)*pageSize:blogList.size();
		mav.addObject("blogList", blogList.subList((Integer.parseInt(page)-1)*pageSize, toIndex));
		mav.addObject("pageCode",this.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), keyword, pageSize, request.getServletContext().getContextPath()));
		mav.addObject("keyword", keyword);
		mav.addObject("resultTotal", blogList.size());
		mav.setViewName("template");
		return mav;
	}
	
	/**
	 * 获取上一页，下一页代码 
	 * @param page
	 * @param totalNum
	 * @param q
	 * @param pageSize
	 * @param projectContext
	 * @return
	 */
	private String genUpAndDownPageCode(Integer page,Integer totalNum,String keyword,Integer pageSize,String projectContext){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		if(totalPage==0){
			return "";
		}else{
			pageCode.append("<nav>");
			pageCode.append("<ul class='pager'>");
			if(page>1){
				pageCode.append("<li><a href='"+projectContext+"/blog/search.html?page="+(page-1)+"&keyword="+keyword+"'>Previous</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>Previous</a></li>");
			}
			if(page<totalPage){
				pageCode.append("<li><a href='"+projectContext+"/blog/search.html?page="+(page+1)+"&keyword="+keyword+"'>Next</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>Next</a></li>");
			}
			pageCode.append("</ul>");
			pageCode.append("</nav>");
		}
		return pageCode.toString();
	}
}

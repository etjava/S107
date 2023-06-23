package com.etjava.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.etjava.bean.CrawlerBlog;
import com.etjava.bean.PageBean;
import com.etjava.bean.Status;
import com.etjava.lucene.CrawlerLucene;
import com.etjava.service.CrawlerBlogService;
import com.etjava.util.DateJsonValueProcessor;
import com.etjava.util.ResponseUtil;
import com.etjava.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 转载文章管理controller
 * @author etjav
 *
 */
@Controller
@RequestMapping("/admin/crawler")
public class AdminCrawlerController {

	private Logger logger = LoggerFactory.getLogger(AdminCrawlerController.class);
	
	private CrawlerLucene index = new CrawlerLucene();
	
	@Resource
	private CrawlerBlogService crawlerBlogService;
	
	
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required = false) String page,
			@RequestParam(value="rows",required = false) String rows,CrawlerBlog blog,
			HttpServletResponse response) throws Exception {
		
		PageBean pageBean = new PageBean(Integer.valueOf(page),Integer.valueOf(rows));
		Map<String,Object> map = new HashMap<>();
		map.put("title", StringUtil.formatLike(blog.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("state", blog.getState());// 不同页面获取不同数据 state是动态的
		
		List<CrawlerBlog> list = crawlerBlogService.list(map);
		Integer total = crawlerBlogService.total(map);
		
		JSONObject jsonObject = new JSONObject();
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", total);
		
		logger.info("获取未爬虫模块未发布消息 "+total+" 条数据 \n"+jsonArray);
		ResponseUtil.write(response, jsonObject);
	}
	
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids",required = true) String ids,
			HttpServletResponse response) throws Exception {
		String[] data = ids.split(",");
		Integer  res = 0;
		for(int i = 0;i<data.length;i++) {
			res	= crawlerBlogService.delete(Integer.parseInt(data[i]));
			
			index.deleteIndex(data[i]);// 删除lucene索引中存在的词片
		}
		JSONObject jsonObject = new JSONObject();
		
		if(res>0) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
		}
		
		ResponseUtil.write(response, jsonObject);
	}
	
	
	@RequestMapping("/findById")
	public void findById(@RequestParam(value="id",required = true) String id,HttpServletResponse response) throws Exception{
		CrawlerBlog blog = crawlerBlogService.findById(Integer.valueOf(id));
		JSONObject result = JSONObject.fromObject(blog);
		ResponseUtil.write(response, result);
	}
	
	// 审核通过
	@RequestMapping("/update")
	public void update(CrawlerBlog blog , HttpServletResponse response) throws Exception {
		blog.setState(1);
		blog.setReleaseDate(new Date());
		Integer total = crawlerBlogService.update(blog);
		JSONObject result = new JSONObject();
		if(total>0) {
			result.put("success", true);
			index.addIndex(blog);
		}else {
			result.put("success", false);
			result.put("errorInfo",Status.ERROR_CODE);
		}
		
		ResponseUtil.write(response, result);
	}
	
	// 驳回
	@RequestMapping("/reject")	
	public void reject(@RequestParam(value="id",required = true) Integer id,
			HttpServletResponse response) throws Exception  {
		CrawlerBlog blog = new CrawlerBlog();
		blog.setState(2);
		blog.setId(id);
		Integer total = 0;
		try {
			total = crawlerBlogService.update(blog);
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			JSONObject result = new JSONObject();
			if(total>0) {
				result.put("success", true);
			}else {
				result.put("success", false);
			}
			ResponseUtil.write(response, result);
		}
		
	}
	
}

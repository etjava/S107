package com.etjava.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.etjava.bean.Blog;
import com.etjava.bean.PageBean;
import com.etjava.bean.Status;
import com.etjava.lucene.ContentLucene;
import com.etjava.service.BlogService;
import com.etjava.util.DateJsonValueProcessor;
import com.etjava.util.ResponseUtil;
import com.etjava.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/admin/blog")
public class AdminController {

	@Resource
	private BlogService blogService;
	
	private ContentLucene index = new ContentLucene();
	
	/**
	 *  添加或修改博客信息
	 * @param blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Blog blog,HttpServletResponse response) throws Exception {
		Integer total = 0;
		if(blog.getId()==null) {
			// insert
			total = blogService.add(blog);
			index.addIndex(blog);// 创建分词 用于全文检索
		}else {
			// update
			total = blogService.update(blog);
			index.updateIndex(blog);
		}
		
		JSONObject result = new JSONObject();
		if(total>0) {
			result.put("success", true);
		}else {
			result.put("success", false);
			result.put("errorInfo",Status.ERROR_CODE);
		}
		
		ResponseUtil.write(response, result);
		
		return null;
	}
	
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required = false) String page,
			@RequestParam(value="rows",required = false) String rows,Blog blog,
			HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.valueOf(page),Integer.valueOf(rows));
		
		Map<String,Object> map = new HashMap<>();
		map.put("title", StringUtil.formatLike(blog.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Blog> list = blogService.list(map);
		Integer total = blogService.total(map);
		// JSON中处理日期对象
//		DateJsonValueProcessor
		JSONObject jsonObject = new JSONObject();
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", total);
		ResponseUtil.write(response, jsonObject);
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids",required = true) String ids,
			HttpServletResponse response) throws Exception {
		String[] data = ids.split(",");
		Integer  res = 0;
		for(int i = 0;i<data.length;i++) {
		res	= blogService.delete(Integer.parseInt(data[i]));
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
	
	/**
	 * 	根据ID获取博客信息
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public void findById(@RequestParam(value="id",required = true) String id,HttpServletResponse response) throws Exception{
		Blog blog = blogService.findbyId(Integer.valueOf(id));
		JSONObject result = JSONObject.fromObject(blog);
		ResponseUtil.write(response, result);
	}
	
}

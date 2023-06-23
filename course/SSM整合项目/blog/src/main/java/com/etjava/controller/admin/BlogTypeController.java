package com.etjava.controller.admin;

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

import com.etjava.bean.BlogType;
import com.etjava.bean.PageBean;
import com.etjava.service.BlogService;
import com.etjava.service.BlogTypeService;
import com.etjava.util.ResponseUtil;
import com.etjava.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeController {
	
	private Logger logger = LoggerFactory.getLogger(BlogTypeController.class);
	
	@Resource
	private BlogTypeService blogTypeService;

	@Resource
	private BlogService blogService2;
	
	/**
	 * 	博客类别查询
	 * @param page
	 * @param rows
	 * @param blogType
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required = false) String page,
			@RequestParam(value="rows",required = false) String rows,BlogType blogType,
			HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.valueOf(page),Integer.valueOf(rows));
		
		Map<String,Object> map = new HashMap<>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		if(blogType!=null && StringUtil.isNotEmpty(blogType.getTypeName())) {
			map.put("typeName", StringUtil.formatLike(blogType.getTypeName()));
		}
		List<BlogType> list = blogTypeService.list(map);
		Integer total = blogTypeService.total(map);
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(list);
		jsonObject.put("rows", jsonArray);// rows 是easyui规定好的key
		jsonObject.put("total", total);// total 也是easyui规定好的key
		
		logger.info("博客类别查询完成 "+jsonObject);
		ResponseUtil.write(response, jsonObject);
	}
	
	
	/**
	 * 	新增或修改博客类别信息
	 * @param blogType
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(BlogType blogType,HttpServletResponse response) throws Exception{
		Integer result = 0;
		if(blogType.getId()!=null) {
			// 修改
			result = blogTypeService.update(blogType);
		}else {
			result = blogTypeService.add(blogType);
		}
		JSONObject jsonObject = new JSONObject();
		if(result>0) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
		}
		
		ResponseUtil.write(response, jsonObject);
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids",required = true) String ids,
			HttpServletResponse response) throws Exception{
		Integer total = 0;
		String[] data = ids.split(",");
		for(int i=0;i<data.length;i++) {
			if(blogService2.countByTypeId(Integer.valueOf(data[i]))<=0) {
				total = blogTypeService.delete(Integer.valueOf(data[i]));
			}
		}
		JSONObject jsonObject = new JSONObject();
		if(total>0) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
			jsonObject.put("errInfo","当前类别下存在博客 不能直接删除.");
		}
		ResponseUtil.write(response, jsonObject);
	}
	
	/**
	 * 	根据ID获取博客类别信息
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public void findById(@RequestParam(value="id",required = true) String id,
			HttpServletResponse response) throws Exception{
		BlogType blogType = blogTypeService.findById(Integer.valueOf(id));
		JSONObject result = JSONObject.fromObject(blogType);
		ResponseUtil.write(response, result);
	}
}

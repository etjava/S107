package com.etjava.controller.admin;

import java.util.Arrays;
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

import com.etjava.bean.Comment;
import com.etjava.bean.PageBean;
import com.etjava.service.CommentService;
import com.etjava.util.DateJsonValueProcessor;
import com.etjava.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {

	private Logger logger = LoggerFactory.getLogger(AdminCommentController.class);
	
	@Resource
	private CommentService commentService;
	
	
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required = false) String page,
			@RequestParam(value="rows",required = false) String rows,
			@RequestParam(value="state",required = false) String state,
			HttpServletResponse response) throws Exception{
		PageBean pageBean = new PageBean(Integer.valueOf(page),Integer.valueOf(rows));
		Map<String,Object> map = new HashMap<>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("state", state);
		Integer total = commentService.total(map);
		List<Comment> list = commentService.list(map);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", total);
		logger.info("查询到评论信息："+jsonObject);
		ResponseUtil.write(response, jsonObject);
	}
	
	@RequestMapping("/review")
	public void review(@RequestParam(value="ids",required = true) String ids,
			@RequestParam(value="state",required = true) String state,
			HttpServletResponse response) throws Exception {
		
		Integer total = 0;
		String[] data = ids.split(",");
		for(int i=0;i<data.length;i++) {
			Comment comment = new Comment();
			comment.setId(Integer.valueOf(data[i]));
			comment.setState(Integer.valueOf(state));
			total += commentService.update(comment);
		}
		JSONObject jsonObject = new JSONObject();
		if(total>0) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
		}
		
		logger.info("批量审核评论功能 "+jsonObject);
		ResponseUtil.write(response, jsonObject);
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids",required = true) String ids,
			HttpServletResponse response) throws Exception {
		String[] data = ids.split(",");
		int[] array = Arrays.asList(data).stream().mapToInt(Integer::parseInt).toArray();
		System.out.println(array);
		Integer total=0;
		for(int i=0;i<data.length;i++) {
			total = commentService.delete(Integer.valueOf(data[i]));
		}
		
		JSONObject jsonObject = new JSONObject();
		if(total>0) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
		}
		logger.info("删除评论信息 id in "+data);
		ResponseUtil.write(response, jsonObject);
	}
}

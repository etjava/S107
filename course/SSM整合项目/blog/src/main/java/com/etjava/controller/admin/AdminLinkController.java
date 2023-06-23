package com.etjava.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.etjava.bean.Link;
import com.etjava.bean.PageBean;
import com.etjava.bean.Status;
import com.etjava.service.LinkService;
import com.etjava.util.ResponseUtil;
import com.etjava.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/link")
public class AdminLinkController {

	
	@Resource
	private LinkService linkService;
	
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required = false) String page,
			@RequestParam(value="rows",required = false) String rows,Link link,
			HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.valueOf(page),Integer.valueOf(rows));
		
		Map<String,Object> map = new HashMap<>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		if(link!=null && StringUtil.isNotEmpty(link.getLinkName())) {
			map.put("linkName", StringUtil.formatLike(link.getLinkName()));
		}
		List<Link> list = linkService.list(map);
		Integer total = linkService.total(map);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(list);
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", total);
		ResponseUtil.write(response, jsonObject);
	}
	@RequestMapping("/save")
	public void save(Link link,
			HttpServletResponse response) throws Exception {
		Integer total = 0;
		if(link.getId()==null) {
			// insert
			total = linkService.add(link);
		}else {
			// update
			total = linkService.update(link);
		}
		
		JSONObject result = new JSONObject();
		if(total>0) {
			result.put("success", true);
		}else {
			result.put("success", false);
			result.put("errorInfo",Status.ERROR_CODE.getName());
		}
		
		ResponseUtil.write(response, result);
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids",required = true) String ids,
			HttpServletResponse response) throws Exception {
		String[] data = ids.split(",");
		Integer total = 0;
		for(int i=0;i<data.length;i++) {
			total = linkService.delete(Integer.valueOf(data[i]));
		}
		
		JSONObject result = new JSONObject();
		if(total>0) {
			result.put("success", true);
		}else {
			result.put("success", false);
			result.put("errorInfo",Status.ERROR_CODE.getName());
		}
		ResponseUtil.write(response, result);
	}
}

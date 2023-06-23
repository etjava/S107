package com.etjava.controller.admin;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.etjava.bean.Status;
import com.etjava.bean.Users;
import com.etjava.service.UsersService;
import com.etjava.util.CryptographyUtil;
import com.etjava.util.DateUtil;
import com.etjava.util.ResponseUtil;

import net.sf.json.JSONObject;

@Controller("adminUserController")
@RequestMapping("/admin/user")
public class AdminUserController {

	@Resource
	private UsersService userService;
	
	@RequestMapping("/find")
	public void find(@RequestParam("id") Integer id, HttpServletResponse response) throws Exception {
		Users users = userService.findById(id);
		JSONObject jsonObject = JSONObject.fromObject(users);
		ResponseUtil.write(response, jsonObject);
	}
	
	
	/**
	 * 更新用户和信息
	 * @param imageFile
	 * @param user
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/update")
	public void update(@RequestParam("imageFileXXXXX") MultipartFile imageFile,
			Users user,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(!imageFile.isEmpty()) {
			// 将图片保存到userImages目录下
			// 项目根路径
			String filePath = request.getServletContext().getRealPath("/");
			// 图片名称拼接
			String imageName = DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
			// 保存到指定的目录
			imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
			user.setImageName(imageName);
		}
		Integer total = userService.update(user);
		
//		ajax方式提交请求时返回数据格式
//		JSONObject result = new JSONObject();
//		if(total>0) {
//			result.put("success", true);
//		}else {
//			result.put("success", false);
//			result.put("errorInfo",Status.ERROR_CODE);
//		}
		
		ResponseUtil.write(response, print(total));
	}
	
	private static String print(Integer total) {
		StringBuffer buf = new StringBuffer();
		if(total>0) {
			buf.append("<script language='javascript' >")
			.append("alert('修改成功');")
			.append("window.parent.$('#tabs').tabs('close', '修改个人信息');")
			.append("</script>");
		}else {
			buf.append("<script language='javascript' >")
			.append("alert('修改失败')")
			.append("</script>");
		}
		return buf.toString();
	}
	
	@RequestMapping("/updatePassword")
	public void updatePassword(Users user,HttpServletResponse response) throws Exception {
		user.setPassword(CryptographyUtil.md5(user.getPassword(),"etjava"));
		Integer total = userService.update(user);
		JSONObject result = new JSONObject();
		if(total>0) {
			result.put("success", true);
		}else {
			result.put("success", false);
			result.put("errorInfo",Status.ERROR_CODE.getName());
		}
		ResponseUtil.write(response, result);
	}
	
	
	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout(); 
		return "redirect:/login.jsp";
	}
}

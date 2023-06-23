package com.etjava.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.etjava.bean.Blog;
import com.etjava.bean.Comment;
import com.etjava.service.BlogService;
import com.etjava.service.CommentService;
import com.etjava.util.ResponseUtil;
import com.etjava.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/comment")
public class CommentController {

	
	@Resource
	private CommentService commentService;
	
	@Resource
	private BlogService blogService;
	
	/**
	 * 	添加或修改评论
	 * @param comment
	 * @param imageCode
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Comment comment,@RequestParam("imageCode") String imageCode,
			HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Integer res = 0;
		if(StringUtil.isNotEmpty(imageCode)) {
			String sRand = (String) session.getAttribute("sRand");
			JSONObject result = new JSONObject();
			if(!imageCode.equals(sRand)) {
				result.put("success", false);
				result.put("errorInfo", "the verification code error.");
			}else {
				String remoteAddr = request.getRemoteAddr();
				if(comment.getId()==null) {
					// insert
					comment.setUserAddr(remoteAddr);
					res = commentService.add(comment);
				}else {
					// update
				}
				
				if(res>0) {
					result.put("success", true);
					Blog blog = blogService.findbyId(comment.getBlog().getId());
					blog.setReplyHit(blog.getReplyHit()+1);
					blogService.update(blog);
				}else {
					result.put("success", false);
					result.put("errorInfo", "评论失败 请联系管理员");
				}
			}
			
			ResponseUtil.write(response, result);
		}
		return null;
	}
	
	@RequestMapping("/list/{id}")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		
		return mav;
	}
}

package com.etjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DownloadController {

	@RequestMapping("/download")
	public ModelAndView download() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pageTitle","download - ETJAVA Blog");
		mav.addObject("mainPage","foreground/blog/download.jsp");
		mav.setViewName("template");
		return mav;
	}
}

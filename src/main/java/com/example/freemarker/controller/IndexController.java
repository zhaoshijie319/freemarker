package com.example.freemarker.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.freemarker.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {

	@GetMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute("user");
		if (ObjectUtil.isNull(user)) {
			mv.setViewName("redirect:/user/info");
		} else {
			mv.setViewName("page/index");
			mv.addObject(user);
		}
		return mv;
	}
}

package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.TreeMenu;
import com.example.demo.common.util.AppLogger;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MenuController {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/menu")
	@ResponseBody
	public ModelAndView menuAction(ModelAndView model, HttpServletRequest request) {
		AppLogger.writeLog(Level.INFO, "---------------------menuAction() start-------------");
		String userID = request.getParameter("userID");
		if(userID == null) {
			userID = "";
		}
		
		List<TreeMenu> treeMenu = userService.selectMenuByUserID(userID);
		
		treeMenu.forEach(menu -> {
			Map<String, String> href = new HashMap<>();
			href.put("href", menu.getAction());
			menu.setA_attr(href);
		});
		model.addObject("treeMenu", treeMenu);

		AppLogger.writeLog(Level.INFO, "---------------------menuAction() end  -------------");
		return model;
	}
	
}
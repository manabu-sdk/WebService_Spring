package com.example.demo.controller;

import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.common.util.AppLogger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@RequestMapping(value = "/main")
	@ResponseBody
	public ModelAndView mainAction(ModelAndView model) {
		AppLogger.writeLog(Level.INFO, "---------------------mainAction() start-------------");

		AppLogger.writeLog(Level.INFO, "---------------------mainAction() end  -------------");
		return model;
	}

	@RequestMapping(value = "/blank")
	@ResponseBody
	public ModelAndView blankAction(ModelAndView model) {
		AppLogger.writeLog(Level.INFO, "---------------------blankAction() start-------------");

		AppLogger.writeLog(Level.INFO, "---------------------blankAction() end  -------------");
		return model;
	}

}
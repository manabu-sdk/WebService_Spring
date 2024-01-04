package com.example.demo.controller;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.User;
import com.example.demo.common.util.AppLogger;
import com.example.demo.form.LoginForm;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public ModelAndView loginAction(@ModelAttribute LoginForm form, ModelAndView model, HttpSession httpSession, HttpServletRequest request) {
		AppLogger.writeLog(Level.INFO, "---------------------loginAction() start-------------");

		String userID = form.getUserID();
		if(userID != null && !"".equals(userID)) {
			String password = form.getPassword();
			User user = userService.selectUserByPK(userID);
			if(user != null && password != null && password.equals(user.getPassword())) {
				httpSession.setAttribute("userID", userID);
				httpSession.setAttribute("userName", user.getUserName());
				model.addObject("userID", userID);
				model.setViewName("main");
				userService.updateLastLoginDateByPK(userID);
			} else {
				model.setViewName("login");
				model.addObject("errorLogin", "ユーザーIDまたはパスワードが正しくありません。");
			}
		}
		
		String timeout = request.getParameter("timeout");
		if(timeout != null && "true".equals(timeout)) {
			model.addObject("errorLogin", "ログアウトされたか、セッションタイムアウトが発生しました、ログインし直してください。");
		}
		
		AppLogger.writeLog(Level.INFO, "---------------------loginAction() end-------------");
		return model;
	}
	
	@RequestMapping(value = "/logout")
	@ResponseBody
	public ModelAndView logoutAction(ModelAndView model, HttpSession httpSession, SessionStatus sessionStatus) {
		AppLogger.writeLog(Level.INFO, "---------------------logoutAction() start-------------");
		
		httpSession.removeAttribute("userID"); //セッションに登録したuserIDを削除する
		httpSession.removeAttribute("userName");//セッションに登録したuserNameを削除する
		httpSession.invalidate(); //セッションの破棄
		sessionStatus.setComplete(); //セッション情報を破棄する
		model.setViewName("login");
		AppLogger.writeLog(Level.INFO, "---------------------logoutAction() end-------------");
		return model;
	}
	
	@RequestMapping(value = "/timeout")
	@ResponseBody
	public ModelAndView timeoutAction(ModelAndView model, HttpSession httpSession, SessionStatus sessionStatus) {
		AppLogger.writeLog(Level.INFO, "---------------------timeoutAction() start-------------");
		
		AppLogger.writeLog(Level.INFO, "---------------------timeoutAction() end-------------");
		return model;
	}
}
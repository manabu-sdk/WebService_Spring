package com.example.demo.controller;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.User;
import com.example.demo.common.util.AppLogger;
import com.example.demo.service.MultiMessageService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	MultiMessageService multiMessageService;
	
	@RequestMapping(value = "/user")
	@ResponseBody
	public ModelAndView userAction(ModelAndView model, HttpServletRequest request) {
		AppLogger.writeLog(Level.INFO, "---------------------userAction() start-------------");
		String message = "";
		String __function = request.getParameter("__function");
		String __userID = request.getParameter("__userID");
		String lang = (String)request.getSession().getAttribute("lang");
		
		if(__userID == null) {
			__userID = "";
		}
		
		if(__function == null || "new".equals(__function)) {
			__function = "new";
			User user = new User();
			model.addObject("objUser", user); 
		} else if("edit".equals(__function)) {
			User user = userService.selectUserByPK(__userID);
			model.addObject("objUser", user); 
		} else if ("insert".equals(__function)) {
			String userID = request.getParameter("userID");
			String userName = request.getParameter("userName");
			String email = request.getParameter("email");
			User user = new User();
			user.setUserID(userID);
			user.setUserName(userName);
			user.setEmail(email);
			user.setRole("");
			user.setPassword(userID);
			user.setCreateUser("ADMIN");
			user.setUpdateUser("ADMIN");
			
			List<User> userList = userService.createAndGetUserList(user);
			
			if(userList != null) {
				model.addObject("listUser", userList);
			} else {
				message = multiMessageService.getMessage("error.user.insert", lang); // ユーザーの登録が失敗しました！
				model.addObject("errorMessage", message);
			}
		} else if ("update".equals(__function)) {
			String userID = request.getParameter("userID");

			String userName = request.getParameter("userName");
			String email = request.getParameter("email");

			User user = userService.selectUserByPK(userID);
			user.setUserName(userName);
			user.setEmail(email);
			user.setUpdateUser("ADMIN");
			
			List<User> userList = userService.updateAndGetUserList(user);
			
			if(userList != null) {
				model.addObject("listUser", userList);
			} else {
				message = multiMessageService.getMessage("error.user.update", lang); // ユーザーの更新が失敗しました！
				model.addObject("errorMessage", message);
			}
		} else if ("deleteP".equals(__function)) {
			List<User> userList = userService.delPAndGetUserList(__userID);

			if(userList != null) {
				model.addObject("listUser", userList);
			} else {
				message = multiMessageService.getMessage("error.user.delete", lang); // ユーザーの物理削除が失敗しました！
				model.addObject("errorMessage", message);
			}
		} else if ("deleteL".equals(__function)) {
			List<User> userList = userService.delLAndGetUserList(__userID);

			if(userList != null) {
				model.addObject("listUser", userList);
			} else {
				message = multiMessageService.getMessage("error.user.delete2", lang); // ユーザーの論理削除が失敗しました！
				model.addObject("errorMessage", message);
			}
		} else if ("select".equals(__function)) {
			String userID = request.getParameter("userID");
			String userName = request.getParameter("userName");
			String email = request.getParameter("email");

			User user = new User();
			user.setUserID(userID);
			user.setUserName(userName + "%");
			user.setEmail(email);
			List<User> userList = userService.selectByCondition(user);

			if(userList != null) {
				model.addObject("listUser", userList);
			} else {
				message = multiMessageService.getMessage("error.user.query", lang); // ユーザーの検索が失敗しました！
				model.addObject("errorMessage", message);
			}
		}

		model.addObject("__userID", __userID); 
		model.addObject("__function", __function);

		AppLogger.writeLog(Level.INFO, "---------------------userAction() end-------------");
		return model;
	}
}
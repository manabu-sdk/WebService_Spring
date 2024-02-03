package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.User;
import com.example.demo.common.util.AppLogger;
import com.example.demo.form.LoginForm;
import com.example.demo.service.MultiMessageService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	UserService userService;

	@Autowired
	MultiMessageService multiMessageService;

	@RequestMapping(value = "/login")
	@ResponseBody
	public ModelAndView loginAction(@ModelAttribute LoginForm form, ModelAndView model, HttpSession httpSession,
			HttpServletRequest request) {
		AppLogger.writeLog(Level.INFO, "---------------------loginAction() start-------------");

		String message = "";
		String lang = form.getLang();

		String userID = form.getUserID();
		if (userID != null && !"".equals(userID)) {
			String password = form.getPassword();
			User user = userService.selectUserByPK(userID);
			if (user != null && password != null && password.equals(user.getPassword())) {
				httpSession.setAttribute("userID", userID);
				httpSession.setAttribute("userName", user.getUserName());
				httpSession.setAttribute("lang", lang);
				model.addObject("userID", userID);
				model.setViewName("main");
				userService.updateLastLoginDateAngLangByPK(userID, lang);
			} else {
				model.setViewName("login");
				message = multiMessageService.getMessage("error.login", lang); // ユーザーIDまたはパスワードが正しくありません。
			}
		}

		String timeout = request.getParameter("timeout");
		if (timeout != null && "true".equals(timeout)) {
			message = multiMessageService.getMessage("error.reLogin", lang); // ログアウトされたか、セッションタイムアウトが発生しました、ログインし直してください。
		}
		model.addObject("errorMessage", message);
		AppLogger.writeLog(Level.INFO, "---------------------loginAction() end-------------");
		return model;
	}

	@RequestMapping(value = "/logout")
	@ResponseBody
	public ModelAndView logoutAction(ModelAndView model, HttpSession httpSession, SessionStatus sessionStatus) {
		AppLogger.writeLog(Level.INFO, "---------------------logoutAction() start-------------");

		httpSession.removeAttribute("userID"); // セッションに登録したuserIDを削除する
		httpSession.removeAttribute("userName");// セッションに登録したuserNameを削除する
		httpSession.invalidate(); // セッションの破棄
		sessionStatus.setComplete(); // セッション情報を破棄する
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
	
	@PostMapping("/ajaxFindLanguage")
	public void findLanguage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AppLogger.writeLog(Level.INFO, "---------------------findLanguage() start-------------");
		String lang = "";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userID = request.getParameter("userID");
        
		if (userID != null && !"".equals(userID)) {
			User user = userService.selectUserByPK(userID);
			if (user != null) {
				lang = user.getLang();
			}
		}
		
        if (lang != null && !lang.isEmpty()) {
            // サーバー側の処理例（ここでは単純にユーザーIDをJSON形式で返す）
            String jsonResponse = "{\"success\": true, \"lang\": \"" + lang + "\"}";
            PrintWriter out = response.getWriter();
            out.print(jsonResponse);
        } else {
            String jsonResponse = "{\"success\": false\"}";
            PrintWriter out = response.getWriter();
            out.print(jsonResponse);
        }
        
		AppLogger.writeLog(Level.INFO, "---------------------findLanguage() end-------------");
	}
	
}
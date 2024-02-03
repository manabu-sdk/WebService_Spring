package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.common.util.AppLogger;
import com.example.demo.service.MultiMessageService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Web アプリケーション全体のエラーコントローラー。 ErrorController インターフェースの実装クラス。
 */
@Controller
@RequestMapping("/error") // エラーページへのマッピング
public class AppErrorController implements ErrorController {

	@Autowired
	MultiMessageService multiMessageService;
	
	/**
	 * HTML レスポンス用の ModelAndView オブジェクトを返す。
	 *
	 * @param req  リクエスト情報
	 * @param view レスポンス情報
	 * @return HTML レスポンス用の ModelAndView オブジェクト
	 */
	@RequestMapping
	public ModelAndView error(HttpServletRequest req, ModelAndView view) {
		AppLogger.writeLog(Level.INFO, "---------------------error() start-------------");

		ResponseEntity<Map<String, Object>> err = getAppErrorEntity(req);

		view.addObject("errBody", err.getBody());
		// view.addObject("errStatus", err.getStatusCode());
		// ビュー名にerror.htmlをセット
		view.setViewName("error");
		AppLogger.writeLog(Level.INFO, "---------------------error() end  -------------");
		return view;

	}

	/**
	 * リクエストのエラーコードからHTTPステータスを取得
	 * 
	 * @param req HTTPリクエスト情報
	 * @return HTTPステータス
	 */
	public static HttpStatus getAppStatusCode(HttpServletRequest req) {
		int statusCode = (Integer) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE); // リクエストからエラーコードを取得する
		for (HttpStatus code : HttpStatus.values()) {
			if (code.value() == statusCode)
				return code;
		}
		return HttpStatus.OK;
	}

	/**
	 * レスポンス用の エラー情報を抽出する。
	 *
	 * @param req HTTPリクエスト情報
	 * @return エラー情報
	 */
	private Map<String, Object> getErrorAttributes(HttpServletRequest req) {
		// DefaultErrorAttributes クラスで詳細なエラー情報を取得する
		ServletWebRequest swr = new ServletWebRequest(req);
		DefaultErrorAttributes dea = new DefaultErrorAttributes();
		ErrorAttributeOptions eao = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.BINDING_ERRORS,
				ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE,
				ErrorAttributeOptions.Include.STACK_TRACE);
		return dea.getErrorAttributes(swr, eao);
	}

	/**
	 * レスポンス用の ResponseEntity オブジェクトを返す。
	 *
	 * @param req HTTPリクエスト情報
	 * @return レスポンス用の ResponseEntity オブジェクト
	 */
	public ResponseEntity<Map<String, Object>> getAppErrorEntity(HttpServletRequest req) {

		// エラー情報を取得
		Map<String, Object> attr = getErrorAttributes(req);

		// HTTP ステータスを決める
		HttpStatus status = getAppStatusCode(req);

		String lang = (String)req.getSession().getAttribute("lang");

		String message = multiMessageService.getMessage("error.system", lang); // "システムエラーが検知されました。至急、システム管理者にご連絡下さい！"

		// 出力したい情報をセットする
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("status", status.value());
		body.put("timestamp", attr.get("timestamp"));
		body.put("error", attr.get("error"));
		body.put("exception", attr.get("exception"));
		body.put("message", message);
		body.put("errors", attr.get("errors"));
		body.put("trace", attr.get("trace"));
		body.put("path", attr.get("path"));

		// 情報を JSON で出力する
		return new ResponseEntity<>(body, status);
	}

}
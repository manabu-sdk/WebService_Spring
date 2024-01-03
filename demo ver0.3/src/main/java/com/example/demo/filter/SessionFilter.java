package com.example.demo.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component // コンポーネントアノテーションをつける
public class SessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (isTarget(request) && isSessionTimeout(httpRequest)) { // セッションタイムアウト
			String requestUri = httpRequest.getRequestURI();
			// ログイン・ログアウト・タイムアウトページのタイムアウトは行わない。
			if (requestUri != null && !requestUri.endsWith("/login") && !requestUri.endsWith("/logout")
					&& !requestUri.endsWith("/timeout")) {
				// 通常画面の場合は、ログイン画面を表示しセッションタイムアウトメッセージを伝える。
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/timeout");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * セッションタイムアウト判定
	 *
	 * @param request
	 * @return
	 */
	private boolean isSessionTimeout(HttpServletRequest request) {
		HttpSession httpSession = request.getSession(false);
		if (httpSession == null) {
			return true;
		}
		if (httpSession.getAttribute("userID") == null) {
			return true;
		}
		String sessionId = request.getRequestedSessionId();
		boolean isValid = request.isRequestedSessionIdValid();
		return httpSession == null || sessionId == null || !isValid || !sessionId.equals(httpSession.getId());
	}

	/**
	 * 対象のリクエストか判定 css, js等のリソースアクセスについては出力対象外 uri.indexOf("/static") < 0 ：
	 * staticフォルダにすべてのリソースが出力対象外 !uri.endsWith(".css") ： staticフォルダ以外の.cssファイルが出力対象外
	 * !uri.endsWith(".js") ： staticフォルダ以外の.jsファイルが出力対象外
	 *
	 * @param request リクエスト
	 * @return true : ロギング対象 / false : ロギング対象ではない
	 */
	private boolean isTarget(ServletRequest request) {
		String uri = ((HttpServletRequest) request).getRequestURI().toLowerCase();
		return (uri.indexOf("/static") < 0 && !uri.endsWith(".css") && !uri.endsWith(".js"));
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}

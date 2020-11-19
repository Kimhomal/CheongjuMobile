/*
 * ------------------------------------------------------------------------------
 * @Project       : 서울시 교통안전시설물 관리시스템
 * @Source        : HttpSessionFilter.java
 * @Description   :
 * @Author        : khb
 * @Version       : v1.0
 * Copyright(c) 2014 MAFRA. All rights reserved
 *
 *------------------------------------------------------------------------------
 *                  변         경         사         항
 *------------------------------------------------------------------------------
 * DATE         AUTHOR      DESCRIPTION
 * ----------   ----------  ----------------------------------------------------
 * 2019. 1. 15.		khb		신규생성
 *------------------------------------------------------------------------------
 */

package tgis.common.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpsRequestWrapper extends HttpServletRequestWrapper {

	private HttpServletResponse response = null;

	public HttpsRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		HttpSession session = super.getSession();
		processSessionCookie(session);
		return session;

	}

	public HttpSession getSession(boolean create) {
		HttpSession session = super.getSession(create);
		processSessionCookie(session);
		return session;

	}

	private void processSessionCookie(HttpSession session) {
		if (session == null || response == null) {
			return;
		}

		Object cookieOverWritten = getAttribute("COOKIE_OVERWRITTEN_FLAG");

		if (cookieOverWritten == null && isSecure() && isRequestedSessionIdFromCookie() && session.isNew()) {
			Cookie cookie = new Cookie("JSESSIONID", session.getId());
			cookie.setMaxAge(-1);

			String contextPath = getContextPath();
			if (contextPath != null && contextPath.length() > 0) {
				cookie.setPath(contextPath);
			} else {
				cookie.setPath("/");
			}

			response.addCookie(cookie);
			setAttribute("COOKIE_OVERWRITTEN_FLAG", "true");
		}

	}

}
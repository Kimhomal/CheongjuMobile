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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpsSessionFilter implements Filter {

	public HttpsSessionFilter() {
		/* Empty */
	}

	public void destroy() {
		/* Empty */
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpsRequestWrapper httpsRequest = new HttpsRequestWrapper((HttpServletRequest) request);
		httpsRequest.setResponse((HttpServletResponse) response);
		chain.doFilter(httpsRequest, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		/* Empty */
	}

}
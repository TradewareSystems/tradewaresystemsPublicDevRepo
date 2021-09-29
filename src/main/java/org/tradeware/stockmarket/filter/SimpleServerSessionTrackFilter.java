package org.tradeware.stockmarket.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.tradeware.stockmarket.bean.TradewareAppInfoBean;
import org.tradeware.stockmarket.util.StockUtil;
//@WebFilter(filterName = "sessionTrackingFilter", urlPatterns = "/*")
//@Component
public class SimpleServerSessionTrackFilter implements Filter {

	/**
	 * TODO : This is a simple authentication session tracing filter to prevent
	 * unauthorized access of application. Application authorization should be
	 * implemented based oauth security standards or any other More research
	 * required for this.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession currentSession = req.getSession(false);
		
		//additionalCheckToProceed()
		if ("/getAccessToken".equals(req.getRequestURI()) || "/getOiTrend".equals(req.getRequestURI())  ){
			chain.doFilter(req, res);
			return;
		}
		
		if (null != currentSession) {
			if (TradewareAppInfoBean.STATUS_APP_UP_AND_RUNNING == (StockUtil.getAppBean().getAppStatus())) {
				if (null != currentSession.getAttribute("user_verify")
						&& (boolean) currentSession.getAttribute("user_verify")) {
					chain.doFilter(req, res);
				} else if ("/appLoginPage".equals(req.getRequestURI()) || "/appLogin".equals(req.getRequestURI())  ){
					chain.doFilter(req, res);
				}
				else {
					res.sendRedirect("/appLoginPage");
					chain.doFilter(req, res);
				}
				
			} else {
				currentSession.setAttribute("app_status", "App is not ready, try after 5 minutes;");
				chain.doFilter(req, res);
			}
		} else {
			currentSession = req.getSession(true);
			if (TradewareAppInfoBean.STATUS_APP_UP_AND_RUNNING == (StockUtil.getAppBean().getAppStatus())) {
				res.sendRedirect("/appLoginPage");
			} else if (TradewareAppInfoBean.STATUS_APP_DOWN == (StockUtil.getAppBean().getAppStatus())) {
				res.sendRedirect("/");
			} else {
				res.sendRedirect("/appLoginPage");
				currentSession.setAttribute("app_status", "App is Coming up, try after 5 minutes;");
			}
		}
		currentSession.setAttribute("app_status_ind", StockUtil.getAppBean().getAppStatus());
	}
}

package com.dongnao.autotest.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dongnao.autotest.web.constants.SessionKey;

/**
 * 上下文拦截器
 * 
 * @author easy
 *
 */
public class ContextInterceptor extends HandlerInterceptorAdapter {
	// private final static Logger logger =
	// LoggerFactory.getLogger(ContextInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<String, Object> userMap = (Map<String, Object>) request.getSession().getAttribute(SessionKey.SS_LOGIN_USER);
		if (CollectionUtils.isEmpty(userMap)) { // 说明没有登录OR登录已失效
			String uri = request.getRequestURI();
			if (uri.equals("/login") || uri.equals("/regist")) {
				return true;
			}
			response.sendRedirect("/login?returnUrl=" + request.getRequestURL().toString());
			return false;
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}

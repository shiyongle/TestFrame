package com.dongnao.autotest.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnao.autotest.common.MapUtil;
import com.dongnao.autotest.service.impl.UserServiceImpl;
import com.dongnao.autotest.web.constants.SessionKey;;

/**
 * 用户控制器
 * 
 * @author easy
 *
 */
@RestController
public class UserController extends BaseController {
	@Autowired
	private UserServiceImpl userSerivce;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 */
	@PostMapping(value = "/login")
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			Map<String, Object> userMap = this.userSerivce.findByName(name);
			if (CollectionUtils.isEmpty(userMap)) 
				return doFailureResult(400, "登录失败");
			String rawPwd = MapUtil.getString(userMap, "password","");
			if (!password.equals(rawPwd))
				return doFailureResult(400, "登录失败");
			request.getSession().setAttribute(SessionKey.SS_LOGIN_USER, userMap);
			return doResult(200, "登录成功");
			
		} catch (Exception e) {
			// TODO: handle exception
			return doFailureResult(400, "登录失败");
		}
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/register")
	@Transactional(rollbackFor = Exception.class)
	public Object register(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> userMap = new HashMap<>();
			userMap.put("name", request.getParameter("name"));
			userMap.put("password", request.getParameter("password"));
			userMap.put("gender", request.getParameter("gender"));
			userMap.put("state", 1);
			int userId = userSerivce.add(userMap);
			return doResult(userId > 0 ? 200 : 400, "注册成功");
		} catch (Exception ex) {
			return doFailureResult(400, "注册失败");
		}
	}
}

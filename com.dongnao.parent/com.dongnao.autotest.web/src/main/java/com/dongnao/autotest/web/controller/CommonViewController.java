package com.dongnao.autotest.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Richered
 *
 */
@Controller
public class CommonViewController {
	/**
	 * 首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/", "/index" })
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/index");
	}

	/**
	 * 登录页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/login" })
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/login");
	}

	/**
	 * 注册页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/regist" })
	public ModelAndView regist(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/regist");
	}

	/**
	 * 用例上传页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = { "/case/case_upload" })
	public ModelAndView caseUpload(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/case/case_upload");
	}

	/**
	 * 分组列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = { "/case/group_list" })
	public ModelAndView groupList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/case/group_list");
	}

	/**
	 * 用例列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = { "/case/case_list" })
	public ModelAndView caseList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/case/case_list");
	}

	/**
	 * 用例执行结果列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = { "/case/case_result_list" })
	public ModelAndView caseResultList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/case/case_result_list");
	}

	/**
	 * 图表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = { "/case/echarts_view" })
	public ModelAndView echartsView(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/case/echarts_view");
	}

	/**
	 * 字典列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = { "/system/dict_list" })
	public ModelAndView DictList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/dict_list");
	}
	
	/**
	 * 邮件服务器列表页
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = { "/system/mail_server_list" })
	public ModelAndView MailServerList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/mail_server_list");
	}
}

package com.dongnao.autotest.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnao.autotest.common.BooleanUtil;
import com.dongnao.autotest.common.IntegerUtil;
import com.dongnao.autotest.service.impl.MailServerServiceImpl;

/**
 * 邮件服务器控制器
 * 
 * @author easy
 *
 */
@RestController
@RequestMapping("/system")
public class MailServerController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailServerController.class);

	@Autowired
	private MailServerServiceImpl mailServerService;

	/**
	 * 保存邮件服务器信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/mailServer")
	public Object saveMailServer(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			Map<String, Object> objMap = new HashMap<>();
			objMap.put("host", request.getParameter("host"));
			objMap.put("port", IntegerUtil.toInteger(request.getParameter("port"), 25));
			objMap.put("userName", request.getParameter("userName"));
			objMap.put("password", request.getParameter("password"));
			objMap.put("from", request.getParameter("from"));
			objMap.put("fromName", request.getParameter("fromName"));
			objMap.put("ssl", BooleanUtil.toBoolean(request.getParameter("ssl"), false));
			objMap.put("default", BooleanUtil.toBoolean(request.getParameter("default"), false));
			objMap.put("remark", request.getParameter("remark"));
			if (id < 1) {
				this.mailServerService.add(objMap);
			} else {
				objMap.put("id", id);
				this.mailServerService.update(objMap);
			}
			return doResult(200, "保存成功");
		} catch (Exception ex) {
			return doFailureResult(400, "保存失败");
		}
	}

	/**
	 * 删除邮件服务器信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping("/mailServer")
	public Object deleteDict(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			this.mailServerService.delete(id);
			return doResult(200, "删除成功");
		} catch (Exception ex) {
			return doFailureResult(400, "删除失败");
		}
	}

	/**
	 * 分页查找邮件服务器信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/mailServers/paged")
	public Object findDictPaged(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> pagerMap = super.getPagerMap(request);
		Map<Long, List<Map<String, Object>>> dataMap = this.mailServerService.findPaged(pagerMap);
		return super.doPagerResult(dataMap);
	}

	/**
	 * 查找邮件服务器信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/mailServers")
	public Object findAll(HttpServletRequest request, HttpServletResponse response) {
		return this.mailServerService.findAll();
	}
}

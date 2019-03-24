package com.dongnao.autotest.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.dongnao.autotest.common.IntegerUtil;
import com.dongnao.autotest.common.Pager;
import com.dongnao.autotest.common.StringUtil;
import com.dongnao.autotest.service.impl.DictServiceImpl;

/**
 * 系统控制器
 * 
 * @author easy
 *
 */
@RestController
@RequestMapping("/system")
public class SystemController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

	@Autowired
	private DictServiceImpl dictService;

	/**
	 * 保存字典信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/dict")
	public Object saveDict(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			Map<String, Object> objMap = new HashMap<>();
			objMap.put("type", request.getParameter("type"));
			objMap.put("name", request.getParameter("name"));
			objMap.put("code", request.getParameter("code"));
			objMap.put("ordinal", request.getParameter("ordinal"));
			objMap.put("remark", request.getParameter("remark"));
			if (id < 1) {
				this.dictService.add(objMap);
			} else {
				objMap.put("id", id);
				this.dictService.update(objMap);
			}
			return doResult(200, "保存成功");
		} catch (Exception ex) {
			return doFailureResult(400, "保存失败");
		}
	}

	/**
	 * 删除字典信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping("/dict")
	public Object deleteDict(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = IntegerUtil.toInteger(request.getParameter("id"), 0);
			this.dictService.delete(id);
			return doResult(200, "删除成功");
		} catch (Exception ex) {
			return doFailureResult(400, "删除失败");
		}
	}

	/**
	 * 分页查找字典信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/dicts/paged")
	public Object findDictPaged(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> pagerMap = super.getPagerMap(request);
		Map<Long, List<Map<String, Object>>> dataMap = this.dictService.findPaged(pagerMap);
		return super.doPagerResult(dataMap);
	}

	/**
	 * 查找字典信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/dicts")
	public Object findGroup(HttpServletRequest request, HttpServletResponse response) {
		return this.dictService.findAll();
	}
}

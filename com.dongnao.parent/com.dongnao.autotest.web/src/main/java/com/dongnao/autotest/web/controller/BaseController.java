package com.dongnao.autotest.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dongnao.autotest.common.IntegerUtil;
import com.dongnao.autotest.common.Pager;
import com.dongnao.autotest.common.StringUtil;

@RestController
public class BaseController {
	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	protected Map<String, Object> getPagerMap(HttpServletRequest request) {
		Map<String, Object> pagerMap = new HashMap<String, Object>();
		pagerMap.put(Pager.PAGEINDEX, IntegerUtil.toInteger(request.getParameter(Pager.PAGEINDEX), 1));
		pagerMap.put(Pager.PAGESIZE, IntegerUtil.toInteger(request.getParameter(Pager.PAGESIZE), 20));
		String sorts = request.getParameter("sorts");
		List<String> sortList = new ArrayList<>();
		for (String sort : sorts.split(",")) {
			if (sort.startsWith("-"))
				sortList.add(String.format("%s %s", StringUtil.trimStart(sort, "-"), "DESC"));
			else
				sortList.add(String.format("%s %s", StringUtil.trimStart(sort, "+"), "ASC"));
		}
		pagerMap.put(Pager.ORDERBY, StringUtil.join(",", sortList));

		for (Entry<String, String[]> item : request.getParameterMap().entrySet()) {
			if (item.getKey().equals(Pager.PAGEINDEX) || item.getKey().equals(Pager.PAGESIZE)
					|| item.getKey().equals("sorts"))
				continue;

			pagerMap.put(item.getKey(), item.getValue()[0]);
		}
		return pagerMap;
	}

	/**
	 * 返回分页结果
	 * 
	 * @param dataMap
	 * @return
	 */
	protected Map<String, Object> doPagerResult(Map<Long, List<Map<String, Object>>> dataMap) {
		Map<String, Object> returnMap = new HashMap<>();
		Iterator<Entry<Long, List<Map<String, Object>>>> iterator = dataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, List<Map<String, Object>>> entry = iterator.next();
			returnMap.put("total", entry.getKey());
			returnMap.put("rows", entry.getValue());
			returnMap.put("pageCount", entry.getValue().size());
		}
		return returnMap;
	}

	/**
	 * 重载+1 返回结果
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	protected Map<String, Object> doResult(int code, String message) {
		return this.doResult(code, message, new HashMap<String, Object>());
	}

	/**
	 * 重载+2 返回结果
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	protected Map<String, Object> doResult(int code, String message, Map<String, Object> data) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("success", HttpStatus.OK.value() == code ? true : false);
		resMap.put("code", code);
		resMap.put("data", data);
		resMap.put("message", message);
		return resMap;
	}

	/**
	 * 重载+3 返回结果
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	protected Map<String, Object> doResult(int code, String message, List<Map<String, Object>> data) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("success", HttpStatus.OK.value() == code ? true : false);
		resMap.put("code", code);
		resMap.put("data", data);
		resMap.put("message", message);
		return resMap;
	}

	/**
	 * 重载+1 失败结果
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	protected Map<String, Object> doFailureResult(int code, String message) {
		return this.doFailureResult(code, message, null);
	}

	/**
	 * 重载+2 失败结果
	 * 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	protected Map<String, Object> doFailureResult(int code, String message, Map<String, Object> data) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("success", false);
		resMap.put("code", code);
		resMap.put("data", data);
		resMap.put("message", message);
		return resMap;
	}
}

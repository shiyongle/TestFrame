package com.dongnao.autotest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dongnao.autotest.common.MapUtil;
import com.dongnao.autotest.common.Pager;
import com.dongnao.autotest.service.mapper.CaseCaseMapper;
import com.dongnao.autotest.service.mapper.CaseStepMapper;

/**
 * 用例服务
 * 
 * @author easy
 *
 */
@Service("CaseCaseService")
public class CaseCaseServiceImpl {
	@Autowired
	private CaseCaseMapper caseCaseMapper;
	@Autowired
	private CaseStepMapper caseStepMapper;

	/**
	 * 新增用例信息
	 * 
	 * @param obj
	 * @return
	 */
	public int add(Map<String, Object> obj) {
		int rowCount = caseCaseMapper.insert(obj);
		return MapUtil.getInteger(obj, "id", 0);
	}

	/**
	 * 修改信息
	 * 
	 * @param obj
	 * @return
	 */
	public int update(Map<String, Object> obj) {
		return caseCaseMapper.update(obj);
	}

	/**
	 * 删除信息
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		// 1.删除用例下所有的步骤
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put("caseId", id);
		List<Map<String, Object>> stepList = this.caseStepMapper.selectList(whereMap);
		for (Map<String, Object> itemMap : stepList) {
			int stepId = MapUtil.getInteger(itemMap, "id", 0);
			this.caseStepMapper.delete(stepId);
		}
				// 2.删除用例
		return caseCaseMapper.delete(id);
	}

	/**
	 * 批量删除信息
	 * 
	 * @param idList
	 * @return
	 */
	public int deleteBatch(List<Integer> idList) {
		return caseCaseMapper.deleteBatch(idList);
	}

	/**
	 * 根据主键id查找信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> findById(int id) {
		return caseCaseMapper.selectById(id);
	}

	/**
	 * 根据条件查找信息
	 * 
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> findList(Map<String, Object> whereMap) {
		return caseCaseMapper.selectList(whereMap);
	}

	/**
	 * 分页查找信息
	 * 
	 * @param pagerMap
	 * @return
	 */
	public Map<Long, List<Map<String, Object>>> findPaged(Map<String, Object> pagerMap) {
		List<Map<String, Object>> list = caseCaseMapper.selectPaged(pagerMap);
		Map<Long, List<Map<String, Object>>> map = new HashMap<>();
		long count = MapUtil.getLong(pagerMap, Pager.COUNT, 0L);
		map.put(count, list);
		return map;
	}

	// ↓↓↓↓↓↓↓↓↓↓ 扩展方法 ↓↓↓↓↓↓↓↓↓↓
	/**
	 * 查找所有用例信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAll() {
		return caseCaseMapper.selectAll();
	}
	// ↑↑↑↑↑↑↑↑↑↑ 扩展方法 ↑↑↑↑↑↑↑↑↑↑
}

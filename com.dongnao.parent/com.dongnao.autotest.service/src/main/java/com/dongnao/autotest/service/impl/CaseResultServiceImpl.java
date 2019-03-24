package com.dongnao.autotest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dongnao.autotest.common.MapUtil;
import com.dongnao.autotest.common.Pager;
import com.dongnao.autotest.service.mapper.CaseResultMapper;

/**
 * 用例结果服务
 * 
 * @author easy
 *
 */
@Service("CaseResultService")
public class CaseResultServiceImpl {
	@Autowired
	private CaseResultMapper caseResultMapper;

	/**
	 * 新增用例结果信息
	 * 
	 * @param obj
	 * @return
	 */
	public int add(Map<String, Object> obj) {
		int rowCount = caseResultMapper.insert(obj);
		return MapUtil.getInteger(obj, "id", 0);
	}

	/**
	 * 修改信息
	 * 
	 * @param obj
	 * @return
	 */
	public int update(Map<String, Object> obj) {
		return caseResultMapper.update(obj);
	}

	/**
	 * 删除信息
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		return caseResultMapper.delete(id);
	}

	/**
	 * 批量删除信息
	 * 
	 * @param idList
	 * @return
	 */
	public int deleteBatch(List<Integer> idList) {
		return caseResultMapper.deleteBatch(idList);
	}

	/**
	 * 根据主键id查找信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> findById(int id) {
		return caseResultMapper.selectById(id);
	}

	/**
	 * 根据条件查找信息
	 * 
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> findList(Map<String, Object> whereMap) {
		return caseResultMapper.selectList(whereMap);
	}

	/**
	 * 分页查找信息
	 * 
	 * @param pagerMap
	 * @return
	 */
	public Map<Long, List<Map<String, Object>>> findPaged(Map<String, Object> pagerMap) {
		List<Map<String, Object>> list = caseResultMapper.selectPaged(pagerMap);
		Map<Long, List<Map<String, Object>>> map = new HashMap<>();
		long count = MapUtil.getLong(pagerMap, Pager.COUNT, 0L);
		map.put(count, list);
		return map;
	}

	// ↓↓↓↓↓↓↓↓↓↓ 扩展方法 ↓↓↓↓↓↓↓↓↓↓
	/**
	 * 查找所有用例结果信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAll() {
		return caseResultMapper.selectAll();
	}
	
	/**
	 * 统计成功率
	 * 
	 * @return
	 */
	public List<Map<String, Object>> countSuccess(Map<String, Object> whereMap) {
		return caseResultMapper.countSuccess(whereMap);
	}
	// ↑↑↑↑↑↑↑↑↑↑ 扩展方法 ↑↑↑↑↑↑↑↑↑↑
}

package com.dongnao.autotest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dongnao.autotest.common.MapUtil;
import com.dongnao.autotest.common.Pager;
import com.dongnao.autotest.service.mapper.CaseCaseMapper;
import com.dongnao.autotest.service.mapper.CaseGroupMapper;

/**
 * 分组服务
 * 
 * @author easy
 *
 */
@Service("CaseGroupService")
public class CaseGroupServiceImpl {
	@Autowired
	private CaseGroupMapper caseGroupMapper;
	@Autowired
	private CaseCaseMapper caseCaseMapper;

	/**
	 * 新增分组信息
	 * 
	 * @param obj
	 * @return
	 */
	public int add(Map<String, Object> obj) {
		int rowCount = caseGroupMapper.insert(obj);
		return MapUtil.getInteger(obj, "id", 0);
	}

	/**
	 * 修改信息
	 * 
	 * @param obj
	 * @return
	 */
	public int update(Map<String, Object> obj) {
		return caseGroupMapper.update(obj);
	}

	/**
	 * 删除信息
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put("groupId", id);
		List<Map<String, Object>> caseList = this.caseCaseMapper.selectList(whereMap);
		if (!CollectionUtils.isEmpty(caseList))
			return -1;	// 该分组下有用例数据，不能删除
		return caseGroupMapper.delete(id);
	}

	/**
	 * 批量删除信息
	 * 
	 * @param idList
	 * @return
	 */
	public int deleteBatch(List<Integer> idList) {
		return caseGroupMapper.deleteBatch(idList);
	}

	/**
	 * 根据主键id查找信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> findById(int id) {
		return caseGroupMapper.selectById(id);
	}

	/**
	 * 根据条件查找信息
	 * 
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> findList(Map<String, Object> whereMap) {
		return caseGroupMapper.selectList(whereMap);
	}

	/**
	 * 分页查找信息
	 * 
	 * @param pagerMap
	 * @return
	 */
	public Map<Long, List<Map<String, Object>>> findPaged(Map<String, Object> pagerMap) {
		List<Map<String, Object>> list = caseGroupMapper.selectPaged(pagerMap);
		Map<Long, List<Map<String, Object>>> map = new HashMap<>();
		long count = MapUtil.getLong(pagerMap, Pager.COUNT, 0L);
		map.put(count, list);
		return map;
	}

	// ↓↓↓↓↓↓↓↓↓↓ 扩展方法 ↓↓↓↓↓↓↓↓↓↓
	/**
	 * 查找所有分组信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAll() {
		return caseGroupMapper.selectAll();
	}
	// ↑↑↑↑↑↑↑↑↑↑ 扩展方法 ↑↑↑↑↑↑↑↑↑↑
}

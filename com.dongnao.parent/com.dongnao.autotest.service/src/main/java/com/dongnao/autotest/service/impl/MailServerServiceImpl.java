package com.dongnao.autotest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dongnao.autotest.common.MapUtil;
import com.dongnao.autotest.common.Pager;
import com.dongnao.autotest.service.mapper.MailServerMapper;

/**
 * 邮件服务器服务
 * 
 * @author easy
 *
 */
@Service("MailServerService")
public class MailServerServiceImpl {
	@Autowired
	private MailServerMapper mailServerMapper;

	/**
	 * 新增信息
	 * 
	 * @param obj
	 * @return
	 */
	public int add(Map<String, Object> obj) {
		// 如果是新增第一条记录，default设为true
		List<Map<String, Object>> list = mailServerMapper.selectAll();
		if (CollectionUtils.isEmpty(list))
			obj.put("default", true);
		
		int rowCount = mailServerMapper.insert(obj);
		return MapUtil.getInteger(obj, "id", 0);
	}

	/**
	 * 修改信息
	 * 
	 * @param obj
	 * @return
	 */
	public int update(Map<String, Object> obj) {
		boolean def = MapUtil.getBoolean(obj, "default", false);
		// 如果修改default为true, 那么把其他记录的default都设为false
		if (def) {
			List<Map<String, Object>> list = mailServerMapper.selectAll();
			for (Map<String, Object> itemMap : list) {
				itemMap.put("default", false);
				mailServerMapper.update(itemMap);
			}
		}
		return mailServerMapper.update(obj);
	}

	/**
	 * 删除信息
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		return mailServerMapper.delete(id);
	}

	/**
	 * 批量删除信息
	 * 
	 * @param idList
	 * @return
	 */
	public int deleteBatch(List<Integer> idList) {
		return mailServerMapper.deleteBatch(idList);
	}

	/**
	 * 根据主键id查找信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> findById(int id) {
		return mailServerMapper.selectById(id);
	}

	/**
	 * 根据条件查找信息
	 * 
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> findList(Map<String, Object> whereMap) {
		return mailServerMapper.selectList(whereMap);
	}

	/**
	 * 分页查找信息
	 * 
	 * @param pagerMap
	 * @return
	 */
	public Map<Long, List<Map<String, Object>>> findPaged(Map<String, Object> pagerMap) {
		List<Map<String, Object>> list = mailServerMapper.selectPaged(pagerMap);
		Map<Long, List<Map<String, Object>>> map = new HashMap<>();
		long count = MapUtil.getLong(pagerMap, Pager.COUNT, 0L);
		map.put(count, list);
		return map;
	}

	// ↓↓↓↓↓↓↓↓↓↓ 扩展方法 ↓↓↓↓↓↓↓↓↓↓
	/**
	 * 查找所有信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAll() {
		return mailServerMapper.selectAll();
	}
	// ↑↑↑↑↑↑↑↑↑↑ 扩展方法 ↑↑↑↑↑↑↑↑↑↑
}

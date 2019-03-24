package com.dongnao.autotest.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dongnao.autotest.service.mapper.UserMapper;

/**
 * 用户服务
 * 
 * @author easy
 *
 */
@Service("UserService")
public class UserServiceImpl {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 新增用户信息
	 * 
	 * @param obj
	 * @return
	 */
	public int add(Map<String, Object> obj) {
		// TODO 校验用户的唯一性
		return userMapper.insert(obj);
	}

	/**
	 * 修改信息
	 * 
	 * @param obj
	 * @return
	 */
	public int update(Map<String, Object> obj) {
		// TODO 校验用户的唯一性
		return userMapper.update(obj);
	}

	/**
	 * 删除信息
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		return userMapper.delete(id);
	}

	/**
	 * 批量删除信息
	 * 
	 * @param idList
	 * @return
	 */
	public int deleteBatch(List<Integer> idList) {
		return userMapper.deleteBatch(idList);
	}

	/**
	 * 根据主键id查找信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> findById(int id) {
		return userMapper.selectById(id);
	}

	/**
	 * 根据条件查找信息
	 * 
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> findList(Map<String, Object> whereMap) {
		return userMapper.selectList(whereMap);
	}

	/**
	 * 分页查找信息
	 * 
	 * @param pagerMap
	 * @return
	 */
	public List<Map<String, Object>> findPaged(Map<String, Object> pagerMap) {
		return userMapper.selectPaged(pagerMap);
	}

	// ↓↓↓↓↓↓↓↓↓↓ 扩展方法 ↓↓↓↓↓↓↓↓↓↓
	/**
	 * 查找所有用户信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAll() {
		return userMapper.selectAll();
	}

	/**
	 * 根据用户名查找用户信息
	 * 
	 * @param name
	 * @return
	 */
	public Map<String, Object> findByName(String name) {
		return userMapper.selectByName(name);
	}
	// ↑↑↑↑↑↑↑↑↑↑ 扩展方法 ↑↑↑↑↑↑↑↑↑↑
}

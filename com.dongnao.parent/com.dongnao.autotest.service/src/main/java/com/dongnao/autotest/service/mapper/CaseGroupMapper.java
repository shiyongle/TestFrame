package com.dongnao.autotest.service.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 分组信息映射
 * 
 * @ClassName: CaseGroupMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author easy
 * @date 2017年3月9日 下午7:01:15
 */
@Mapper
public interface CaseGroupMapper {
	/**
	 * 新增信息
	 * 
	 * @param obj
	 * @return
	 */
	int insert(Map<String, Object> obj);

	/**
	 * 修改信息
	 * 
	 * @param obj
	 * @return
	 */
	int update(Map<String, Object> obj);

	/**
	 * 逻辑删除信息
	 * 
	 * @param id
	 * @return
	 */
	int rubbish(int id);

	/**
	 * 批量逻辑删除信息
	 * 
	 * @param idList
	 * @return
	 */
	int rubbishBatch(List<Integer> idList);

	/**
	 * 删除信息
	 * 
	 * @param id
	 * @return
	 */
	int delete(int id);

	/**
	 * 批量删除信息
	 * 
	 * @param idList
	 * @return
	 */
	int deleteBatch(List<Integer> idList);

	/**
	 * 根据主键id查找信息
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> selectById(int id);

	/**
	 * 根据条件查找信息
	 * 
	 * @param whereMap
	 * @return
	 */
	List<Map<String, Object>> selectList(Map<String, Object> whereMap);

	/**
	 * 分页查找信息
	 * 
	 * @param pagerMap
	 * @return
	 */
	List<Map<String, Object>> selectPaged(Map<String, Object> pagerMap);

	// ↓↓↓↓↓↓↓↓↓↓ 扩展方法 ↓↓↓↓↓↓↓↓↓↓
	/**
	 * 查找所有用户信息
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectAll();
	// ↑↑↑↑↑↑↑↑↑↑ 扩展方法 ↑↑↑↑↑↑↑↑↑↑
}
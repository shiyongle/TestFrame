package com.dongnao.autotest.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页
 * 
 * @author easy
 *
 */
public class Pager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8608294378934325983L;

	/**
	 * 列表达式字段常量
	 */
	public static final String COLUMNPATTEN = "columnPattern";
	/**
	 * 排序字段常量
	 */
	public static final String ORDERBY = "orderBy";
	/**
	 * 条件字段常量
	 */
	public static final String WHERE = "where";
	/**
	 * 页码字段常量
	 */
	public static final String PAGEINDEX = "pageIndex";
	/**
	 * 页大小字段常量
	 */
	public static final String PAGESIZE = "pageSize";
	/**
	 * 是否统计总数字段常量
	 */
	public static final String ISSTATCOUNT = "isStatCount";
	/**
	 * 总数字段常量
	 */
	public static final String COUNT = "count";

	private String columnPattern = "*";

	private String orderBy = "";

	private String where = "";

	private int pageIndex = 1;

	private int pageSize = 20;

	private boolean isStatCount = true;

	private long count = 0;

	/**
	 * 扩展字段
	 */
	private Map<String, Object> whereMap = new HashMap<>();

	/**
	 * 扩展字段
	 */
	private int pageCount = 0;

	/**
	 * 获取列
	 * 
	 * @return
	 */
	public String getColumnPattern() {
		return columnPattern;
	}

	/**
	 * 设置列
	 * 
	 * @param columnPattern
	 */
	public void setColumnPattern(String columnPattern) {
		this.columnPattern = columnPattern;
	}

	/**
	 * 获取排序
	 * 
	 * @return
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序
	 * 
	 * @param orderby
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获取条件
	 * 
	 * @return
	 */
	@Deprecated
	public String getWhere() {
		return where;
	}

	/**
	 * 设置条件
	 * 
	 * @param where
	 */
	@Deprecated
	public void setWhere(String where) {
		this.where = where;
	}

	/**
	 * 获取条件map
	 * 
	 * @return
	 */
	public Map<String, Object> getWhereMap() {
		return this.whereMap;
	}

	/**
	 * 设置条件map
	 * 
	 * @param whereMap
	 */
	public void setWhereMap(Map<String, Object> whereMap) {
		this.whereMap = whereMap;
	}

	/**
	 * 获取页码 （从1开始）
	 * 
	 * @return
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 设置页码（从1开始）
	 * 
	 * @param pageindex
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 获取页大小
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置页大小
	 * 
	 * @param pagesize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize == 0 ? 1 : pageSize;
	}

	/**
	 * 获取是否统计总数
	 * 
	 * @return
	 */
	public boolean getIsStatCount() {
		return isStatCount;
	}

	/**
	 * 设置是否统计总数
	 * 
	 * @param isstatcount
	 */
	public void setIsStatCount(boolean isStatCount) {
		this.isStatCount = isStatCount;
	}

	/**
	 * 获取总数
	 * 
	 * @return
	 */
	public long getCount() {
		return count;
	}

	/**
	 * 设置总数
	 * 
	 * @param count
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * 获取总页数
	 * <p>
	 * 扩展属性
	 * </p>
	 * 
	 * @return
	 */
	public int getPageCount() {
		if (this.pageCount > 0)
			return this.pageCount;

		long pagecount = this.getCount() / this.getPageSize();
		long mod = this.getCount() % this.getPageSize() > 0 ? 1 : 0;
		long total = pagecount + mod;
		return Integer.parseInt(String.valueOf(total));
	}

	/**
	 * 设置总页数
	 * <p>
	 * 扩展属性
	 * </p>
	 * 
	 * @param pageCount
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 获取每页首记录的索引
	 * <p>
	 * 扩展属性<br />
	 * 只读<br />
	 * 从1开始
	 * </p>
	 * 
	 * @return
	 */
	public int getStartIndexPerPage() {
		int startIndex = (this.getPageIndex() - 1) * this.getPageSize();
		return startIndex + 1;
	}

	/**
	 * 获取分页偏移量
	 * <p>
	 * 扩展属性<br />
	 * 只读<br />
	 * 根据页码计算，从0开始
	 * </p>
	 * 
	 * @return
	 */
	public int getOffset() {
		return this.pageIndex * this.pageSize - this.pageSize;
	}

	/**
	 * 获取where值
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) this.whereMap.get(key);
	}

	/**
	 * 获取where值
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T get(String key, Class<T> clazz) {
		return ObjectUtil.toDataType(clazz, this.whereMap.get(key), null);
	}

	/**
	 * 获取where值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public <T> T get(String key, Class<T> clazz, T defaultValue) {
		return ObjectUtil.toDataType(clazz, this.whereMap.get(key), defaultValue);
	}

	/**
	 * 设置whereMap
	 * <p>
	 * 扩展方法
	 * </p>
	 * 
	 * @param key
	 * @param value
	 */
	public void setWhereMap(String key, Object value) {
		this.whereMap.put(key, value);
	}

	/**
	 * 转换where条件
	 * <p>
	 * 扩展方法
	 * </p>
	 * 
	 * @param whereable
	 */
	public String toWhere(Whereable whereable) {
		this.where = whereable.parse(this);
		return this.where;
	}

	/**
	 * where解析接口
	 * 
	 * @author mrluo735
	 *
	 */
	public interface Whereable {
		/**
		 * 解析
		 * 
		 * @param pager
		 * @return
		 */
		public abstract String parse(Pager pager);
	}
}

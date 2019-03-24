package com.dongnao.autotest.common.enumerate;

/**
 * 枚举基类接口
 * 
 * @author easy
 *
 * @param <E>
 * @param <T>
 */
public interface IBaseEnum<E extends Enum<E>, T> {
	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public T getValue();

	/**
	 * 设置值
	 * 
	 * @param value
	 */
	public void setValue(T value);

	/**
	 * 获取描述
	 * 
	 * @return
	 */
	public String getDescription();

	/**
	 * 设置描述
	 * 
	 * @param description
	 */
	public void setDescription(String description);
}
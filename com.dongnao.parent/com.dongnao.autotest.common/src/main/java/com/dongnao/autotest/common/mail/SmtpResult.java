/**
 * @title SmtpResult.java
 * @description TODO
 * @package lm.com.framework.mail
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年2月6日上午11:36:55
 * @version v1.0
 */
package com.dongnao.autotest.common.mail;

import java.io.Serializable;

/**
 * Smtp结果
 * 
 * @author mrluo735
 *
 */
public class SmtpResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3310894772787636089L;

	private boolean success = true;

	private int code = 200;

	private String message = "成功";

	private String data = "";

	/**
	 * 重载+1 构造函数
	 */
	public SmtpResult() {
	}

	/**
	 * 重载+2 构造函数
	 */
	public SmtpResult(boolean success, int code, String message) {
		this.success = success;
		this.code = code;
		this.message = message;
	}

	/**
	 * 获取是否成功
	 * 
	 * @return
	 */
	public boolean getSuccess() {
		return success;
	}

	/**
	 * 设置是否成功
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 获取代码
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 获取消息
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置消息
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}
}

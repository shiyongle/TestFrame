/**
 * @title MailHelper.java
 * @description TODO
 * @package lm.com.framework.mail
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年2月6日下午3:49:18
 * @version v1.0
 */
package com.dongnao.autotest.common.mail;

import java.io.Serializable;

import javax.mail.Authenticator;
import javax.mail.Session;

/**
 * 
 * @author mrluo735
 *
 */
public abstract class MailHelper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1046614930207454608L;

	protected boolean debug = false;
	protected String host = "";
	protected int port;
	protected boolean enableSsl = false;
	protected String userName = "";
	protected String password = "";
	protected int timeout = 180000;
	protected int connectionTimeout = 180000;

	protected boolean connected = false;
	protected Authenticator authenticator;
	protected Session session;

	/**
	 * 获取是否调试
	 * 
	 * @return
	 */
	public boolean getDebug() {
		return debug;
	}

	/**
	 * 设置是否调试
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * 获取邮件服务器主机名
	 * 
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置邮件服务器主机
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 获取端口
	 * <p>
	 * smtp: 25; pop3: 110; imap: 143 <br />
	 * SSL: smtp: 465; pop3: 995; imap: 993 <br />
	 * TLS: smtp: 587
	 * </p>
	 * 
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置端口
	 * <p>
	 * smtp: 25; pop3: 110; imap: 143 <br />
	 * SSL: smtp: 465; pop3: 995; imap: 993 <br />
	 * TLS: smtp: 587
	 * </p>
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 获取是否启用安全连接
	 * 
	 * @return
	 */
	public boolean getEnableSsl() {
		return enableSsl;
	}

	/**
	 * 设置是否启用安全连接
	 * 
	 * @param enableSsl
	 */
	public void setEnableSsl(boolean enableSsl) {
		this.enableSsl = enableSsl;
	}

	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
}

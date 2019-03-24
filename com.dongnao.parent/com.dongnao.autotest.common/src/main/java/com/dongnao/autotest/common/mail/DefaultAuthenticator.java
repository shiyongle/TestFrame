/**
 * @title DefaultAuthenticator.java
 * @description TODO
 * @package lm.com.framework.mail
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年2月5日下午5:43:23
 * @version v1.0
 */
package com.dongnao.autotest.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * @author mrluo735
 *
 */
public class DefaultAuthenticator extends Authenticator {
	private final PasswordAuthentication authentication;

	/**
	 * Default constructor.
	 * 
	 * @param userName
	 *            user name to use when authentication is requested
	 * @param password
	 *            password to use when authentication is requested
	 * @since 1.0
	 */
	public DefaultAuthenticator(String userName, String password) {
		this.authentication = new PasswordAuthentication(userName, password);
	}

	/**
	 * Gets the authentication object that will be used to login to the mail
	 * server.
	 * 
	 * @return A <code>PasswordAuthentication</code> object containing the login
	 *         information.
	 * @since 1.0
	 */
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return this.authentication;
	}
}

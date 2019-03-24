/**
 * @title Pop3Helper.java
 * @description TODO
 * @package lm.com.framework.mail
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年2月6日下午2:56:33
 * @version v1.0
 */
package com.dongnao.autotest.common.mail;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * pop3帮助类
 * 
 * @author mrluo735
 *
 */
public class Pop3Helper extends MailHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3758906347377167896L;

	private Store store;

	/**
	 * 重载+1 构造函数
	 */
	public Pop3Helper() {
		super.setPort(110);
	}

	/**
	 * 重载+2 构造函数
	 * 
	 * @param host
	 *            邮件服务器主机名
	 * @param port
	 *            端口
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 */
	public Pop3Helper(String host, int port, String userName, String password) {
		super.host = host;
		super.port = port;
		super.userName = userName;
		super.password = password;
	}

	/**
	 * 连接
	 * 
	 * @throws MessagingException
	 */
	public void connect() throws MessagingException {
		try {
			this.init();

			this.store = this.getSession().getStore("pop3");
			this.store.connect();

			this.setConnected(true);
		} catch (Exception ex) {
			this.setConnected(false);
		}
	}

	/**
	 * 是否连接
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * 设置是否连接
	 * 
	 * @param connected
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * 获取Session
	 * 
	 * @return
	 */
	public Session getSession() {
		if (this.session == null) {
			this.createSession();
		}
		return this.session;
	}

	/**
	 * 关闭store
	 */
	public void close() {
		if (null != this.store) {
			try {
				this.store.close();
				this.store = null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		setConnected(false);
	}

	/**
	 * 获取邮件
	 * 
	 * @return
	 */
	public List<Email> getMessages() {
		List<Email> list = new ArrayList<>();
		try {
			// 通过POP3协议获得的Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
			Folder folder = this.store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			int messageCount = folder.getMessageCount();
			System.out.println("INBOX:" + messageCount);
			Message[] messages = folder.getMessages();

			Email email;
			for (Message message : messages) {
				email = new Email();
				MimeMessage mimeMessage = (MimeMessage) message;
				// POP3没有判断邮件是否为已读的功能，要使用IMAP才可以
				// Flags flags = mimeMessage.getFlags();
				// if (flags.contains(Flags.Flag.SEEN))
				// System.out.println("这是一封已读邮件");
				// else {
				// System.out.println("未读邮件");
				// }

				email.setMessageId(mimeMessage.getMessageID());
				email.setSubject(mimeMessage.getSubject());
				email.setSendOn(mimeMessage.getSentDate());
				email.setReceiveOn(mimeMessage.getReceivedDate());

				Address[] fromAddresss = mimeMessage.getFrom();
				for (Address item : fromAddresss) {
					InternetAddress address = (InternetAddress) item;
					email.setFrom(address.getAddress());
					email.setFromName(address.getPersonal());
				}
				Address[] toAddresss = mimeMessage.getRecipients(RecipientType.TO);
				if (null != toAddresss && toAddresss.length > 0)
					for (Address item : toAddresss) {
						InternetAddress address = (InternetAddress) item;
						email.getTos().put(address.getAddress(), address.getPersonal());
					}
				Address[] ccAddresss = mimeMessage.getRecipients(RecipientType.CC);
				if (null != ccAddresss && ccAddresss.length > 0)
					for (Address item : ccAddresss) {
						InternetAddress address = (InternetAddress) item;
						email.getCcs().put(address.getAddress(), address.getPersonal());
					}
				Address[] bccAddresss = mimeMessage.getRecipients(RecipientType.BCC);
				if (null != bccAddresss && bccAddresss.length > 0)
					for (Address item : bccAddresss) {
						InternetAddress address = (InternetAddress) item;
						email.getBccs().put(address.getAddress(), address.getPersonal());
					}

				// 解析邮件内容
				Object content = message.getContent();
				if (content instanceof MimeMultipart) {
					MimeMultipart multipart = (MimeMultipart) content;
					parseMultipart(multipart, email);
				}
				// 获取所有的Header，头信息
				/*
				 * Enumeration headers = message.getAllHeaders();
				 * System.out.println(
				 * "----------------------allHeaders-----------------------------"
				 * ); while (headers.hasMoreElements()) { Header header =
				 * (Header)headers.nextElement();
				 * System.out.println(header.getName()+" ======= "+header.
				 * getValue()); }
				 */
			}

			folder.close(true);
			this.close();
		} catch (IllegalStateException ex) {

		} catch (MessagingException ex) {

		} catch (Exception ex) {

		}
		return list;
	}

	/**
	 * 对复杂邮件的解析
	 * 
	 * @param multipart
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void parseMultipart(Multipart multipart, Email email) throws MessagingException, IOException {
		int count = multipart.getCount();
		for (int idx = 0; idx < count; idx++) {
			BodyPart bodyPart = multipart.getBodyPart(idx);
			if (bodyPart.isMimeType("text/plain")) {
				email.setIsBodyHtml(false);
				Object body = bodyPart.getContent();
				email.setBody(null == body ? "" : body.toString());
			} else if (bodyPart.isMimeType("text/html")) {
				email.setIsBodyHtml(true);
				Object body = bodyPart.getContent();
				email.setBody(null == body ? "" : body.toString());
			} else if (bodyPart.isMimeType("multipart/*")) {
				Multipart mpart = (Multipart) bodyPart.getContent();
				parseMultipart(mpart, email);
			} else if (bodyPart.isMimeType("application/octet-stream")) {
				String disposition = bodyPart.getDisposition();
				System.out.println(disposition);
				if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {
					String fileName = bodyPart.getFileName();
					InputStream inputStream = bodyPart.getInputStream();
					email.getAttachments().put(fileName, inputStream);
				}
			}
		}
	}

	/**
	 * 初始化
	 */
	private void init() {
		super.authenticator = new DefaultAuthenticator(super.getUserName(), super.getPassword());
	}

	/**
	 * 创建Session
	 */
	private void createSession() {
		Properties properties = (Properties) System.getProperties().clone();
		properties.setProperty("mail.debug", String.valueOf(super.getDebug()));
		properties.setProperty("mail.store.protocol", "pop3");
		properties.setProperty("mail.pop3.host", super.getHost());
		properties.setProperty("mail.pop3.port", String.valueOf(super.getPort()));
		properties.setProperty("mail.pop3.timeout", String.valueOf(super.getTimeout()));
		properties.setProperty("mail.pop3.connectiontimeout", String.valueOf(super.getConnectionTimeout()));
		properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.pop3.socketFactory.fallback", "false");
		properties.setProperty("mail.pop3.socketFactory.port", String.valueOf(super.getPort()));
		if (super.getEnableSsl()) {
			properties.setProperty("mail.pop3.ssl.enable", "true");
			properties.setProperty("mail.pop3.ssl.checkserveridentity", "true");
			// 要用 SSL/TLS 加密時可能需要加上。這段是 certification 一律通過不檢查(信任範圍設為
			// *)，需要檢查憑證時就依照自己的環境狀況去設定。
			properties.setProperty("mail.pop3.ssl.trust", "*");
			MailSSLSocketFactory mailSSLSocketFactory = null;
			try {
				mailSSLSocketFactory = new MailSSLSocketFactory();
				mailSSLSocketFactory.setTrustAllHosts(true);
				properties.put("mail.pop3.ssl.socketFactory", mailSSLSocketFactory);
			} catch (GeneralSecurityException ex) {
				ex.printStackTrace();
			}
			properties.setProperty("mail.pop3.starttls.enable", "true");
		}

		super.session = Session.getInstance(properties, super.authenticator);
	}
}

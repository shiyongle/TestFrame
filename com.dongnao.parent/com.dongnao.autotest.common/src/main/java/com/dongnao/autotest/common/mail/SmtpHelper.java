/**
 * @title MailHelper.java
 * @description TODO
 * @package lm.com.framework.mail
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年2月5日下午4:35:14
 * @version v1.0
 */
package com.dongnao.autotest.common.mail;

import java.io.File;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;

import com.dongnao.autotest.common.StringUtil;
import com.dongnao.autotest.common.ThreadUtil;

/**
 * smtp帮助类
 * 
 * @author mrluo735
 *
 */
public class SmtpHelper extends MailHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1465243761383729600L;

	private String from = "";
	private String fromName = "";

	private Transport transport;

	/**
	 * 重载+1 构造函数
	 */
	public SmtpHelper() {
		super.setPort(25);
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
	 * @param from
	 *            发件人
	 * @param fromName
	 *            发件人名称
	 */
	public SmtpHelper(String host, int port, String userName, String password, String from, String fromName) {
		super.host = host;
		super.port = port;
		super.userName = userName;
		super.password = password;
		this.from = from;
		this.fromName = fromName;
	}

	/**
	 * 获取发件人
	 * 
	 * @return
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * 设置发件人
	 * 
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 获取发件人名称
	 * 
	 * @return
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * 设置发件人名称
	 * 
	 * @param fromName
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * 连接
	 * 
	 * @throws MessagingException
	 */
	public void connect() throws MessagingException {
		try {
			this.transport = this.getSession().getTransport("smtp");
			this.transport.connect();
			// this.transport.connect(super.userName, super.password);

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
	 * 关闭transport
	 */
	public void close() {
		if (null != this.transport) {
			try {
				this.transport.close();
				this.transport = null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		setConnected(false);
	}

	private void createSession() {
		Properties properties = (Properties) System.getProperties().clone();
		properties.setProperty("mail.debug", String.valueOf(super.debug));
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.host", super.host);
		properties.setProperty("mail.smtp.port", String.valueOf(super.port));
		properties.setProperty("mail.smtp.timeout", String.valueOf(super.timeout));
		properties.setProperty("mail.smtp.connectiontimeout", String.valueOf(super.connectionTimeout));
		// 设置发件人
		// properties.setProperty("mail.smtp.from", "");

		if (!StringUtil.isNullOrWhiteSpace(super.getUserName())
				&& !StringUtil.isNullOrWhiteSpace(super.getPassword())) {
			properties.setProperty("mail.smtp.auth", "true");
			this.authenticator = new DefaultAuthenticator(super.userName, super.password);
		}
		properties.setProperty("mail.smtp.sendpartial", "true");
		properties.setProperty("mail.smtps.sendpartial", "true");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.socketFactory.port", String.valueOf(super.port));

		if (this.enableSsl) {
			properties.setProperty("mail.smtp.ssl.enable", "true");
			properties.setProperty("mail.smtp.ssl.checkserveridentity", "true");
			// 要用 SSL/TLS 加密時可能需要加上。這段是 certification 一律通過不檢查(信任範圍設為
			// *)，需要檢查憑證時就依照自己的環境狀況去設定。
			properties.setProperty("mail.smtp.ssl.trust", "*");
			MailSSLSocketFactory mailSSLSocketFactory = null;
			try {
				mailSSLSocketFactory = new MailSSLSocketFactory();
				mailSSLSocketFactory.setTrustAllHosts(true);
				properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
			} catch (GeneralSecurityException ex) {
				ex.printStackTrace();
			}
			properties.setProperty("mail.smtp.starttls.enable", "true");
		}

		this.session = Session.getInstance(properties, this.authenticator);
		// this.session = Session.getInstance(properties, new Authenticator() {
		// protected PasswordAuthentication getPasswordAuthentication() {
		// return new PasswordAuthentication("", "");
		// }
		// });
	}

	/**
	 * initMailCap
	 * <p>
	 * 处理错误：“no object DCH for MIME type * multipart/mixed”
	 * </p>
	 * 
	 * @return void 返回类型
	 */
	private void initMailCap() {
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;charset=; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;charset=; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;charset=; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;charset=; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;charset=; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
	}

	// region 发送邮件
	/**
	 * 重载+1 发送邮件
	 * 
	 * @param subject
	 *            主题
	 * @param body
	 *            内容
	 * @param to
	 *            接收人
	 * @return
	 */
	public SmtpResult send(String subject, String body, Map<String, String> to) {
		return this.send(subject, body, true, to, null, null, null, 3);
	}

	/**
	 * 重载+2 发送邮件
	 * 
	 * @param subject
	 *            主题
	 * @param body
	 *            内容
	 * @param isBodyHtml
	 *            是否html
	 * @param to
	 *            接收人
	 * @param cc
	 *            抄送人
	 * @param bcc
	 *            密送人
	 * @param attachments
	 *            附件
	 * @param priority
	 *            优先级 1(High):紧急 3:普通(Normal) 5:低(Low)
	 * @return
	 */
	public SmtpResult send(String subject, String body, boolean isBodyHtml, Map<String, String> to,
			Map<String, String> cc, Map<String, String> bcc, String[] attachments, int priority) {
		SmtpResult result = new SmtpResult();
		try {
			Message message = new MimeMessage(this.session);
			// 1(High):紧急 3:普通(Normal) 5:低(Low)
			message.setHeader("X-Priority", String.valueOf(priority));
			// 发件人
			message.setFrom(new InternetAddress(this.from, this.fromName));
			// 收件人
			List<Address> toList = new ArrayList<>();
			for (Entry<String, String> entry : to.entrySet()) {
				toList.add(new InternetAddress(entry.getKey(), entry.getValue()));
			}
			message.setRecipients(RecipientType.TO, toList.toArray(new Address[0]));
			// 抄送人
			if (null != cc && cc.size() > 0) {
				List<Address> ccList = new ArrayList<>();
				for (Entry<String, String> entry : cc.entrySet()) {
					ccList.add(new InternetAddress(entry.getKey(), entry.getValue()));
				}
				message.setRecipients(RecipientType.CC, ccList.toArray(new Address[0]));
			}
			// 密送人
			if (null != bcc && bcc.size() > 0) {
				List<Address> bccList = new ArrayList<>();
				for (Entry<String, String> entry : bcc.entrySet()) {
					bccList.add(new InternetAddress(entry.getKey(), entry.getValue()));
				}
				message.setRecipients(RecipientType.BCC, bccList.toArray(new Address[0]));
			}
			Multipart multipart = new MimeMultipart();
			// 主题和内容
			message.setSubject(subject);
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			if (!isBodyHtml)
				mimeBodyPart.setText(body);
			else
				mimeBodyPart.setContent(body, "text/html; charset=utf-8");
			multipart.addBodyPart(mimeBodyPart);
			// 添加附件
			if (null != attachments && attachments.length > 0) {
				MimeBodyPart attachBodyPart = new MimeBodyPart();
				for (String filePath : attachments) {
					DataSource dataSource = new FileDataSource(filePath);
					attachBodyPart.setDataHandler(new DataHandler(dataSource));
					String fileName = new String(filePath.getBytes("UTF-8"), "UTF-8");
					int pos = fileName.lastIndexOf(File.separator);
					fileName = fileName.substring(pos + 1);
					attachBodyPart.setFileName(fileName);
					multipart.addBodyPart(attachBodyPart);
				}
			}
			message.setContent(multipart);

			this.initMailCap();
			this.transport.sendMessage(message, message.getAllRecipients());
			this.close();
		} catch (SendFailedException ex) {
			result.setSuccess(false);
			result.setCode(-1);
			result.setMessage(ex.getMessage());
		} catch (MessagingException ex) {
			result.setSuccess(false);
			result.setCode(-2);
			result.setMessage(ex.getMessage());
		} catch (Exception ex) {
			result.setSuccess(false);
			result.setCode(-3);
			result.setMessage(ex.getMessage());
		}
		return result;
	}
	// endregion

	// region 异步发送邮件
	/**
	 * 重载+1 异步发送邮件
	 * 
	 * @param subject
	 *            主题
	 * @param body
	 *            内容
	 * @param to
	 *            收件人
	 */
	public void sendAsync(final String subject, final String body, final Map<String, String> to) {
		this.sendAsync(subject, body, true, to, null, null, null, 3);
	}

	/**
	 * 重载+2 异步发送邮件
	 * 
	 * @param subject
	 *            主题
	 * @param body
	 *            内容
	 * @param isBodyHtml
	 *            是否html
	 * @param to
	 *            接收人
	 * @param cc
	 *            抄送人
	 * @param bcc
	 *            密送人
	 * @param attachments
	 *            附件
	 * @param priority
	 *            优先级 1(High):紧急 3:普通(Normal) 5:低(Low)
	 * @return
	 */
	public void sendAsync(final String subject, final String body, final boolean isBodyHtml,
			final Map<String, String> to, final Map<String, String> cc, final Map<String, String> bcc,
			final String[] attachments, final int priority) {
		ThreadUtil.startNew(new Runnable() {
			@Override
			public void run() {
				SmtpResult result = send(subject, body, isBodyHtml, to, cc, bcc, attachments, priority);
				System.out.println(result.getSuccess());
			}
		});
	}
	// endregion
}

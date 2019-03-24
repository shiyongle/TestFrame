/**
 * @title Email.java
 * @description TODO
 * @package lm.com.framework.mail
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年2月6日下午5:03:13
 * @version v1.0
 */
package com.dongnao.autotest.common.mail;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件实体类
 * 
 * @author mrluo735
 *
 */
public class Email implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8741923257585139424L;

	private String messageId;

	private String subject;

	private String body;

	private boolean isBodyHtml;

	private String from;

	private String fromName;

	private Map<String, String> tos = new HashMap<String, String>();

	private Map<String, String> ccs = new HashMap<String, String>();

	private Map<String, String> bccs = new HashMap<String, String>();

	private Map<String, InputStream> attachments = new HashMap<String, InputStream>();

	private Date sendOn;

	private Date receiveOn;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean getIsBodyHtml() {
		return isBodyHtml;
	}

	public void setIsBodyHtml(boolean isBodyHtml) {
		this.isBodyHtml = isBodyHtml;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Map<String, String> getTos() {
		return tos;
	}

	public void setTos(Map<String, String> tos) {
		this.tos = tos;
	}

	public Map<String, String> getCcs() {
		return ccs;
	}

	public void setCcs(Map<String, String> ccs) {
		this.ccs = ccs;
	}

	public Map<String, String> getBccs() {
		return bccs;
	}

	public void setBccs(Map<String, String> bccs) {
		this.bccs = bccs;
	}

	public Map<String, InputStream> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, InputStream> attachments) {
		this.attachments = attachments;
	}

	public Date getSendOn() {
		return sendOn;
	}

	public void setSendOn(Date sendOn) {
		this.sendOn = sendOn;
	}

	public Date getReceiveOn() {
		return receiveOn;
	}

	public void setReceiveOn(Date receiveOn) {
		this.receiveOn = receiveOn;
	}
}

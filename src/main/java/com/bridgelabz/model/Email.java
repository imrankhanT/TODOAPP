package com.bridgelabz.model;

import java.io.Serializable;

public class Email implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String subject, to, setBody, url;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSetBody() {
		return setBody;
	}

	public void setSetBody(String setBody) {
		this.setBody = setBody;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

package com.icommit.model;

public class Message {
	private String content;
	
	public Message() {
	}

	public Message(String content) {
		super();
		this.setContent(content);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

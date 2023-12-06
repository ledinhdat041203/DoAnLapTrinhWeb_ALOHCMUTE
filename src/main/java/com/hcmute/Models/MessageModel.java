package com.hcmute.Models;

import java.io.Serializable;

public class MessageModel implements Serializable{

	private static final long serialVersionUID = 1L;
	public long sender;
	public String content;
	
	
	public long getSender() {
		return sender;
	}
	public void setSender(long sender) {
		this.sender = sender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MessageModel(long sender, String content) {
		this.sender = sender;
		this.content = content;
	}
	public MessageModel() {
		super();
	}
	
	
}

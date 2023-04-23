package com.phuclq.student.kafka;

public class MessageInfoEvent {
	 private long userId;
     private String message;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageInfoEvent(long userId, String message) {
		super();
		this.userId = userId;
		this.message = message;
	}
	public MessageInfoEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

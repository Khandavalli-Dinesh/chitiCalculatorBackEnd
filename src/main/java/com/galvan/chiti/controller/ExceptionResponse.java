package com.galvan.chiti.controller;

import java.util.Date;

public class ExceptionResponse {

	private int status;
	private String message;
    private Date time; 
    
    public ExceptionResponse() {
		super();
	}
	public ExceptionResponse(int status, String message, Date time) {
		super();
		this.status = status;
		this.message = message;
		this.time = time;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
}

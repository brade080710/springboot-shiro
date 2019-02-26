package com.xm.shiro.utils;

public class BaseError extends Exception {

	public BaseError(String msg) {
		super(msg);
	}
	
	public int getCode() {
		String msg = this.getMessage();
		return Integer.parseInt(msg.substring(0,msg.indexOf(":")));
	}
	
	public String toString() {
		return this.getMessage();
	}

	private static final long serialVersionUID = 1L;
	
}
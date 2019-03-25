package com.xm.shiro.entity;

public class ServiceResult {

	private Integer data;
	private int value;
	private String msg;

	public ServiceResult(int value, String msg) {
		super();
		this.value = value;
		this.msg = msg;
	}
	
	

	public ServiceResult(String msg) {
		super();
		this.msg = msg;
	}



	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

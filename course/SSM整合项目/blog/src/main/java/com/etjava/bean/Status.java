package com.etjava.bean;

public enum Status {

	/**
	 * 执行成功 1001
	 */
	SUCCESS_CODE("执行成功",1001),
	/**
	 * 执行失败 1002
	 */
	ERROR_CODE("执行失败",1002);
	
	private String name;
	private Integer code;
	private Status(String name,Integer code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public Integer getCode() {
		return code;
	}
}

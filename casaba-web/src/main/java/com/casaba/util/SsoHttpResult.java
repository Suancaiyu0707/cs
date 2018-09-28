package com.casaba.util;


import com.casaba.common.base.User;

public class SsoHttpResult {
	private int status;
	private String info;
	private User data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public User getData() {
		return data;
	}

	public void setData(User data) {
		this.data = data;
	}
}

package com.casaba.common.base;

import com.alibaba.fastjson.JSONObject;

public class SsoHttpResult {

	private int status;
	private String info;
	private JSONObject data;

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

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
}

package com.casaba.common.enums;

import org.apache.commons.lang.StringUtils;
/**
 * 商户订单类型
 * @author tqlei
 */
public enum MerOrderType {
	/**
	 * 续期
	 */
	TYPE_RENEWAL	("R", "续期"),
	/**
	 * 新建
	 */
	TYPE_NEW	("N", "新建"),
	/**
	 * 开单
	 */
	TYPE_OPEN ("O", "开单"),
	/**
	 * 开单
	 */
	TYPE_REGISTER ("Z", "注册"),
	/**
	 * 升级
	 */
	TYPE_UPGRADE ("U", "升级");
	
	private String code;
    private String desc;
    
    private MerOrderType (String code, String desc) {
    	this.code = code;
    	this.desc = desc;
    }

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
    public static MerOrderType valueOfCode(String code) {
    	for(MerOrderType status : values()){
    		if(StringUtils.equals(code, status.getCode())){
    			return status;
    		}
    	}
    	return null;
    }
}

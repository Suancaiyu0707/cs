package com.casaba.common.enums;

import org.apache.commons.lang.StringUtils;
/**
 * 商户类型
 * @author tqlei
 *  
 */
public enum MerType {
	/**
	 * 免费
	 */
	TYPE_FREE ("F", "免费"),
	/**
	 * 付费
	 */
	TYPE_PAY ("P", "付费");
	
	private String code;
    private String desc;
    
    private MerType (String code, String desc) {
    	this.code = code;
    	this.desc = desc;
    }

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
    public static MerType valueOfCode(String code) {
    	for(MerType status : values()){
    		if(StringUtils.equals(code, status.getCode())){
    			return status;
    		}
    	}
    	return null;
    }
}

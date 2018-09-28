package com.casaba.common.enums;

import org.apache.commons.lang.StringUtils;
/**
 * 商户订单类型
 * @author tqlei
 *  
 */
public enum MerOrderStatus {
	/**
	 * 待开通
	 */
	STATUS_WW	("WW", "待开通"),
	/**
	 * 使用中
	 */
	STATUS_ING ("00", "使用中"),
	/**
	 * 结束
	 */
	STATUS_END ("01", "结束");
	
	private String code;
    private String desc;
    
    private MerOrderStatus (String code, String desc) {
    	this.code = code;
    	this.desc = desc;
    }

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
    public static MerOrderStatus valueOfCode(String code) {
    	for(MerOrderStatus status : values()){
    		if(StringUtils.equals(code, status.getCode())){
    			return status;
    		}
    	}
    	return null;
    }
}

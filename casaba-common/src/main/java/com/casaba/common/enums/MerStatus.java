package com.casaba.common.enums;

import org.apache.commons.lang.StringUtils;
/**
 * 商户类型
 * @author tqlei
 *	
 */
public enum MerStatus {
	/**
	 * 待开通
	 */
	STATUS_WW	("WW", "待开通"),
	/**
	 * 正常
	 */
	STATUS_ING ("00", "正常"),
	/**
	 * 到期
	 */
	STATUS_END ("04", "到期"),
	/**
	 * 冻结
	 */
	STATUS_FROZEN ("01", "冻结");
	
	private String code;
    private String desc;
    
    private MerStatus (String code, String desc) {
    	this.code = code;
    	this.desc = desc;
    }

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static String getDesc(String code) {
		for(MerStatus status : values()){
    		if(StringUtils.equals(code, status.getCode())){
    			return status.getDesc();
    		}
    	}
    	return null;
	}
	
	

    public static MerStatus valueOfCode(String code) {
    	for(MerStatus status : values()){
    		if(StringUtils.equals(code, status.getCode())){
    			return status;
    		}
    	}
    	return null;
    }
}

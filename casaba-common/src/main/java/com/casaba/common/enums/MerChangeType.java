package com.casaba.common.enums;

import org.apache.commons.lang.StringUtils;
/**
 * 商户变更类型
 * @author tqlei
 *   '变更类型,注册Z，新建N，开单O，续期R，升级U'
 */
public enum MerChangeType {
	/**
	 * 注册
	 */
	TYPE_REGISTER	("Z", "注册"),
	/**
	 * 新建
	 */
	TYPE_NEW	("N", "新建"),
	/**
	 * 续期
	 */
	TYPE_RENEWAL	("R", "续期"),
	/**
	 * 开单
	 */
	TYPE_OPEN ("O", "开单"),
	/**
	 * 升级
	 */
	TYPE_UPGRADE ("U", "升级");
	
	private String code;
    private String desc;
    
    private MerChangeType (String code, String desc) {
    	this.code = code;
    	this.desc = desc;
    }

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
    public static MerChangeType valueOfCode(String code) {
    	for(MerChangeType status : values()){
    		if(StringUtils.equals(code, status.getCode())){
    			return status;
    		}
    	}
    	return null;
    }
}

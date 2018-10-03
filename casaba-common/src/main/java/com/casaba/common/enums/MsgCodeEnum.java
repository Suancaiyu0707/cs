package com.casaba.common.enums;

import org.apache.commons.lang.StringUtils;

/***
 * 消息枚举类
 * @author zhifang.xu
 */
public enum MsgCodeEnum {
    success("10100","短信发送成功");
    private String code;
    private String desc;

    private MsgCodeEnum (String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static MsgCodeEnum valueOfCode(String code) {
        for(MsgCodeEnum msgCode : values()){
            if(StringUtils.equals(code, msgCode.getCode())){
                return msgCode;
            }
        }
        return null;
    }
}

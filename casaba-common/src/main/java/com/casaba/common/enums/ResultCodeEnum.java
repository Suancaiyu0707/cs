package com.casaba.common.enums;

/***
 * 返回码
 */
public enum ResultCodeEnum {
    SUCCESS("1","success"),
    LOGIN("2","login"),
    FAIL("0","fail");

    private String code;

    private String msg;

    private ResultCodeEnum(String code , String msg){
        this.code = code ;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

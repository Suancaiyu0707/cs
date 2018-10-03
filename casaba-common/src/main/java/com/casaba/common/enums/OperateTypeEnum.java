package com.casaba.common.enums;

/***
 * 操作用户类型枚举
 * @author zhifang.xu
 */
public enum OperateTypeEnum {
    H("H","代理商"), A("A","总部");
    private String code;

    private String desc;

    private OperateTypeEnum(String code, String desc) {
        this.code=code;
        this.desc=desc;
    }

    public static OperateTypeEnum getType(String code){
        for(OperateTypeEnum type:OperateTypeEnum.values()){
            if(type.code.equals(code)){
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

package com.casaba.common.enums;

/**
 * 分配状态枚举类
 * @author zhifang.xu
 */
public enum AllocStatusEnum {
    /**
     * 已分配
     */
    ALLAC_YES	("00", "已分配"),
    /**
     * 未分配
     */
    ALLOC_NO	("01", "未分配");


    private String code;

    private String desc;

    private AllocStatusEnum(String code, String desc) {
        this.code=code;
        this.desc=desc;
    }

    public static AllocStatusEnum getStatus(String code){
        for(AllocStatusEnum allocEnum:AllocStatusEnum.values()){
            if(allocEnum.code.equals(code)){
                return allocEnum;
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

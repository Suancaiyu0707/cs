package com.casaba.common.enums;

/**
 * 线索状态枚举类
 * @author zhifang.xu
 */
public enum ClueStatusEnum {
    /**
     * 待分配
     */
    CLUE_STATUS_TO_BE_ASSIGNED	("01", "待分配"),
    /**
     * 已分配渠道员
     */
    CLUE_STATUS_HAD_ASSIGNED_CANAL	("02", "已分配渠道员"),
    /**
     * 已成交
     */
    CLUE_STATUS_HAD_DEAL	("03", "已成交"),
    /**
     * 有意向
     */
    CLUE_STATUS_HAVE_INTENTION	("04", "有意向"),
    /**
     * 待分配
     */
    CLUE_STATUS_HAD_ASSIGNED_AGENT	("05", "已分配代理商"),

    /**
     * 已分配销售员
     */
    CLUE_STATUS_HAD_ASSIGNED_SALER	("06", "已分配销售员");


    private String code;

    private String desc;

    private ClueStatusEnum(String code, String desc) {
        this.code=code;
        this.desc=desc;
    }

    public static ClueStatusEnum getClueStatus(String code){
        for(ClueStatusEnum clueStatusEnum:ClueStatusEnum.values()){
            if(clueStatusEnum.code.equals(code)){
                return clueStatusEnum;
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

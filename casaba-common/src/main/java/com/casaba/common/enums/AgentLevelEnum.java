package com.casaba.common.enums;

/***
 * 代理商等级枚举
 * @author zhifang.xu
 */
public enum     AgentLevelEnum {
    EXCELUSIVE("00","独家代理"), CORE("01","核心代理"), NORMAL("02","普通代理");
    private String code;

    private String desc;

    private AgentLevelEnum(String code,String desc) {
        this.code=code;
        this.desc=desc;
    }

    public static AgentLevelEnum getAgentLevel(String code){
        for(AgentLevelEnum levelEnum:AgentLevelEnum.values()){
            if(levelEnum.code.equals(code)){
                return levelEnum;
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

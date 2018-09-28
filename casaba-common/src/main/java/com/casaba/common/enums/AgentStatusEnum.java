package com.casaba.common.enums;

/***
 * 返回码
 */
public enum AgentStatusEnum {
    normal("00","正常"),
    expire("01","过期"),
    freeze("04","冻结");

    private String code;

    private String msg;

    private AgentStatusEnum(String code , String msg){
        this.code = code ;
        this.msg = msg;
    }
    public static AgentStatusEnum getStatus(String code){
        for(AgentStatusEnum statusEnum:AgentStatusEnum.values()){
            if(statusEnum.code.equals(code)){
                return statusEnum;
            }
        }
        return null;
    }
    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

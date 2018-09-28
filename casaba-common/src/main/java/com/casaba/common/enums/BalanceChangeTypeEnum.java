package com.casaba.common.enums;

/***
 * 余额变更类型
 */
public enum BalanceChangeTypeEnum {
    /**
     * 充值
     */
    TYPE_RECHARGE	("R", "充值"),
    /**
     * 扣减
     */
    TYPE_DEDUCTION	("D", "扣减"),
    /**
     * 消耗
     */
    TYPE_CONSUME	("C", "消耗");

    private String code;

    private String msg;

    private BalanceChangeTypeEnum(String code , String msg){
        this.code = code ;
        this.msg = msg;
    }
    public static BalanceChangeTypeEnum getAgentBalanceChangeEnum(String code){
        for(BalanceChangeTypeEnum balanceChangeTypeEnum:BalanceChangeTypeEnum.values()){
            if(balanceChangeTypeEnum.code.equals(code)){
                return balanceChangeTypeEnum;
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
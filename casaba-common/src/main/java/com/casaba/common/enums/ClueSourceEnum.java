package com.casaba.common.enums;

/**
 * 线索来源枚举类
 * @author zhifang.xu
 */
public enum ClueSourceEnum {
    /**
     * 官方注册
     */
    SOURCE_OFFICIAL_REGIST	("01", "官网注册"),
    /**
     * 套餐升级
     */
    SOURCE_PACKAGE_UPGRADE	("02", "套餐升级"),
    /**
     * 后台添加
     */
    SOURCE_BACKSTAGE_ADDITION	("03", "后台添加");

    private String code;

    private String desc;

    private ClueSourceEnum(String code,String desc) {
        this.code=code;
        this.desc=desc;
    }

    public static ClueSourceEnum getSource(String code){
        for(ClueSourceEnum sourceEnum:ClueSourceEnum.values()){
            if(sourceEnum.code.equals(code)){
                return sourceEnum;
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

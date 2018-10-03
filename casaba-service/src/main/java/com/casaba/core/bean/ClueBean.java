package com.casaba.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/***
 * 线索封装bean
 * @author zhifang.xu
 */
@Data
@ToString
public class ClueBean  implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键id
    private Integer id;
    /**
     * 客户名称
     */
    private String customerName;
    /***
     * 商户账号：11位手机号
     */
    private String customerAccount;

    /**
     *客户区域-省
     */
    private String province;
    /**
     *客户区域-市
     */
    private String city;
    /***
     * 客户区域-区
     */
    private String area;
    /**
     * 渠道员id
     */
    private Integer canalId;
    /***
     * 渠道员名称
     */
    private String canalName;
    /***
     * 邮箱
     */
    private String email;
    /**
     * QQ
     */
    private String qq;
    /***
     * 微信号
     */
    private String wechat;

    /**
     * 代理商id
     */
    private Integer agentId;
    /**
     * 代理商名称
     */
    private String agentName;
    /***
     * 联系人
     */
    private String contacts;
    /**
     * 联系人人手机号
     */
    private String contactMobile;
    /***
     * 线索来源 00-官网注册 01-套餐升级 02-后台添加
     */
    private String clueType;
    /**
     * 00-已指派渠道员 01-待分配 02-已指派渠道 03-已成交 04-有意向 05-已分配代理商
     */
    private String status;
    /**
     * 创建人
     */
    private String operator;
    /**8
     * 备注
     */
    private String remark;


}

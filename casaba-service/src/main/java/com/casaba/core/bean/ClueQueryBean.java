package com.casaba.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/***
 * 线索查询bean
 * @author zhifang.xu
 */
@Data
@ToString
public class ClueQueryBean   implements Serializable {

    private static final long serialVersionUID = 1L;
    /***
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区域
     */
    private String area;
    /***
     * 商户账号
     */
    private String customerAccount;
    /**
     * 客户名称
     */
    private String customerName;

    /***
     * 联系人
     */
    private String contacts;


    /***
     * 联系人电话
     */
    private String contactMobile;
    /**
     * 所属渠道员
     */
    private Integer salerId;
    /**
     * 分配的代理商
     */
    private Integer agentId;
}

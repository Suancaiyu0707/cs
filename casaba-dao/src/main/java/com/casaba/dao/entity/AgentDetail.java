package com.casaba.dao.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 代理商权限信息
 * @author zhifang.xu
 */
@Data
@ToString
@Alias("agentDetail")
public class AgentDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    /***
     * 主键ID 无业务信息
     */
    private Integer id;
    /***
     * 账号Id
     */
    private Integer agentId;
    /***
     * 账号有效期开始时间
     */
    private Date startTime;
    /***
     * 账号有效期结束时间
     */
    private Date endTime;
    /***
     * 代理商等级
     */
    private String agentLevel;
    /***
     * 折扣
     */
    private BigDecimal discount;
    /***
     * 保证金
     */
    private BigDecimal cashDeposit;
    /***
     * 预存款
     */
    private BigDecimal balance;

    private String remark;
//    /***
//     * 省
//     */
//    private String province;
//    /**
//     * 市
//     */
//    private String city;
//    /***
//     * 区
//     */
//    private String area;
    /***
     * 省市区
     */
    private String provinceCityArea;


    private Date createTime;

    private Date updateTime;

}
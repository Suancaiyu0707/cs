package com.casaba.agent.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 代理商详细信息
 * @author zhifang.xu
 */
@Data
@ToString
public class AgentDetailBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 代理商账号id
     */
    private Integer agentId;
    /***
     * 账号有效期开始时间
     * yyyyMMddHHmmss
     */
    private Date startTime;
    /***
     * 账号有效期结束时间
     * yyyyMMddHHmmss
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
     * 调整的余额
     */
    private  BigDecimal balanceAdjust;
    /***
     * 预存款
     */
    private BigDecimal balance;
//    /***
//     * 省
//     */
//    private String province;
//    /***
//     * 城市
//     */
//    private String city;
//    /***
//     * 区域
//     */
//    private String area;
    private String provinceCityArea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(String agentLevel) {
        this.agentLevel = agentLevel;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(BigDecimal cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public BigDecimal getBalanceAdjust() {
        return balanceAdjust;
    }

    public void setBalanceAdjust(BigDecimal balanceAdjust) {
        this.balanceAdjust = balanceAdjust;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getProvinceCityArea() {
        return provinceCityArea;
    }

    public void setProvinceCityArea(String provinceCityArea) {
        this.provinceCityArea = provinceCityArea;
    }
}

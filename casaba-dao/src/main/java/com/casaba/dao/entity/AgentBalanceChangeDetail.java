package com.casaba.dao.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 预存款变更信息
 * @author zhifang.xu
 */
@Alias("agentBalanceChangeDetail")
public class AgentBalanceChangeDetail implements Serializable {

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
     * 变更时间
     */
    private Date changeTime;
    /***
     * 交易单号
     */
    private String transNo;
    /***
     * 变更项目
     */
    private String project;
    /***
     * 变动金额（元）
     */
    private BigDecimal transAmount;
    /**
     * 变更后余额（元）
     */
    private BigDecimal amountAfterTrans;
    /***
     * 变更类型 充值R，扣减D，消耗C
     */
    private String changeType;
    /***
     * 操作人
     */
    private String operator;

    private Date createTime;

    private Date updateTime;

    public  AgentBalanceChangeDetail(){

    }
    public AgentBalanceChangeDetail(Integer agentId, Date changeTime, String transNo,
                                    String project, BigDecimal transAmount,
                                    BigDecimal amountAfterTrans, String changeType,
                                    String operator,Date createTime) {
        this.agentId = agentId;
        this.changeTime = changeTime;
        this.transNo = transNo;
        this.project = project;
        this.transAmount = transAmount;
        this.amountAfterTrans = amountAfterTrans;
        this.changeType = changeType;
        this.operator = operator;
        this.createTime = createTime;
    }

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

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public BigDecimal getAmountAfterTrans() {
        return amountAfterTrans;
    }

    public void setAmountAfterTrans(BigDecimal amountAfterTrans) {
        this.amountAfterTrans = amountAfterTrans;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType == null ? null : changeType.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
}
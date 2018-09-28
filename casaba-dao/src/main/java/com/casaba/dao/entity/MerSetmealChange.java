package com.casaba.dao.entity;

import java.util.Date;
/***
 * 	商户套餐变更信息记录Bean
 * @author tqlei
 *
 */
public class MerSetmealChange {
	 private Integer id;
    /**
     * 	商户id
     */
    private Integer merId;
    /**
     * 	商户id
     */
    private Date changeTime;
    /**
     * 	商户id
     */
    private String changeType;
    /**
     * 	商户id
     */
    private String beforeInfo;
    /**
     * 	商户id
     */
    private String afterInfo;
    /**
     * 	套餐开始时间
     */
    private Date beforeTime;
    /**
     * 	套餐结束时间
     */
    private Date afterTime;
    /**
     * 	套餐生效时间
     */
    private Date startTime;
    /**
     * 	操作人
     */
    private String operator;
    /**
     * 	创建时间
     */
    private Date createTime;
    /**
     * 	更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerId() {
        return merId;
    }

    public void setMerId(Integer merId) {
        this.merId = merId;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType == null ? null : changeType.trim();
    }

    public String getBeforeInfo() {
        return beforeInfo;
    }

    public void setBeforeInfo(String beforeInfo) {
        this.beforeInfo = beforeInfo == null ? null : beforeInfo.trim();
    }

    public String getAfterInfo() {
        return afterInfo;
    }

    public void setAfterInfo(String afterInfo) {
        this.afterInfo = afterInfo == null ? null : afterInfo.trim();
    }

    public Date getBeforeTime() {
        return beforeTime;
    }

    public void setBeforeTime(Date beforeTime) {
        this.beforeTime = beforeTime;
    }

    public Date getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(Date afterTime) {
        this.afterTime = afterTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
        this.createTime = createTime ;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
}
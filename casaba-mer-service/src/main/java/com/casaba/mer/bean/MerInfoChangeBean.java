package com.casaba.mer.bean;

import java.io.Serializable;
import java.util.Date;

public class MerInfoChangeBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
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
     * 	变更项目
     */
    private String project;
    /**
     * 	变更前信息
     */
    private String beforeInfo;
    /**
     * 	变更后信息
     */
    private String afterInfo;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
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
        this.updateTime = updateTime;
    }
}
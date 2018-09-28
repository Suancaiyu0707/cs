package com.casaba.dao.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/***
 * 代理商信息变更记录
 * @author zhifang.xu
 */
@Alias("agentChangeDetail")
@ToString
public class AgentChangeDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    /***
     * 代理商账号id
     */
    private Integer agentId;
    /***
     * 变更时间
     */
    private Date changeTime;
    /**
     * 变更项目
     */
    private String project;
    /***
     * 变更前
     */
    private String beforeInfo;
    /***
     * 变更后
     */
    private String afterInfo;
    /***
     * 操作人
     */

    private String operator;

    private Date createTime;

    private Date updateTime;


    public  AgentChangeDetail(){

    }
    public AgentChangeDetail(Integer agentId, Date changeTime, String project,
                             String beforeInfo, String afterInfo, String operator,Date createTime) {
        this.agentId = agentId;
        this.changeTime = changeTime;
        this.project = project;
        this.beforeInfo = beforeInfo;
        this.afterInfo = afterInfo;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBeforeInfo() {
        return beforeInfo;
    }

    public void setBeforeInfo(String beforeInfo) {
        this.beforeInfo = beforeInfo;
    }

    public String getAfterInfo() {
        return afterInfo;
    }

    public void setAfterInfo(String afterInfo) {
        this.afterInfo = afterInfo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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
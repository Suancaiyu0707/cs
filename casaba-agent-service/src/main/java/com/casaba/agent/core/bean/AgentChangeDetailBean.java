package com.casaba.agent.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Author: zhifang.xu
 * Date: 上午11:13 2018/9/17
 * Desc:信息变更详情信息
 **/
@Data
@ToString
public class AgentChangeDetailBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 代理商id
     */
    private Integer agentId;
    /**
     * 变更时间
     * yyyyMMddHHmmss
     */
    private Date changeTime;
    /**
     * 变更项目
     */
    private String project;
    /**
     * 变更前信息
     */
    private String beforeInfo;
    /**
     * 变更后信息
     */
    private String afterInfo;
    /**
     * 操作人员
     */
    private String operator;

}

package com.casaba.dao.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 代理商详情
 * @author zhifang.xu
 */
@Data
@ToString
public class AgentWholeDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 代理商基本信息
     */
    private AgentInfo agent;
    /***
     * 代理商详情
     */
    private AgentDetail agentDetail;
    /***
     * 调整的余额
     */
    private BigDecimal balanceAdjust;
    /***
     * 企业基本信息
     */
    private AgentBusiness agentBusiness;


}

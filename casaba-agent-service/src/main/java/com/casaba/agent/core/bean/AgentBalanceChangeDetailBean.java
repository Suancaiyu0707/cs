package com.casaba.agent.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Author: zhifang.xu
 * Date: 下午4:02 2018/9/17
 * Desc: 预存款信息变更明细
 **/
@Data
@ToString
public class AgentBalanceChangeDetailBean implements Serializable {
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
     * 交易单号
     */
    private String transNo;
    /**
     * 变更项目名称
     */
    private String project;
    /**
     * 变更金额
     */
    private BigDecimal transAmount;
    /**
     * 变更后金额
     */
    private BigDecimal amountAfterTrans;
    /**
     * 变更类型，充值R，扣减D，消耗C
     */
    private String changeType;
    /**
     * 操作人员
     */
    private String operator;


}

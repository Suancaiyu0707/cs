package com.casaba.agent.core.service;

import com.casaba.dao.entity.AgentBalanceChangeDetail;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: zhifang.xu
 * Date: 下午4:27 2018/9/17
 * Desc:
 **/
public interface AgentBalanceChangeDetailService {
    /***
     * 查询代理的余额变更记录列表
     * @param agentId 代理商id
     * @return
     */
    List<AgentBalanceChangeDetail> selectBalanceChangeByAgentId(Integer agentId);

}

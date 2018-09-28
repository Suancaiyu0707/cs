package com.casaba.agent.core.service;

import com.casaba.dao.entity.AgentChangeDetail;

import java.util.List;

/**
 * Author: zhifang.xu
 * Date: 上午11:34 2018/9/17
 * Desc:代理商信息变更详情
 **/
public interface AgentChangeDetailService {
    /***
     * 查询代理商的变更列表
     * @param agentId 代理商id
     * @return
     */
    List<AgentChangeDetail> selectAgentChangeByAgentId(Integer agentId);


}

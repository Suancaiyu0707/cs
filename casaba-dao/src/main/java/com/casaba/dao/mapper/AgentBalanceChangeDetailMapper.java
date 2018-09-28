package com.casaba.dao.mapper;

import com.casaba.dao.entity.AgentBalanceChangeDetail;
import com.casaba.dao.entity.AgentChangeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgentBalanceChangeDetailMapper {

    void insert(AgentBalanceChangeDetail agentBalanceChangeDetail);

    AgentBalanceChangeDetail selectById(@Param("id") Integer id);

    /***
     * 根据账号id查询某个代理商所有的余额变更记录
     * @param agentId 账号id
     * @return
     */
    List<AgentBalanceChangeDetail> selectBalanceChangeByAgentId(@Param("agentId") Integer agentId);

}
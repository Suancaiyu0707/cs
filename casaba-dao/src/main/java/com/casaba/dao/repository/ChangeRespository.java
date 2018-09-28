package com.casaba.dao.repository;

import com.casaba.dao.entity.AgentBalanceChangeDetail;
import com.casaba.dao.entity.AgentChangeDetail;
import com.casaba.dao.mapper.AgentBalanceChangeDetailMapper;
import com.casaba.dao.mapper.AgentChangeDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * 变更记录Respository
 * (包括余额变更记录和代理商信息变更记录)
 * @author zhifang.xu
 */
@Repository
public class ChangeRespository {
    private static final Logger logger = LoggerFactory.getLogger(ChangeRespository.class);
    @Autowired
    private AgentChangeDetailMapper agentChangeDetailMapper;
    @Autowired
    private AgentBalanceChangeDetailMapper agentBalanceChangeDetailMapper;

    /***
     * 新增一条变更记录
     * @param agentChangeDetail
     */
    public void saveAgentChangeDetail(AgentChangeDetail agentChangeDetail){
        agentChangeDetailMapper.insert(agentChangeDetail);
    }

    /***
     * 新增一条余额变更记录
     * @param agentBalanceChangeDetail
     */
    public void saveAgentBalanceChangeDetail(AgentBalanceChangeDetail agentBalanceChangeDetail){
        agentBalanceChangeDetailMapper.insert(agentBalanceChangeDetail);
    }
    /***
     * 根据agentId查询 余额变更记录列表
     * @param agentId 账号id
     * @return
     */
    public List<AgentBalanceChangeDetail> selectBalanceChangeByAgentId(Integer agentId) {
        return agentBalanceChangeDetailMapper.selectBalanceChangeByAgentId(agentId);
    }

    /***
     * 根据agentId查询 代理信息变更记录列表
     * @param agentId 账号id
     * @return
     */
    public List<AgentChangeDetail> selectChangeByAgentId(Integer agentId) {
        return agentChangeDetailMapper.selectAgentChangeByAgentId(agentId);
    }


}

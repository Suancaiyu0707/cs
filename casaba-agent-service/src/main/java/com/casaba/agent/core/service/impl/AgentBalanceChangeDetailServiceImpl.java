package com.casaba.agent.core.service.impl;

import com.casaba.agent.core.service.AgentBalanceChangeDetailService;
import com.casaba.dao.entity.AgentBalanceChangeDetail;
import com.casaba.dao.repository.ChangeRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: zhifang.xu
 * Date: 下午4:27 2018/9/17
 * Desc:
 **/
@Service
public class AgentBalanceChangeDetailServiceImpl implements AgentBalanceChangeDetailService {
    private static final Logger logger = LoggerFactory.getLogger(AgentChangeDetailServiceImpl.class);
    @Autowired
    private ChangeRespository changeRespository;
    @Override
    public List<AgentBalanceChangeDetail> selectBalanceChangeByAgentId(Integer agentId) {
        return changeRespository.selectBalanceChangeByAgentId(agentId);
    }

}

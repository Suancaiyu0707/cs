package com.casaba.agent.core.service.impl;

import com.casaba.agent.core.service.AgentChangeDetailService;
import com.casaba.dao.entity.AgentChangeDetail;
import com.casaba.dao.mapper.AgentChangeDetailMapper;
import com.casaba.dao.repository.ChangeRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: zhifang.xu
 * Date: 上午11:34 2018/9/17
 * Desc:代理商信息变更接口实现
 **/
@Service
public class AgentChangeDetailServiceImpl implements AgentChangeDetailService {
    private static final Logger logger = LoggerFactory.getLogger(AgentChangeDetailServiceImpl.class);
    @Autowired
    private ChangeRespository changeRespository;
    @Autowired
    private AgentChangeDetailMapper agentChangeDetailMapper;

    @Override
    public List<AgentChangeDetail> selectAgentChangeByAgentId(Integer agentId) {
        return changeRespository.selectChangeByAgentId(agentId);
    }


}

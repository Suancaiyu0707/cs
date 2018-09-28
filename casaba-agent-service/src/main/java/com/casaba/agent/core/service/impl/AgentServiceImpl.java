package com.casaba.agent.core.service.impl;

import com.casaba.agent.core.service.AgentService;
import com.casaba.common.enums.AgentStatusEnum;
import com.casaba.agent.core.bean.AgentBean;
import com.casaba.dao.entity.AgentInfo;
import com.casaba.dao.mapper.AgentInfoMapper;
import com.casaba.dao.repository.AgentRespsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {
    private static final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);
    @Autowired
    private AgentRespsitory agentRespsitory;
    @Override
    public void addAgentInfo(AgentInfo agentInfo) {
        agentRespsitory.addAgentInfo(agentInfo);
    }

    @Override
    public AgentBean queryAgentInfo(String mobile, String passwd) {
        AgentInfo info =  agentRespsitory.queryAgentInfo(mobile,passwd);
        if(info == null){
            logger.warn("queryAgentInfo:agent is not exists,mobile={}",mobile);
            return null;
        }
        AgentBean bean = new AgentBean();
        BeanUtils.copyProperties(info,bean);
        return bean;
    }

    @Override
    public void modifyAgentPasswd(String mobile, String passwd,String pwd) {

        agentRespsitory.modifyAgentPasswd(mobile,passwd,pwd);
    }

    @Override
    public void findAgentPasswd(String mobile, String passwd) {
        agentRespsitory.findAgentPasswd(mobile, passwd);
    }

    @Override
    public AgentBean queryAgentByMobile(String mobile) {
        AgentInfo info =  agentRespsitory.queryAgentByMobile(mobile);
        if(info ==null){
            logger.warn("queryAgentByMobile: agent is not exists,mobile={}",mobile);
            return null;
        }
        AgentBean bean = new AgentBean();
        BeanUtils.copyProperties(info,bean);
        return bean;
    }

    @Override
    public void freezeAgent(Integer id) {
        agentRespsitory.updateAgentStatus(id,AgentStatusEnum.freeze.getCode());
    }

    @Override
    public void unfreezeAgent(Integer id) {
        agentRespsitory.updateAgentStatus(id,AgentStatusEnum.normal.getCode());
    }

    @Override
    public void expireAgent(Integer id) {
        agentRespsitory.updateAgentStatus(id,AgentStatusEnum.expire.getCode());
    }


    @Override
    public void updateLastLoginTime(Integer id) {
        agentRespsitory.updateLastLoginTime(id);
    }


    @Override
    public AgentBean loadAgentById(Integer id) {
        AgentInfo agentInfo=agentRespsitory.loadAgentInfo(id);
        if(agentInfo==null) {
        	return null;
        }
        AgentBean bean = new AgentBean();
        BeanUtils.copyProperties(agentInfo,bean);
        return bean;
    }

    @Override
    public void expireAgents() {
        agentRespsitory.expireAgents();
    }

    @Override
    public List<AgentInfo> selectAgents() {
        return agentRespsitory.selectAgents();
    }
}

package com.casaba.agent.core.service;

import com.casaba.agent.core.bean.AgentBean;
import com.casaba.dao.entity.AgentInfo;

import java.util.List;

/***
 * 代理商服务
 * @author  zhifang.xu
 */
public interface AgentService {
    /***
     * 新增代理商用户
     * @param agentInfo
     */
    void addAgentInfo(AgentInfo agentInfo);
    /***
     * 根据手机号和密码查询用户信息
     * @param mobile 手机号
     * @param passwd 密码
     */
    AgentBean queryAgentInfo(String mobile, String passwd);

    /***
     * 修改密码
     * @param mobile 手机号
     * @param passwd 旧密码
     * @param pwd 新密码
     */
    void modifyAgentPasswd(String mobile, String passwd, String pwd);

    /***
     * 修改密码
     * @param mobile 手机号
     * @param passwd 新密码
     */
    void findAgentPasswd(String mobile, String passwd);

    /**
     * 根据手机号查询代理商账号(判断手机号是否已存在)
     * @param mobile 手机号
     * @return
     */
    AgentBean queryAgentByMobile(String mobile);
    /***
     * 根据id冻结代理商
     * @param id 代理商id
     */
    void freezeAgent(Integer id);

    /***
     * 根据id解冻代理商
     * @param id 代理商id
     */
    void unfreezeAgent(Integer id);
    /***
     * 代理商过期
     * @param id 主键id
     */
    void expireAgent(Integer id);

    /***
     * 更新上次登录时间
     * @param id
     */
    void updateLastLoginTime(Integer id);


    /***
     * 根据id查询代理商信息
     * @param id
     */
    AgentBean loadAgentById(Integer id);


    /***
     * 更新过期代理商
     *
     */
    void expireAgents();

    /***
     * 查询有效的代理商列表
     * @return
     */
    List<AgentInfo> selectAgents();
}

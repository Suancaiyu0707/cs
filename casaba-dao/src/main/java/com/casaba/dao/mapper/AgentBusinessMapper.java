package com.casaba.dao.mapper;

import com.casaba.dao.entity.AgentBusiness;

/***
 * 代理商企业信息Mapper
 * @author zhifang.xu
 */
public interface AgentBusinessMapper {
    /***
     * 根据id来删除
     * @param id
     * @return
     */
    void deleteById(Integer id);

    /***
     * 插入一条代理商企业信息
     * @param agentBusiness
     * @return
     */
    void insert(AgentBusiness agentBusiness);

    /***
     * 根据主键id查询代理商企业信息
     * @param id
     * @return
     */
    AgentBusiness selectById(Integer id);
    /***
     * 根据账号id查询代理商企业信息
     * @param agentId 账号ID
     * @return
     */
    AgentBusiness selectByAgentId(Integer agentId);

    /***
     * 更新代理商企业信息
     * @param agentBusiness
     * @return
     */
    void updateById(AgentBusiness agentBusiness);
}
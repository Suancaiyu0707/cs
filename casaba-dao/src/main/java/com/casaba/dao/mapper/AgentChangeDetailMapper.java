package com.casaba.dao.mapper;

import com.casaba.dao.entity.AgentChangeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * 代理商变更记录mapper
 * @author zhifang.xu
 */
public interface AgentChangeDetailMapper {

    /***
     * 批量插入变更记录
     * @param agentChangeDetails
     * @return
     */
    void batchInsert(@Param("agentChangeDetails") List<AgentChangeDetail> agentChangeDetails);

    /***
     * 插入变更记录
     * @param agentChangeDetail
     * @return
     */
    void insert(AgentChangeDetail agentChangeDetail);

    /***
     * 根据id查询代理商变更记录
     * @param id 主键id
     * @return
     */
    AgentChangeDetail selectById(@Param("id") Integer id);

    /***
     * 根据账户id查询代理商变更记录
     * @param agentId 账号id
     * @return
     */
    List<AgentChangeDetail> selectAgentChangeByAgentId(@Param("agentId") Integer agentId);

}
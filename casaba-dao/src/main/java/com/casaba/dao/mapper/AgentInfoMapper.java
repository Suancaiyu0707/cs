package com.casaba.dao.mapper;

import com.casaba.dao.entity.AgentInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

/***
 * 代理商mapper
 * @author zhifang.xu
 */
public interface AgentInfoMapper {

    /***
     * 新增一个代理商信息
     * @param agentInfo 代理商信息
     * @return
     */
    void insert(AgentInfo agentInfo);

    /***
     * 根据代理商id查询代理商信息
     * @param id 主键id
     * @return
     */
    AgentInfo selectById(@Param("id") Integer id);

    /***
     * 根据代理商手机号和密码查询代理商信息
     * @param mobile 手机号
     * @param passwd 密码
     * @return
     */
    AgentInfo queryAgentByMobile(@Param("mobile") String mobile, @Param("passwd") String passwd);
    /***
     * 根据代理商id更新代理商信息
     * @param agentInfo 代理商信息
     * @return
     */
    void updateById(AgentInfo agentInfo);

    /***
     * 修改代理商密码
     * @param mobile 手机号
     * @param passwd 旧密码
     * @param pwd 新密码
     */
    void modifyAgentPasswd(@Param("mobile") String mobile,@Param("passwd") String passwd,@Param("pwd") String pwd);

    /***
     * 找回密码
     * @param mobile 手机号
     * @param passwd 密码
     */
    void findAgentPasswd(@Param("mobile") String mobile,@Param("pwd")  String passwd);

    /***
     * 根据手机号查询代理商账号
     * @param mobile 手机号
     * @return
     */
    AgentInfo queryAgentByMobile(@Param("mobile")String mobile);

    /***
     * 更新代理商状态
     * @param id 主键id
     * @param status 更新状态 00-正常 01-过期 04-冻结
     */
    void updateAgentStatus(@Param("id") Integer id,@Param("status") String status);

    /***
     * 更新上次登录时间
     * @param id 主键id
     * @param loginTime 登录时间
     */
    void updateLastLoginTime(@Param("id") Integer id,@Param("loginTime") Date loginTime);


    /***
     * 更新过期代理商
     * @param currentDate
     */
    void expireAgent(@Param("currentDate") Date currentDate);

    List<AgentInfo> selectAgents();


}
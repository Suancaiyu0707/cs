package com.casaba.dao.mapper;

import com.casaba.dao.entity.AgentDetail;
import com.casaba.dao.entity.AgentWholeDetail;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/***
 * 代理商权限信息变更
 * @author  zhifang.xu
 */
public interface AgentDetailMapper {
    /***
     * 根据id 删除代理商权限信息
     * @param id 主键id
     * @return
     */
    void deleteById(Integer id);

    /***
     * 新增一条代理商权限信息
     * @param agentDetail
     * @return
     */
    void insert(AgentDetail agentDetail);

    /***
     * 根据id查询代理商权限信息
     * @param id
     * @return
     */
    AgentDetail selectById(Integer id);

    /***
     * 根据账号id查询代理商权限信息
     * @param agentId 账号ID
     * @return
     */
    AgentDetail selectByAgentId(Integer agentId);
    /***
     * 根据id更新代理商权限信息
     * @param agentDetail
     * @return
     */

    void updateById(AgentDetail agentDetail);

    /***
     * 批量更新 代理商等级
     * @param ids 批量id
     * @param level 等级
     * @return
     */
    void batchUpdateLevel(@Param("ids") List<Integer> ids, String level);

    /***
     * 批量修改折扣
     * @param ids 批量id
     * @param discount 折扣
     * @return
     */
    void batchUpdateDiscount(@Param("ids") List<Integer> ids, BigDecimal discount);

    /***
     * 根据查询条件查询代理商列表
     * @param discount_dt 折扣信息下限
     * @param discount_ft 折扣信息上限
     * @param status 状态
     *                  正常00,到期04,冻结01
     * @param provinceCityArea 省市区
     * @param agentLevel 级别
     *                     00-独家代理 01-核心代理 02-普通代理
     * @param remark 账号手机或代理商名称
     * @return
     */
    Page<Map> queryAgentDetails(@Param("discount_dt") String discount_dt, @Param("discount_ft") String discount_ft, @Param("status")String status,
                                      @Param("provinceCityArea")String provinceCityArea,
                                      @Param("agentLevel")String agentLevel, @Param("remark")String remark);

    /***
     *  充值
     * @param agentId 代理商Id
     * @param amount 充值金额
     */
    void adjustBalance(@Param("agentId") Integer agentId,@Param("amount") double amount);

    /***
     *  扣减
     * @param agentId 代理商Id
     * @param amount 扣减金额
     */
    void deductionBalance(@Param("agentId") Integer agentId,@Param("amount") BigDecimal amount);

    /**
     * 查询余额
     * @param agentId
     * @return
     */
    AgentDetail queryBalance(@Param("agentId") Integer agentId);

}
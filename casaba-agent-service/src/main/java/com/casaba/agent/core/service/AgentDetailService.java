package com.casaba.agent.core.service;


import com.casaba.agent.core.bean.AgentDetailBean;
import com.casaba.common.paging.PageInfo;
import com.casaba.agent.core.bean.AgentDetailBean;
import com.casaba.agent.core.bean.AgentWholeDetailBean;

import java.math.BigDecimal;
import java.util.List;

/***
 * 代理商详情
 * @author zhifang.xu
 */
public interface AgentDetailService {
    /***
     * 新增代理商详情
     * @param agentWholeDetail
     *      (包含代理商账号、权限信息、公司信息、联系方式)
     */
    public void addAgentDetail(AgentWholeDetailBean agentWholeDetail, String fouder);

    /***
     * 修改代理商详情
     * @param agentWholeDetail (包含代理商账号、权限信息、公司信息、联系方式)
     *
     */
    public void modifyAgentDetail(AgentWholeDetailBean agentWholeDetail, String operator);

    /***
     * 根据账号id查询代理详情
     * @param agentId 账号id
     */
    public AgentWholeDetailBean queryAgentDetail(Integer agentId);

    /***
     * 批量更新 代理商等级
     * @param ids 批量id
     * @param level 等级
     * @return
     */
    void batchUpdateLevel(List <Integer> ids, String level);

    /***
     * 批量修改折扣
     * @param ids 批量id
     * @param discount 折扣
     * @return
     */
    void batchUpdateDiscount(List <Integer> ids, BigDecimal discount);

    /***
     *  充值
     * @param agentId 代理商Id
     * @param amount 充值金额
     * @param  operator 操作人
     */
    void adjustBalance(Integer agentId, double amount, String operator);

    /***
     *  扣减
     * @param agentId 代理商Id
     * @param amount 金额
     * @param transNo 交易号
     * @param  project 项目
     * @param operator 操作人
     */
    void deductionBalance(Integer agentId, BigDecimal amount, String transNo, String project, String operator);

    /***
     * 根据查询条件分页查询代理商列表
     * @param discount_dt 折扣信息下限
     * @param discount_ft 折扣信息上限
     * @param status 状态
     *                  正常00,到期04,冻结01
     * @param provinceCityArea 省市区
     * @param agentLevel 级别
     *                     00-独家代理 01-核心代理 02-普通代理
     * @param remark 账号手机或代理商名称
     * @param page 页码
     * @param pageSize 每页大小
     * @return
     */
    PageInfo<AgentWholeDetailBean> queryAgentDetails(String discount_dt, String discount_ft, String status,
                                                     String provinceCityArea,
                                                     String agentLevel, String remark,
                                                     Integer page, Integer pageSize);
    /**
     * 查询代理商余额相关信息
     * @param agentId 账号ID
     * @return
     */
    AgentDetailBean queryBalance(Integer agentId);

}

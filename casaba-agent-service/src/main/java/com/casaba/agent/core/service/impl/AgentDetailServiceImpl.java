package com.casaba.agent.core.service.impl;

import com.casaba.agent.core.service.AgentDetailService;
import com.casaba.common.paging.PageInfo;
import com.casaba.agent.core.bean.AgentBean;
import com.casaba.agent.core.bean.AgentBusinessBean;
import com.casaba.agent.core.bean.AgentDetailBean;
import com.casaba.agent.core.bean.AgentWholeDetailBean;
import com.casaba.dao.entity.AgentBusiness;
import com.casaba.dao.entity.AgentDetail;
import com.casaba.dao.entity.AgentInfo;
import com.casaba.dao.entity.AgentWholeDetail;
import com.casaba.dao.repository.AgentRespsitory;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * 代理商详情编辑接口实现
 * @author  zhifang.xu
 */
@Service
public class AgentDetailServiceImpl implements AgentDetailService {
    @Autowired
    private AgentRespsitory agentRespsitory;
    @Override
    public void addAgentDetail(AgentWholeDetailBean agentWholeDetailBean, String fouder) {
        AgentInfo agent = new AgentInfo();
        AgentDetail agentDetail = new AgentDetail();
        AgentBusiness agentBusiness = new AgentBusiness();
        AgentWholeDetail agentWholeDetail = new AgentWholeDetail();
        BeanUtils.copyProperties(agentWholeDetailBean.getAgent(),agent);
        BeanUtils.copyProperties(agentWholeDetailBean.getAgentDetail(),agentDetail);
        BeanUtils.copyProperties(agentWholeDetailBean.getAgentBusiness(),agentBusiness);
        agentWholeDetail.setAgent(agent);
        agentWholeDetail.setAgentDetail(agentDetail);
        agentWholeDetail.setAgentBusiness(agentBusiness);
        agentRespsitory.addAgent(agentWholeDetail,fouder);
    }

    @Override
    public void modifyAgentDetail(AgentWholeDetailBean agentWholeDetailBean,String operator) {
        AgentInfo agent = new AgentInfo();
        AgentDetail agentDetail = new AgentDetail();
        AgentBusiness agentBusiness = new AgentBusiness();
        AgentWholeDetail agentWholeDetail = new AgentWholeDetail();
        BeanUtils.copyProperties(agentWholeDetailBean.getAgent(),agent);
        BeanUtils.copyProperties(agentWholeDetailBean.getAgentDetail(),agentDetail);
        BeanUtils.copyProperties(agentWholeDetailBean.getAgentBusiness(),agentBusiness);
        agentWholeDetail.setAgent(agent);
        agentDetail.setBalance(agentWholeDetailBean.getAgentDetail().getBalance().add(agentWholeDetailBean.getAgentDetail().getBalanceAdjust()));
        agentWholeDetail.setAgentDetail(agentDetail);
        agentWholeDetail.setBalanceAdjust(agentWholeDetailBean.getAgentDetail().getBalanceAdjust());
        agentWholeDetail.setAgentBusiness(agentBusiness);

        agentRespsitory.modifyAgent(agentWholeDetail,operator);
    }

    @Override
    public AgentWholeDetailBean queryAgentDetail(Integer agentId) {

        AgentWholeDetail agentWholeDetail = agentRespsitory.queryAgentDetail(agentId);
        if(agentWholeDetail==null){
            return null;
        }
        AgentWholeDetailBean agentWholeDetailBean = new AgentWholeDetailBean();
        AgentBean agent = new AgentBean();
        agentWholeDetail.getAgent().setPwd("");//不要把密码返回过去
        BeanUtils.copyProperties(agentWholeDetail.getAgent(),agent);
        agentWholeDetailBean.setAgent(agent);

        AgentDetailBean agentDetail = new AgentDetailBean();
        BeanUtils.copyProperties(agentWholeDetail.getAgentDetail(),agentDetail);
        agentWholeDetailBean.setAgentDetail(agentDetail);

        AgentBusinessBean agentBusiness = new AgentBusinessBean();
        BeanUtils.copyProperties(agentWholeDetail.getAgentBusiness(),agentBusiness);
        agentWholeDetailBean.setAgentBusiness(agentBusiness);
        return agentWholeDetailBean;
    }

    @Override
    public void batchUpdateLevel(List<Integer> ids, String level) {
        agentRespsitory.batchUpdateLevel(ids,level);
    }

    @Override
    public void batchUpdateDiscount(List <Integer> ids, BigDecimal discount) {
        agentRespsitory.batchUpdateDiscount(ids,discount);
    }

    @Override
    public void adjustBalance(Integer agentId, double amount,String operator) {
        agentRespsitory.adjustBalance(agentId,amount,operator);
    }

    @Override
    public  void deductionBalance(Integer agentId, BigDecimal amount,String transNo,String project,String operator) {
        agentRespsitory.deductionBalance(agentId,amount,transNo,project,operator);
    }

    @Override
    public PageInfo<AgentWholeDetailBean> queryAgentDetails(String discount_dt, String discount_ft, String status,
                                                            String provinceCityArea, String agentLevel,
                                                            String remark,
                                                            Integer page, Integer pageSize) {
        PageInfo<AgentWholeDetailBean> resultPage= new PageInfo<AgentWholeDetailBean>();
        Page<Map> listPages = agentRespsitory.queryAgentDetails( discount_dt,   discount_ft,  status,
                provinceCityArea,
                agentLevel,  remark,page,pageSize);
        if(listPages == null){
            resultPage.setList(Lists.newArrayList());
            resultPage.setTotal(0);
            resultPage.setPageNum(0);
            resultPage.setPageSize(20);
            return resultPage;
        }
        List<Map> agentWholeDetailList= listPages.getResult();
        if(CollectionUtils.isEmpty(agentWholeDetailList)){
            resultPage.setList(Lists.newArrayList());
            resultPage.setTotal(listPages.getTotal());//总记录书
            resultPage.setPages(listPages.getPages());//总页数
            resultPage.setPageNum(listPages.getPageNum());//页码
            resultPage.setPageSize(listPages.getPageSize());//每页大小
            return resultPage;
        }
        List<AgentWholeDetailBean> result = Lists.newArrayList();
        for(Map map:agentWholeDetailList){
            AgentWholeDetailBean bean = new AgentWholeDetailBean();
            AgentBean agent = new AgentBean();
            AgentDetailBean agentDetail=new AgentDetailBean();
            AgentBusinessBean agentBusiness = new AgentBusinessBean();
            bean.setAgent(agent);
            bean.setAgentDetail(agentDetail);
            bean.setAgentBusiness(agentBusiness);
            agent.setId((Integer) map.get("id"));
            agent.setMobile((String)map.get("mobile"));
            agentDetail.setAgentId((Integer) map.get("id"));
            agentBusiness.setAgentId((Integer) map.get("id"));
            agent.setAgentName((String)map.get("agent_name"));
            agentDetail.setAgentLevel((String)map.get("agent_level"));
            agentDetail.setDiscount((BigDecimal) map.get("discount"));
            agentDetail.setCashDeposit((BigDecimal) map.get("cash_deposit"));
            agentDetail.setBalance((BigDecimal) map.get("balance"));
            agentDetail.setProvinceCityArea((String)map.get("province_city_area"));
            agent.setCreateTime((Date)map.get("create_time"));
            agentDetail.setEndTime((Date)map.get("end_time"));
            agent.setStatus((String)map.get("status"));
            result.add(bean);
        }
        resultPage.setList(result);
        resultPage.setTotal(listPages.getTotal());//总记录书
        resultPage.setPages(listPages.getPages());//总页数
        resultPage.setPageNum(listPages.getPageNum());//页码
        resultPage.setPageSize(listPages.getPageSize());//每页大小
        return resultPage;
    }


    /**
     * 查询代理商余额
     * @param agentId 账号ID
     * @return
     */
    @Override
    public AgentDetailBean queryBalance( Integer agentId){
    	AgentDetail datail=agentRespsitory.queryBalance(agentId);
    	if(datail==null) {
    		return null;
    	}
    	AgentDetailBean bean=new AgentDetailBean();
        BeanUtils.copyProperties(datail,bean);
        bean.setDiscount(bean.getDiscount().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
        return  bean;
    }


}

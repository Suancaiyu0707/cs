package com.casaba.agent.core.bean;

import com.casaba.common.enums.AgentLevelEnum;
import com.google.gson.Gson;
import lombok.Data;
import lombok.ToString;
import sun.applet.Main;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 代理商详情
 * @author zhifang.xu
 */
//@Data
@ToString
public class AgentWholeDetailBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 代理商基本信息
     */
    private AgentBean agent;
    /***
     * 代理商详情
     */
    private AgentDetailBean agentDetail;
    /***
     * 企业基本信息
     */
    private AgentBusinessBean agentBusiness;

    public AgentBean getAgent() {
        return agent;
    }

    public void setAgent(AgentBean agent) {
        this.agent = agent;
    }

    public AgentDetailBean getAgentDetail() {
        return agentDetail;
    }

    public void setAgentDetail(AgentDetailBean agentDetail) {
        this.agentDetail = agentDetail;
    }

    public AgentBusinessBean getAgentBusiness() {
        return agentBusiness;
    }

    public void setAgentBusiness(AgentBusinessBean agentBusiness) {
        this.agentBusiness = agentBusiness;
    }


    public static void main (String args[]) {
        AgentWholeDetailBean a = new AgentWholeDetailBean();
        AgentBean agent = new AgentBean();
        agent.setMobile("13661628684");
        agent.setFounder("xuzf");
        agent.setPwd("adabdab12121");
        AgentDetailBean detailBean = new AgentDetailBean();
        detailBean.setStartTime(new Date());
        detailBean.setEndTime(new Date());
        detailBean.setAgentLevel(AgentLevelEnum.EXCELUSIVE.getCode());

        detailBean.setProvinceCityArea("福建省-莆田是-秀屿区");
        detailBean.setBalance(new BigDecimal("1000.13"));
        detailBean.setCashDeposit(new BigDecimal("99.13"));
        detailBean.setDiscount(new BigDecimal(30.55));

        detailBean.setAgentId(1);
        AgentBusinessBean businessBean = new AgentBusinessBean();
        businessBean.setAddress("福建省莆田市");

        businessBean.setCompanyName("哈罗出行");
        businessBean.setCompanyPhone("02166812345");
        businessBean.setContactMobile("13661628684");
        businessBean.setEmail("491978944@qq.com");
        businessBean.setQq("491978944");
        businessBean.setContacts("xuzf");
        a.setAgent(agent);
        a.setAgentDetail(detailBean);
        a.setAgentBusiness(businessBean);
        System.out.println(new Gson().toJson(a));
    }
}

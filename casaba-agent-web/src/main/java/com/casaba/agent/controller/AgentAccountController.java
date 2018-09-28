package com.casaba.agent.controller;

import com.casaba.agent.aspect.SsoFilter;
import com.casaba.common.constants.SystemConstant;
import com.casaba.common.enums.AgentStatusEnum;
import com.casaba.common.enums.BalanceChangeTypeEnum;
import com.casaba.common.result.ResultObject;
import com.casaba.common.util.DesUtil;
import com.casaba.common.util.MD5Util;
import com.casaba.common.util.MsgUtil;
import com.casaba.common.util.RegExpValidatorUtils;
import com.casaba.agent.core.bean.AgentBean;
import com.casaba.agent.core.bean.AgentWholeDetailBean;
import com.casaba.agent.core.service.AgentBalanceChangeDetailService;
import com.casaba.agent.core.service.AgentChangeDetailService;
import com.casaba.agent.core.service.AgentDetailService;
import com.casaba.agent.core.service.AgentService;
import com.casaba.dao.entity.AgentBalanceChangeDetail;
import com.casaba.dao.entity.AgentChangeDetail;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/***
 * 代理商账号管理
 */
@SsoFilter
@RestController
@RequestMapping("agent/account")
public class AgentAccountController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AgentAccountController.class);
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentDetailService agentDetailService;
    @Autowired
    private AgentChangeDetailService agentChangeDetailService;
    @Autowired
    private AgentBalanceChangeDetailService agentBalanceChangeDetailService;
    @SsoFilter(isDoFilter = false)
    @RequestMapping("/modifyAgentPasswd")
    public Object modifyAgentPasswd(HttpServletRequest request, HttpServletResponse response){
        String mobile = request.getParameter("mobile");
        String old_passwd = request.getParameter("old_passwd");
        String passwd = request.getParameter("passwd");
        String passwd_sure = request.getParameter("passwd_sure");
        if(StringUtils.isEmpty(mobile)) {
            return ResultObject.setNotOK("请输入手机号");
        }
        if(!RegExpValidatorUtils.isPhone(mobile)){
            logger.error("mobile is error");
            return ResultObject.setNotOK("手机号格式有误");
        }
        if(agentService.queryAgentByMobile(mobile)==null){
            logger.error("mobile is exists");
            return ResultObject.setNotOK("手机号尚未注册");
        }
        if(StringUtils.isEmpty(old_passwd)){
            return ResultObject.setNotOK("请输入原密码");
        }
        if(StringUtils.isEmpty(passwd)){
            return ResultObject.setNotOK("请输入新密码");
        }
        if(StringUtils.isEmpty(passwd_sure)){
            return ResultObject.setNotOK("请确认新密码");
        }
        if(!passwd.equals(passwd_sure)){
            return ResultObject.setNotOK("两次密码不一致");
        }
        if(!RegExpValidatorUtils.IsPassword(passwd)){
            return ResultObject.setNotOK("密码必须是6~16位字母和数字组合");
        }
        AgentBean agentBean = agentService.queryAgentInfo(mobile,MD5Util.MD5Encode(old_passwd,SystemConstant.charset));
        if(agentBean==null){
            return ResultObject.setNotOK("手机号或原密码有误");
        }
        agentService.modifyAgentPasswd(mobile,MD5Util.MD5Encode(old_passwd,SystemConstant.charset),MD5Util.MD5Encode(passwd,SystemConstant.charset));
        agentBean = agentService.queryAgentInfo(mobile,MD5Util.MD5Encode(passwd,SystemConstant.charset));
        return ResultObject.setOK(agentBean);

    }

    @SsoFilter(isDoFilter = false)
    @RequestMapping("/findAgentPasswd")
    public Object findAgentPasswd(HttpServletRequest request, HttpServletResponse response){
        String mobile = request.getParameter("mobile");
        String msgCode = request.getParameter("msgCode");
        String passwd = request.getParameter("passwd");
        String passwd_sure = request.getParameter("passwd_sure");
        if(StringUtils.isEmpty(mobile)){
            return ResultObject.setNotOK("请输入手机号");
        }
        if(!RegExpValidatorUtils.isPhone(mobile)){
            logger.error("mobile is error");
            return ResultObject.setNotOK("手机号格式有误");
        }
        if(agentService.queryAgentByMobile(mobile)==null){
            logger.error("mobile is not exists");
            return ResultObject.setNotOK("手机号尚未注册");
        }
        if(StringUtils.isEmpty(msgCode)){
            return ResultObject.setNotOK("请输入短信验证码");
        }
        String msgCode_session = (String) request.getSession().getAttribute(super.HQ_MSG_CODE);
        if(StringUtils.isEmpty(msgCode_session)){
            return ResultObject.setNotOK("短信验证码已失效");
        }
        if(StringUtils.isEmpty(passwd)){
            return ResultObject.setNotOK("请输入新密码");
        }
        if(StringUtils.isEmpty(passwd_sure)){
            return ResultObject.setNotOK("请确认新密码");
        }
        if(!RegExpValidatorUtils.IsPassword(passwd)){
            return ResultObject.setNotOK("密码必须是6~16位字母和数字组合");
        }
        if(!passwd.equals(passwd_sure)){
            return ResultObject.setNotOK("两次密码不一致");
        }

        AgentBean agentBean = agentService.queryAgentByMobile(mobile);
        if(!AgentStatusEnum.normal.getCode().equals(agentBean.getStatus())){
            logger.error("agent status is unnormal,status={}",agentBean.getStatus());
            return ResultObject.setNotOK("商户不可用");
        }
        agentService.findAgentPasswd(mobile,MD5Util.MD5Encode(passwd,SystemConstant.charset));
        AgentBean agentBean1 = agentService.queryAgentInfo(mobile,DesUtil.encrypt(passwd));
        return ResultObject.setOK(agentBean1);

    }
    @SsoFilter(isDoFilter = false)
    @RequestMapping("/generateMsgcode")
    public Object generateMsgcode(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("mobile")String mobile){
        if(!RegExpValidatorUtils.isPhone(mobile)){
            logger.error("mobile is error");
            return ResultObject.setNotOK("手机号格式有误");
        }
        if(agentService.queryAgentByMobile(mobile)==null){
            logger.error("mobile is not exists");
            return ResultObject.setNotOK("手机号尚未注册");
        }
        String msgcode = null;
        try {
            msgcode = MsgUtil.generatorMsgCode();
            String msg = "短信验证："+msgcode+"，请及时修改密码!";
            MsgUtil.sendSms(mobile,msg);
            logger.info("generate msgCode success,msgCode={}",msgcode);
        } catch (Exception e) {
            logger.error("generate msgcode  error，e={}",e);
            return ResultObject.setNotOK("生成短信验证码失败");
        }
        request.getSession().setAttribute(super.HQ_MSG_CODE,msgcode);

        return ResultObject.setOK(null);

    }

    /**
     * 查询代理商详情
     * @param request
     * @param response
     * @param agentId 代理商id
     * @return
     */
    @RequestMapping("/loadDetail")
    public Object loadAgentDetail(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("agentId")Integer agentId){
        Map<String,Object> result = Maps.newHashMap();
        AgentWholeDetailBean agentWholeDetailBean =agentDetailService.queryAgentDetail(agentId);
        result.put("agentWholeDetailBean",agentWholeDetailBean);

        List<AgentChangeDetail> agentChangeDetails= agentChangeDetailService.selectAgentChangeByAgentId(agentId);

        result.put("agentChangeDetails",agentChangeDetails);
        List<AgentBalanceChangeDetail> agentBalanceChangeDetails= agentBalanceChangeDetailService.selectBalanceChangeByAgentId(agentId);
        BigDecimal R_b = BigDecimal.ZERO;//初始化总的充值金额
        int R_i =0;//初始化总的充值次数
        BigDecimal D_b = BigDecimal.ZERO;//初始化总的减少金额
        int D_i =0;//初始化总的减少次数
        BigDecimal C_b = BigDecimal.ZERO;//初始化总的消耗金额
        int C_i =0;//初始化总的消耗次数
        if(!CollectionUtils.isEmpty(agentBalanceChangeDetails)){
            for(AgentBalanceChangeDetail balanceChangeDetail:agentBalanceChangeDetails){
                if(BalanceChangeTypeEnum.TYPE_RECHARGE.getCode().equals(balanceChangeDetail.getChangeType())){
                    R_b = R_b.add(balanceChangeDetail.getTransAmount()==null?BigDecimal.ZERO:balanceChangeDetail.getTransAmount());
                    R_i++;
                }else if(BalanceChangeTypeEnum.TYPE_DEDUCTION.getCode().equals(balanceChangeDetail.getChangeType())){
                    D_b= D_b.add(balanceChangeDetail.getTransAmount()==null?BigDecimal.ZERO:balanceChangeDetail.getTransAmount());
                    D_i++;
                }else if(BalanceChangeTypeEnum.TYPE_CONSUME.getCode().equals(balanceChangeDetail.getChangeType())){
                    C_b =C_b.add(balanceChangeDetail.getTransAmount()==null?BigDecimal.ZERO:balanceChangeDetail.getTransAmount());
                    C_i++;
                }
            }

        }
        result.put("R",R_b);
        result.put("R_COUNT",R_i);
        result.put("D",D_b);
        result.put("D_COUNT",D_i);
        result.put("C",C_b);
        result.put("C_COUNT",C_i);
        result.put("agentBalanceChangeDetails",agentBalanceChangeDetails);

        return ResultObject.setOK(result);

    }
}

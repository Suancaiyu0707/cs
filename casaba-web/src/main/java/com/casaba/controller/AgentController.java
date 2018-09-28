package com.casaba.controller;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.casaba.agent.core.bean.AgentBean;
import com.casaba.common.base.User;
import com.casaba.common.util.*;
import com.casaba.core.bean.HqInfoBean;
import com.google.gson.Gson;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.casaba.agent.core.bean.AgentWholeDetailBean;
import com.casaba.agent.core.service.AgentBalanceChangeDetailService;
import com.casaba.agent.core.service.AgentChangeDetailService;
import com.casaba.agent.core.service.AgentDetailService;
import com.casaba.agent.core.service.AgentService;
import com.casaba.aspect.SsoFilter;
import com.casaba.common.constants.DateConstant;
import com.casaba.common.constants.SystemConstant;
import com.casaba.common.enums.AgentLevelEnum;
import com.casaba.common.enums.AgentStatusEnum;
import com.casaba.common.enums.BalanceChangeTypeEnum;
import com.casaba.common.exception.BaseException;
import com.casaba.common.paging.PageInfo;
import com.casaba.common.result.ResultObject;
import com.casaba.dao.entity.AgentBalanceChangeDetail;
import com.casaba.dao.entity.AgentChangeDetail;
import com.casaba.util.CSVUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/***
 * 代理商账号管理
 * @author zhifang.xu
 */
@SsoFilter
@RestController
@RequestMapping("hq/agent")
public class AgentController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AgentController.class);
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentDetailService agentDetailService;
    @Autowired
    private AgentChangeDetailService agentChangeDetailService;
    @Autowired
    private AgentBalanceChangeDetailService agentBalanceChangeDetailService;

    @RequestMapping("/addAgent")
    @ResponseBody
    public Object addAgent(HttpServletRequest request, HttpServletResponse response,
                           @RequestBody AgentWholeDetailBean agentWholeDetail){
        logger.info("begin addAgent:agentWholeDetail={}",agentWholeDetail);
        try{
            if(agentWholeDetail==null||agentWholeDetail.getAgent()==null||agentWholeDetail.getAgentDetail()==null){
                logger.error("agentWholeDetail or agent or agentDetail is empty");
                return ResultObject.setNotOK(SystemConstant.system_error);
            }
            if(StringUtils.isEmpty(agentWholeDetail.getAgent().getMobile())){
                logger.error("mobile is empty");
                return ResultObject.setNotOK("手机号为空");
            }

            if(!RegExpValidatorUtils.isPhone(agentWholeDetail.getAgent().getMobile())){
                logger.error("mobile is error");
                return ResultObject.setNotOK("手机号格式有误");
            }
            if(agentService.queryAgentByMobile(agentWholeDetail.getAgent().getMobile())!=null){
                logger.error("mobile is exists");
                return ResultObject.setNotOK("手机号已注册");
            }
            if(StringUtils.isEmpty(agentWholeDetail.getAgent().getAgentName())) {
                logger.error("agentName is empty");
                return ResultObject.setNotOK("代理商名称为空");
            }
            //TODO 放开可随机生成8位密码
//            String initPasswd = PasswdUtil.getPasswdStringRandom(8);
                String initPasswd = agentWholeDetail.getAgent().getPwd();
                String msg = "账户注册成功，初始密码：" + initPasswd + "，请及时修改！";
                agentWholeDetail.getAgent().setPwd(MD5Util.MD5Encode(initPasswd, SystemConstant.charset));
                Date beginDate = agentWholeDetail.getAgentDetail().getStartTime();
                Date endDate = agentWholeDetail.getAgentDetail().getEndTime();
                agentWholeDetail.getAgentDetail().setStartTime(DateUtil.formatDate1(beginDate == null?new Date():beginDate ,DateConstant.DATE_POINT_HOUR_SS_ZERO_FORMAT));
                agentWholeDetail.getAgentDetail().setEndTime(DateUtil.formatDate1(endDate == null?new Date():endDate ,DateConstant.DATE_POINT_HOUR_SS_59_FORMAT));
                if(agentWholeDetail.getAgentDetail().getEndTime().compareTo(agentWholeDetail.getAgentDetail().getStartTime())<=0){
                    logger.error("endTime not large beginTime");
                    return ResultObject.setNotOK("有效期结束日期必须大于开始日期");
                }
                if (StringUtils.isEmpty(agentWholeDetail.getAgentDetail().getAgentLevel())) {
                    logger.error("agentLevel is empty");
                    return ResultObject.setNotOK("请选择代理商等级");
                }

                if (agentWholeDetail.getAgentDetail().getDiscount() == null) {
                    logger.error("discount is empty");
                    return ResultObject.setNotOK("折扣不能为空");
                }
                if (agentWholeDetail.getAgentDetail().getDiscount().compareTo(new BigDecimal(100)) > 0 ||
                        agentWholeDetail.getAgentDetail().getDiscount().compareTo(new BigDecimal(0)) < 0
                        || !RegExpValidatorUtils.IsDecimal(agentWholeDetail.getAgentDetail().getDiscount().toString())) {
                    logger.error("discount is error,dicount={}", agentWholeDetail.getAgentDetail().getDiscount());
                    return ResultObject.setNotOK("折扣只支持输入0~100内的数字，并支持小数点后2位");
                }
                if (agentWholeDetail.getAgentDetail().getCashDeposit() == null) {
                    logger.error("cashDeposit is empty");
                    return ResultObject.setNotOK("保证金不能为空");
                }
                if (agentWholeDetail.getAgentDetail().getCashDeposit().compareTo(new BigDecimal(0)) <= 0
                        || !RegExpValidatorUtils.IsDecimal(agentWholeDetail.getAgentDetail().getCashDeposit().toString())) {
                    logger.error("cashDeposit is error,cashDeposit={}", agentWholeDetail.getAgentDetail().getCashDeposit());
                    return ResultObject.setNotOK("保证金请输入大于0的数字，支持小数点后2位");
                }

                if (agentWholeDetail.getAgentDetail().getBalance() == null) {
                    logger.error("balance is empty");
                    return ResultObject.setNotOK("预存款不能为空");
                }
                if (agentWholeDetail.getAgentDetail().getBalance().compareTo(new BigDecimal(0)) <= 0
                        || !RegExpValidatorUtils.IsDecimal(agentWholeDetail.getAgentDetail().getBalance().toString())) {
                    logger.error("balance is error,dicount={}", agentWholeDetail.getAgentDetail().getBalance());
                    return ResultObject.setNotOK("预存款请输入大于0的数字，支持小数点后2位");
                }
                if (StringUtils.isEmpty(agentWholeDetail.getAgentDetail().getProvinceCityArea())) {
                    logger.error("province or city is empty");
                    return ResultObject.setNotOK("权限信息省、市不能为空");
                }

                if (agentWholeDetail.getAgentBusiness() != null && !StringUtils.isEmpty(agentWholeDetail.getAgentBusiness().getContacts())
                        && RegExpValidatorUtils.IsNotChinese(agentWholeDetail.getAgentBusiness().getContacts())) {
                    logger.error("contacts is error");
                    return ResultObject.setNotOK("联系人姓名长度为6~16位字符，可以为'数字/字母/中划线/下划线'");
                }
                if (agentWholeDetail.getAgentBusiness() != null && !StringUtils.isEmpty(agentWholeDetail.getAgentBusiness().getContactMobile())
                        && !RegExpValidatorUtils.isPhone(agentWholeDetail.getAgentBusiness().getContactMobile())) {
                    logger.error("phone is error");
                    return ResultObject.setNotOK("联系人手机号有误");
                }

                if (agentWholeDetail.getAgentBusiness() != null && !StringUtils.isEmpty(agentWholeDetail.getAgentBusiness().getEmail())
                        && !RegExpValidatorUtils.isEmail(agentWholeDetail.getAgentBusiness().getEmail())) {
                    logger.error("email is error");
                    return ResultObject.setNotOK("邮箱格式有误");
                }
                //TODO
                agentDetailService.addAgentDetail(agentWholeDetail, this.getCurrentHqInfo(request).getHqName());
                //          agentDetailService.addAgentDetail(agentWholeDetail,1);
        }catch (BaseException be){
            logger.error("add agent fail,e={}",be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("add agent fail,e={}",e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }

        return ResultObject.setOK(null);
    }

    @RequestMapping("/modifyAgent")
    public Object modifyAgent(HttpServletRequest request, HttpServletResponse response,
                           @RequestBody AgentWholeDetailBean agentWholeDetail){

        logger.info("begin modifyAgent:agentWholeDetail={}",agentWholeDetail);
        if(agentWholeDetail==null||agentWholeDetail.getAgent()==null||agentWholeDetail.getAgentDetail()==null){
            logger.error("agentWholeDetail is error");
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
        if(agentWholeDetail.getAgent().getId()==null){
            logger.error("agentWholeDetail.agent.id  is empty");
            return ResultObject.setNotOK(SystemConstant.system_error);
        }

        if(StringUtils.isEmpty(agentWholeDetail.getAgent().getAgentName())){
            logger.error("agentName is empty");
            return ResultObject.setNotOK("代理商名称为空");
        }
        Date beginDate = agentWholeDetail.getAgentDetail().getStartTime();
        Date endDate = agentWholeDetail.getAgentDetail().getEndTime();
        agentWholeDetail.getAgentDetail().setStartTime(DateUtil.formatDate1(beginDate == null?new Date():beginDate ,DateConstant.DATE_POINT_HOUR_SS_ZERO_FORMAT));
        agentWholeDetail.getAgentDetail().setEndTime(DateUtil.formatDate1(endDate == null?new Date():endDate ,DateConstant.DATE_POINT_HOUR_SS_59_FORMAT));
        if(agentWholeDetail.getAgentDetail().getEndTime().compareTo(agentWholeDetail.getAgentDetail().getStartTime())<=0){
            logger.error("endTime not large beginTime");
            return ResultObject.setNotOK("有效期结束日期必须大于开始日期");
        }
        if(StringUtils.isEmpty(agentWholeDetail.getAgentDetail().getAgentLevel())){
            logger.error("agentLevel is empty");
            return ResultObject.setNotOK("请选择代理商等级");
        }

        if(agentWholeDetail.getAgentDetail().getDiscount()==null){
            logger.error("discount is empty");
            return ResultObject.setNotOK("折扣不能为空");
        }
        if(agentWholeDetail.getAgentDetail().getDiscount().compareTo(new BigDecimal(100))>0||
                agentWholeDetail.getAgentDetail().getDiscount().compareTo(new BigDecimal(0))<0
                ||!RegExpValidatorUtils.IsDecimal(agentWholeDetail.getAgentDetail().getDiscount().toString())){
            logger.error("discount is error,dicount={}",agentWholeDetail.getAgentDetail().getDiscount());
            return ResultObject.setNotOK("折扣只支持输入0~100内的数字，并支持小数点后2位");
        }
        if(agentWholeDetail.getAgentDetail().getBalanceAdjust()==null){
            agentWholeDetail.getAgentDetail().setBalanceAdjust(BigDecimal.ZERO);
        }
        if(StringUtils.isEmpty(agentWholeDetail.getAgentDetail().getProvinceCityArea())){
            logger.error("province or city is empty");
            return ResultObject.setNotOK("权限信息省、市不能为空");
        }

        if(agentWholeDetail.getAgentBusiness()!=null&&!StringUtils.isEmpty(agentWholeDetail.getAgentBusiness().getContacts())
                &&RegExpValidatorUtils.IsNotChinese(agentWholeDetail.getAgentBusiness().getContacts())){
            logger.error("contacts is error");
            return ResultObject.setNotOK("联系人姓名长度为6~16位字符，可以为'数字/字母/中划线/下划线'");
        }
        if(agentWholeDetail.getAgentBusiness()!=null&&!StringUtils.isEmpty(agentWholeDetail.getAgentBusiness().getCompanyPhone())
                &&!RegExpValidatorUtils.isPhone(agentWholeDetail.getAgentBusiness().getCompanyPhone())){
            logger.error("phone is error");
            return ResultObject.setNotOK("联系人手机号有误");
        }

        if(agentWholeDetail.getAgentBusiness()!=null&&!StringUtils.isEmpty(agentWholeDetail.getAgentBusiness().getEmail())
                &&!RegExpValidatorUtils.isEmail(agentWholeDetail.getAgentBusiness().getEmail())){
            logger.error("email is error");
            return ResultObject.setNotOK("邮箱有误");
        }
        try{
            agentDetailService.modifyAgentDetail(agentWholeDetail,this.getCurrentHqInfo(request).getHqName());
        }catch (BaseException be){
            logger.error("modify agent fail,e={}",be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("modify agent fail,e={}",e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
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

    /**
     * 查询代理商列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/list")
    public Object queryAgent(HttpServletRequest request,HttpServletResponse response,
                                Integer page,Integer pageSize){

        String discount_dt = request.getParameter("discount_dt");

        String discount_ft = request.getParameter("discount_ft");

        String status = request.getParameter("status");

        String provinceCityArea = request.getParameter("provinceCityArea");


        String agentLevel = request.getParameter("agentLevel");

        String remark = request.getParameter("remark");

        if(page==null||page<=0){
            page = 1;
        }
        if(pageSize==null||pageSize<=0){
            pageSize = 20;
        }
        List<AgentWholeDetailBean>quotaList;
        PageInfo<AgentWholeDetailBean> pageResult = null;
        try{
            pageResult = agentDetailService.queryAgentDetails(discount_dt,discount_ft,status,provinceCityArea,agentLevel,remark,page,pageSize);
            System.out.println(pageResult);
        }catch(Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
            ResultObject.setNotOK(SystemConstant.system_error);

        }
        return ResultObject.setOK(pageResult);
    }

    /***
     *充值
     * @param request
     * @param response
     * @param id 代理商id
     * @param transAmount 交易金额
     * @return
     */
    @RequestMapping("/recharge")
    public Object recharge(HttpServletRequest request,HttpServletResponse response,
                           @RequestParam("id") Integer id,@RequestParam("transAmount") String transAmount){
        if(!RegExpValidatorUtils.IsIntNumber(transAmount)){
            logger.error("transAmount is error,transAmount={}",transAmount);
            return ResultObject.setNotOK("充值金额必须为正整数");
        }
        try{
            HqInfoBean hqInfoBean = this.getCurrentHqInfo(request);
            if(hqInfoBean==null){
                return ResultObject.setNotOK("请先登录");
            }
            agentDetailService.adjustBalance(id,Double.parseDouble(transAmount),this.getCurrentHqInfo(request).getHqName());
            //      agentDetailService.adjustBalance(id,Double.parseDouble(transAmount),"xuzf");
            return ResultObject.setOK(null);
        }catch (BaseException be){
            logger.error("adjustBalance error,id={},amount={},e={}", id,transAmount,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("adjustBalance error,id={},amount={},e={}", id,transAmount,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }

    }

    /***
     *冻结
     * @param request
     * @param response
     * @param id 代理商id
     * @return
     */
    @RequestMapping("/freeze")
    public Object freezeAgent(HttpServletRequest request,HttpServletResponse response,
                           @RequestParam("id") Integer id){
        try{
            agentService.freezeAgent(id);
            return ResultObject.setOK(null);
        }catch (BaseException be){
            logger.error("freezeAgent error,id={},e={}", id,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("freezeAgent error,id={},e={}", id,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }

    }
    /***
     *解冻代理商
     * @param request
     * @param response
     * @param id 代理商id
     * @return
     */
    @RequestMapping("/unfreeze")
    public Object unfreezeAgent(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam("id") Integer id){
        try{
            agentService.unfreezeAgent(id);
            return ResultObject.setOK(null);
        }catch (BaseException be){
            logger.error("unfreezeAgent error,id={},e={}", id,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("unfreezeAgent error,id={},e={}", id,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
    }

    /***
     *调整折扣
     * @param request
     * @param response
     * @param ids 代理商id
     * @param discount 折扣
     * @return
     */
    @RequestMapping("/adjustDiscount")
    public Object adjustDiscount(HttpServletRequest request,HttpServletResponse response,
                                 @RequestBody List<Integer> ids,@RequestParam("discount")String discount){
        try{
            if(StringUtils.isEmpty(discount)){
                logger.error("discount is empty");
                return ResultObject.setNotOK("折扣不能为空");
            }
            BigDecimal b = new BigDecimal(discount);
            if(b.compareTo(new BigDecimal(100))>0||
                    b.compareTo(new BigDecimal(0))<0
                    ||!RegExpValidatorUtils.IsDecimal(discount)){
                logger.error("discount is error,dicount={}",discount);
                return ResultObject.setNotOK("折扣只支持输入0~100内的数字，并支持小数点后2位");
            }
            agentDetailService.batchUpdateDiscount(ids,b);
        }catch (BaseException be){
            logger.error("adjustDiscount error,ids={},discount={},e={}", ids,discount,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("adjustDiscount error,ids={},discount={},e={}", ids,discount,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
        return ResultObject.setOK(null);
    }
    /***
     *调整等级
     * @param request
     * @param response
     * @param ids 代理商id
     * @param agentLevel 代理商等级
     * @return
     */
    @RequestMapping("/adjustLevel")
    public Object adjustLevel(HttpServletRequest request,HttpServletResponse response,
                                 @RequestBody List<Integer> ids,@RequestParam("agentLevel")String agentLevel){
        try{
            if(StringUtils.isEmpty(agentLevel)){
                logger.error("agentLevel is empty");
                return ResultObject.setNotOK("代理商等级不能为空");
            }
            agentDetailService.batchUpdateLevel(ids,agentLevel);
        }catch (BaseException be){
            logger.error("adjustDiscount error,ids={},discount={},e={}", ids,agentLevel,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("adjustDiscount error,ids={},discount={},e={}", ids,agentLevel,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
        return ResultObject.setOK(null);
    }

    /***
     * 导出代理商列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export")
    public void exportAgentDetails(HttpServletRequest request,HttpServletResponse response) throws Exception {


        String discount_dt = request.getParameter("discount_dt");

        String discount_ft = request.getParameter("discount_ft");

        String status = request.getParameter("status");

        String provinceCityArea = request.getParameter("provinceCityArea");


        String agentLevel = request.getParameter("agentLevel");

        String remark = request.getParameter("remark");


        List<AgentWholeDetailBean>quotaList;
        PageInfo<AgentWholeDetailBean> pageResult = null;
        try{
            pageResult = agentDetailService.queryAgentDetails(discount_dt,discount_ft,status,provinceCityArea,agentLevel,remark,1,Integer.MAX_VALUE);

        }catch(Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
            ResultObject.setNotOK(SystemConstant.system_error);

        }
//        ServletOutputStream out = response.getOutputStream();
//        String fileName = new String(
//                ("UserPayAmount-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
//        response.setContentType("application/binary;charset=UTF-8");
//        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".csv");
//

        File csvFile = createCSVFile(request,pageResult.getList());

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(csvFile.getName(), "UTF-8"));

        response.setHeader("Content-Length", String.valueOf(csvFile.length()));

        bis = new BufferedInputStream(new FileInputStream(csvFile));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        while (true) {
            int bytesRead;
            if (-1 == (bytesRead = bis.read(buff, 0, buff.length))) break;
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();


    }

    /***
     * 开始创建临时csv
     * @param request
     * @param list 数据列表
     * @return
     * @throws Exception
     */
    private File createCSVFile(HttpServletRequest request,List<AgentWholeDetailBean> list) throws Exception {


        // 设置表格头
        Object[] head = {"代理商id", "登录手机号", "代理商名称", "代理商等级", "代理商折扣", "保证金（元）", "预存款余额（元）", "代理区域","账号有效期",  "创建时间","创建人", "代理商状态",
            "到期时间","冻结时间","公司名称","公司地址","公司电话","联系人姓名","联系人手机号","联系人固定电话","联系人QQ","联系人Email",};
        List<Object> headList = Arrays.asList(head);


        // 设置数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> rowList = null;
        long num = 0;
        for(AgentWholeDetailBean bean:list){
            num++;
            rowList= Lists.newArrayList();
            rowList.add(bean.getAgent().getId());
            rowList.add(bean.getAgent().getMobile());
            rowList.add(bean.getAgent().getAgentName());
            rowList.add(StringUtils.isEmpty(bean.getAgentDetail().getAgentLevel())?"":AgentLevelEnum.getAgentLevel(bean.getAgentDetail().getAgentLevel()).getDesc());
            rowList.add(bean.getAgentDetail().getDiscount());
            rowList.add(bean.getAgentDetail().getCashDeposit());
            rowList.add(bean.getAgentDetail().getBalance());
            rowList.add(bean.getAgentDetail().getProvinceCityArea());
            rowList.add(DateUtil.formartDate(bean.getAgentDetail().getEndTime(),DateConstant.DATE_POINT_HOUR_SS_FORMAT));
            rowList.add(DateUtil.formartDate(bean.getAgent().getCreateTime(),DateConstant.DATE_POINT_HOUR_SS_FORMAT));
            rowList.add(bean.getAgent().getFounder());
            rowList.add(StringUtils.isEmpty(bean.getAgent().getStatus())?"":AgentStatusEnum.getStatus(bean.getAgent().getStatus()).getMsg());
            rowList.add(DateUtil.formartDate(bean.getAgentDetail().getEndTime(),DateConstant.DATE_POINT_HOUR_SS_FORMAT));
            rowList.add(bean.getAgent().getFreezeTime()==null?"":DateUtil.formartDate(bean.getAgent().getFreezeTime(),DateConstant.DATE_POINT_HOUR_SS_FORMAT));
            rowList.add(bean.getAgentBusiness().getCompanyName());
            rowList.add(bean.getAgentBusiness().getAddress());
            rowList.add(bean.getAgentBusiness().getCompanyPhone());
            rowList.add(bean.getAgentBusiness().getContacts());
            rowList.add(bean.getAgentBusiness().getContactMobile());
            rowList.add(bean.getAgentBusiness().getContactTelephone());
            rowList.add(bean.getAgentBusiness().getQq());
            rowList.add(bean.getAgentBusiness().getEmail());


            dataList.add(rowList);
        }

        String fileName = new String(
                ("代理商列表-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
        // 导出CSV文件
        File csvFile = CSVUtils.createCSVFile(headList, dataList, fileName);

        return csvFile;
    }
    @RequestMapping("/loginAgent")
    public Object loginAgent(@RequestParam("agentId")Integer agentId){
        Gson gson = new Gson();
        AgentWholeDetailBean bean = agentDetailService.queryAgentDetail(agentId);
        if(bean==null||bean.getAgent()==null){
            return ResultObject.setNotOK("代理商信息不存在");
        }
        AgentBean agentBean = bean.getAgent();
        User user = new User();
        user.setId(agentId);
        user.setMobile(agentBean.getMobile());
        String key = DesUtil.encrypt(gson.toJson(user));
        return ResultObject.setOK(key);
//        FormBody frombody = new FormBody.Builder().
//                add("key",DesUtil.encrypt(key))
//                .build();

//        try {
//            String result_str = OKHttpUtils.post(OKHttpUtils.agentLoginUrl,frombody);
//            if(!StringUtils.isEmpty(result_str)){
//                ResultObject result = gson.fromJson(result_str,ResultObject.class);
//                return result;
//            }
//            logger.error("login agent and return empty");
//            return ResultObject.setNotOK("登录失败");
//        } catch (IOException e) {
//            logger.error("login agent error,e={}",e);
//           return ResultObject.setNotOK("登录失败");
//        }

    }
}

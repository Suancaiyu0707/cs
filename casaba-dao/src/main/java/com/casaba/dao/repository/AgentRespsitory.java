package com.casaba.dao.repository;

import com.casaba.common.constants.BusinessConstant;
import com.casaba.common.constants.DateConstant;
import com.casaba.common.enums.AgentStatusEnum;
import com.casaba.common.enums.BalanceChangeTypeEnum;
import com.casaba.common.exception.AgentServiceException;
import com.casaba.common.exception.BaseException;
import com.casaba.common.util.DateUtil;
import com.casaba.dao.entity.*;
import com.casaba.dao.mapper.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import sun.management.resources.agent;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * 代理商 Respsitory
 * @author zhifang.xu
 */
@Repository
public class AgentRespsitory {
    private static final Logger logger = LoggerFactory.getLogger(AgentRespsitory.class);
    @Autowired
    private AgentInfoMapper agentInfoMapper;
    @Autowired
    private AgentDetailMapper agentDetailMapper;
    @Autowired
    private AgentBusinessMapper agentBusinessMapper;
    @Autowired
    private AgentChangeDetailMapper agentChangeDetailMapper;

    @Autowired
    private AgentBalanceChangeDetailMapper agentBalanceChangeDetailMapper;

    /***
     * 新增代理商信息
     * @param agentInfo 代理商信息
     */
    public void addAgentInfo(AgentInfo agentInfo) {
        if(agentInfo == null){
            logger.error("agentInfo is empty");
            return ;
        }
        if(StringUtils.isEmpty(agentInfo.getMobile())){
            logger.error("mobile is empty");
            return ;
        }
        if(StringUtils.isEmpty(agentInfo.getAgentName())){
            agentInfo.setAgentName(agentInfo.getMobile());
        }
        agentInfoMapper.insert(agentInfo);
    }

    /***
     * 根据手机号和密码查询代理商信息(只查状态是00)
     * @param mobile 手机号
     * @param passwd 密码
     * @return
     */
    public AgentInfo queryAgentInfo(String mobile, String passwd) {

        return agentInfoMapper.queryAgentByMobile(mobile,passwd);
    }

    /***
     * 修改代理商账号密码
     * @param mobile 手机号
     * @param passwd 旧密码
     * @param pwd 新密码
     */
    public void modifyAgentPasswd(String mobile, String passwd,String pwd) {
        agentInfoMapper.modifyAgentPasswd(mobile,passwd,pwd);
    }

    /***
     * 代理商账号找回密码
     * @param mobile 手机号
     * @param passwd 新密码
     */
    public void findAgentPasswd(String mobile, String passwd) {
        agentInfoMapper.findAgentPasswd(mobile, passwd);
    }
    /***
     * 根据手机号查询代理商账号
     * @param mobile 手机号
     * @return
     */
    public AgentInfo queryAgentByMobile(String mobile){
        return agentInfoMapper.queryAgentByMobile(mobile);
    }

    /***
     * 更新代理商状态
     * @param id 主键id
     * @param status  状态：正常00,到期04,冻结01
     */
    public void updateAgentStatus(Integer id,String status) {
        agentInfoMapper.updateAgentStatus(id,status);
    }


    /***
     * 更新登录时间
     * @param id 主键Id
     */
    public void updateLastLoginTime(Integer id) {
        agentInfoMapper.updateLastLoginTime(id,new Date());
    }
    @Transactional
    public void addAgent(AgentWholeDetail agentWholeDetail, String fouder){
        Date currentDate = new Date();
        if(agentWholeDetail ==null){
            logger.error("agentWholeDetail is empty");
            throw new BaseException("","请求信息为空");
        }
        AgentInfo agentInfo = agentWholeDetail.getAgent();
        if(agentInfo == null){
            logger.error("agent is empty");
            throw new BaseException("","代理商账号为空");
        }

        AgentDetail agentDetail = agentWholeDetail.getAgentDetail();
        if(agentDetail == null){
            logger.error("agentDetail is empty");
            throw new BaseException("","代理商权限信息为空");
        }
        AgentBusiness agentBusiness = agentWholeDetail.getAgentBusiness();
        //插入代理商账号信息
        agentInfo.setFounder(fouder);
        agentInfo.setCreateTime(currentDate);
        agentInfoMapper.insert(agentInfo);
        //插入代理商基本信息
        agentDetail.setCreateTime(currentDate);
        agentDetail.setAgentId(agentInfo.getId());
        agentDetailMapper.insert(agentDetail);
        //插入代理商联系信息
        agentBusiness.setCreateTime(currentDate);
        agentBusiness.setAgentId(agentInfo.getId());
        agentBusinessMapper.insert(agentBusiness);

    }

    /***
     * 修改代理商信息
     * @param agentWholeDetail 代理商详情
     * @param operater 操作人
     */
    @Transactional
    public void modifyAgent(AgentWholeDetail agentWholeDetail,String operater){

        List<AgentChangeDetail> changeList = Lists.newArrayList();
        AgentBalanceChangeDetail agentBalanceChangeDetail = null;
        Date currentDate = new Date();
        if(agentWholeDetail ==null){
            logger.error("agentWholeDetail is empty");
            throw new BaseException("","请求信息为空");
        }
        //代理商账号校验
        AgentInfo agentInfo = agentWholeDetail.getAgent();
        if(agentInfo == null){
            logger.error("agent is empty");
            throw new BaseException("","代理商账号为空");
        }

        AgentInfo agentInfo_o = agentInfoMapper.selectById(agentInfo.getId());
        if(agentInfo_o == null){
            logger.error("agent is not exists,id={}",agentInfo.getId());
            throw new BaseException("","代理商账号不存在");
        }
        if(!agentInfo_o.getAgentName().equals(agentInfo.getAgentName())){
            changeList.add(new AgentChangeDetail(agentInfo.getId(),currentDate,
                    BusinessConstant.agent_change_agentName,agentInfo_o.getAgentName(),agentInfo.getAgentName(),operater,currentDate));
        }
        //代理商权限信息校验
        AgentDetail agentDetail = agentWholeDetail.getAgentDetail();
        if(agentDetail == null){
            logger.error("agentDetail is empty");
            throw new BaseException("","代理商权限信息为空");
        }
        AgentDetail agentDetail_o = agentDetailMapper.selectByAgentId(agentInfo.getId());
        if(agentDetail_o == null){
            logger.error("agentDetail is not exists,agentId={}",agentInfo.getId());
            throw new BaseException("","代理商权限信息不存在");
        }
        if(agentDetail_o.getAgentId() == null||agentDetail_o.getAgentId()!=agentInfo.getId()){
            logger.error("agentDetail.agentId is not exists,agentDetail={},agentId={}",agentDetail,agentInfo.getId());
            throw new BaseException("","代理商权限信息有误");
        }

        if(agentDetail_o.getDiscount().compareTo(agentDetail.getDiscount())!=0){
            changeList.add(new AgentChangeDetail(agentInfo.getId(),currentDate,
                    BusinessConstant.agent_change_discount,
                    agentDetail_o.getDiscount().toString(),agentDetail.getDiscount().toString(),operater,currentDate));
        }
        if(!agentDetail_o.getProvinceCityArea().equals(agentDetail.getProvinceCityArea())){

            changeList.add(new AgentChangeDetail(agentInfo.getId(),currentDate,
                    BusinessConstant.agent_change_area,agentDetail_o.getProvinceCityArea(),agentDetail.getProvinceCityArea()
                    ,operater,currentDate));
        }

        if(!agentDetail_o.getEndTime().equals(agentDetail.getEndTime())){
            changeList.add(new AgentChangeDetail(agentInfo.getId(),currentDate,
                    BusinessConstant.agent_change_endTime,DateUtil.formartDate(agentDetail_o.getEndTime(),DateConstant.DATE_FORMAT),
                    DateUtil.formartDate(agentDetail.getEndTime(),DateConstant.DATE_FORMAT),operater,currentDate));
        }



        //代理商企业信息校验
        AgentBusiness agentBusiness = agentWholeDetail.getAgentBusiness();
        if(agentBusiness == null){
            logger.error("agentBusiness is empty");
            throw new BaseException("","代理商企业信息为空");
        }
        AgentBusiness agentBusiness_o = agentBusinessMapper.selectByAgentId(agentInfo.getId());
        if(agentBusiness_o == null){
            logger.error("agentBusiness is not exists,agentId={}",agentInfo.getId());
            throw new BaseException("","代理商企业信息不存在");
        }
        if(agentBusiness_o.getAgentId() == null||agentBusiness_o.getAgentId()!=agentInfo.getId()){
            logger.error("agentBusiness.agentId is not exists,agentBusiness={},agentId={}",agentBusiness,agentInfo.getId());
            throw new BaseException("","代理商企业信息有误");
        }

        //开始修改代理账号
        agentInfo.setUpdateTime(currentDate);
        agentInfoMapper.updateById(agentInfo);
        //开始修改更新时间
        agentDetail.setUpdateTime(currentDate);
        agentDetailMapper.updateById(agentDetail);
        //查询变更后余额，创建充值变更记录
        AgentDetail detailAftertrans= agentDetailMapper.selectByAgentId(agentInfo.getId());

        if(agentWholeDetail.getBalanceAdjust().compareTo(BigDecimal.ZERO)>0){
            agentBalanceChangeDetail = new AgentBalanceChangeDetail(agentInfo.getId(),currentDate,null,BusinessConstant.agent_balance_change_recharge,
                    agentWholeDetail.getBalanceAdjust(),detailAftertrans.getBalance(),BalanceChangeTypeEnum.TYPE_RECHARGE.getCode(),operater,currentDate);
        }else if(agentWholeDetail.getBalanceAdjust().compareTo(BigDecimal.ZERO)<0){
            agentBalanceChangeDetail = new AgentBalanceChangeDetail(agentInfo.getId(),currentDate,null,BusinessConstant.agent_balance_change_deduction,
                    agentWholeDetail.getBalanceAdjust(),detailAftertrans.getBalance(),BalanceChangeTypeEnum.TYPE_DEDUCTION.getCode(),operater,currentDate);
        }
        //更新企业信息
        agentBusiness.setUpdateTime(currentDate);
        agentBusinessMapper.updateById(agentBusiness);

        if(!CollectionUtils.isEmpty(changeList)){
            agentChangeDetailMapper.batchInsert(changeList);
        }
        //新增充值变更记录
        if(agentBalanceChangeDetail!=null){
            agentBalanceChangeDetailMapper.insert(agentBalanceChangeDetail);
        }

    }

    /***
     * 批量更新 代理商等级
     * @param ids 批量id
     * @param level 等级
     * @return
     */
    public void batchUpdateLevel(List<Integer> ids, String level){
        if(CollectionUtils.isEmpty(ids)){
            logger.error("ids is empty");
            throw new BaseException("","ids is empty");
        }
        if(StringUtils.isEmpty(level)){
            logger.error("level is empty");
            throw new BaseException("","level is empty");
        }
        agentDetailMapper.batchUpdateLevel(ids,level);
    }

    /***
     * 批量修改折扣
     * @param ids 批量id
     * @param discount 折扣
     * @return
     */
    public void batchUpdateDiscount(List<Integer> ids, BigDecimal discount){
        agentDetailMapper.batchUpdateDiscount(ids,discount);
        if(CollectionUtils.isEmpty(ids)){
            logger.error("ids is empty");
            throw new BaseException("","ids is empty");
        }
    }

    /***
     * 根据账号Id查询代理详情
     * @param agentId 代理商账号id
     */
    public AgentWholeDetail queryAgentDetail(Integer agentId) {
        AgentWholeDetail agentWholeDetail = new AgentWholeDetail();
        AgentInfo agentInfo = agentInfoMapper.selectById(agentId);
        if(agentInfo == null){
            logger.error("agent not exists,agentId={}",agentId);
            throw new BaseException("","agent not exists");
        }
        AgentDetail agentDetail = agentDetailMapper.selectByAgentId(agentId);
        AgentBusiness agentBusiness = agentBusinessMapper.selectByAgentId(agentId);

        agentWholeDetail.setAgentBusiness(agentBusiness);
        agentWholeDetail.setAgent(agentInfo);
        agentWholeDetail.setAgentDetail(agentDetail);
        return agentWholeDetail;
    }
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
    public Page<Map> queryAgentDetails(String discount_dt,  String discount_ft, String status,
                                String provinceCityArea,
                               String agentLevel, String remark,Integer page,Integer pageSize){
        PageHelper.startPage(page,pageSize);
        Page<Map> result =agentDetailMapper.queryAgentDetails( discount_dt,   discount_ft,  status,
                   provinceCityArea,agentLevel,  remark);
        return result;
    }

    /***
     * 充值
     * @param agentId 代理商id
     * @param amount 充值金额
     */
    public void adjustBalance(Integer agentId, double amount,String operator) {
        Date currentDate = new Date();
        AgentInfo agentInfo = agentInfoMapper.selectById(agentId);
        if(agentInfo == null){
            logger.error("agentInfo  not exists,id={}",agentId);
            throw new AgentServiceException("","系统异常");
        }
        if(!AgentStatusEnum.normal.getCode().equals(agentInfo.getStatus())){
            logger.error("agentInfo  not normal,id={}",agentId);
            throw new AgentServiceException("","账号过期或被冻结");
        }
        agentDetailMapper.adjustBalance(agentId,amount);
        //查询充值后余额
        AgentDetail agentDetail = agentDetailMapper.selectByAgentId(agentId);

        AgentBalanceChangeDetail detail =null;
        if(amount>0){
            detail = new AgentBalanceChangeDetail(agentId,currentDate,null,BusinessConstant.agent_balance_change_recharge,new BigDecimal(amount),
                    agentDetail.getBalance(),BalanceChangeTypeEnum.TYPE_RECHARGE.getCode(),operator,currentDate);
        }else{
            detail = new AgentBalanceChangeDetail(agentId,currentDate,null,BusinessConstant.agent_balance_change_deduction,new BigDecimal(amount),
                    agentDetail.getBalance(),BalanceChangeTypeEnum.TYPE_DEDUCTION.getCode(),operator,currentDate);

        }
        agentBalanceChangeDetailMapper.insert(detail);
    }
    /***
     * 充值
     * @param agentId 代理商id
     * @param amount 充值金额
     * @param transNo 交易号
     * @param project 变更项目
     * @param operator 操作者
     */
    public void deductionBalance(Integer agentId, BigDecimal amount,String transNo,String project,String operator) {
        Date currentDate = new Date();
        AgentInfo agentInfo = agentInfoMapper.selectById(agentId);
        if(agentInfo == null){
            logger.error("agentInfo  not exists,id={}",agentId);
            throw new AgentServiceException("","系统异常");
        }
        if(!AgentStatusEnum.normal.getCode().equals(agentInfo.getStatus())){
            logger.error("agentInfo  not normal,id={}",agentId);
            throw new AgentServiceException("","账号过期或被冻结");
        }
        AgentDetail agentDetail_o = agentDetailMapper.selectByAgentId(agentId);
        if(agentDetail_o==null){
            logger.error("agentDetail is empty,agentId={}",agentId);
            throw new AgentServiceException("","系统异常");
        }

        if(agentDetail_o.getBalance()==null||agentDetail_o.getBalance().compareTo(amount)<0){
            throw new AgentServiceException("","余额不足，扣款失败");
        }
        agentDetailMapper.deductionBalance(agentId,amount);
        //查询消费后余额
        AgentDetail agentDetail = agentDetailMapper.selectByAgentId(agentId);
        AgentBalanceChangeDetail detail = new AgentBalanceChangeDetail(agentId,currentDate,transNo,project,amount,
                agentDetail.getBalance(),BalanceChangeTypeEnum.TYPE_CONSUME.getCode(),operator,currentDate);
        agentBalanceChangeDetailMapper.insert(detail);
    }

    public AgentInfo loadAgentInfo(Integer id){
        return agentInfoMapper.selectById(id);
    }

    /**
     * 查询代理商余额
     * @param agentId 账号ID
     * @return
     */
    public AgentDetail queryBalance( Integer agentId){
       return  agentDetailMapper.queryBalance(agentId);
    }

    /***
     * 更新过期代理商
     */
    public void expireAgents() {

        agentInfoMapper.expireAgent(new Date());
    }

    /***
     * 查询代理商列表，只查询状态是00
     * @return
     */
    public List<AgentInfo> selectAgents(){
        return agentInfoMapper.selectAgents();
    }
}

package com.casaba.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.casaba.common.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.casaba.aspect.SsoFilter;
import com.casaba.common.constants.SystemConstant;
import com.casaba.common.exception.BaseException;
import com.casaba.common.result.ResultObject;
import com.casaba.common.util.MsgUtil;
import com.casaba.common.util.RegExpValidatorUtils;
import com.casaba.core.bean.HqInfoBean;
import com.casaba.core.service.HqService;

/***
 * 总台账号管理controller
 * @author  zhifang.xu
 */
@SsoFilter
@RestController
@RequestMapping("hq/account")
public class AccountController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private HqService hqService;

    /***
     * 修改总部账号密码
     * @param request
     * @param response
     * @return
     */
    @SsoFilter(isDoFilter = false)
    @RequestMapping("/modifyHqPasswd")
    public Object modifyHqPasswd(HttpServletRequest request, HttpServletResponse response){
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
        if(hqService.queryHqByMobile(mobile)==null){
            logger.error("mobile is not exists");
            return ResultObject.setNotOK("手机号尚未注册");
        }
        if(StringUtils.isEmpty(old_passwd)){
            return ResultObject.setNotOK("请输入原密码");
        }
        if(StringUtils.isEmpty(passwd)){
            logger.error("passwd is empty");
            return ResultObject.setNotOK("请输入新密码");
        }
        if(StringUtils.isEmpty(passwd_sure)){
            logger.error("passwd_sure is empty");
            return ResultObject.setNotOK("请确认新密码");
        }
        if(!passwd.equals(passwd_sure)){
            logger.error("passwd not equal passwd_sure");
            return ResultObject.setNotOK("两次密码不一致");
        }
        if(!RegExpValidatorUtils.IsPassword(passwd)){
            logger.error("passwd is unvalid,passwd={}",passwd);
           return ResultObject.setNotOK("密码必须是6~16位字母和数字组合");
        }
        HqInfoBean hqInfoBean = hqService.queryHqInfo(mobile,MD5Util.MD5Encode(old_passwd,SystemConstant.charset));

        if(hqInfoBean==null){
            logger.error("hqInfoBean is null");
            return ResultObject.setNotOK("手机号或原密码有误");
        }
        hqService.modifyHqPasswd(mobile,MD5Util.MD5Encode(old_passwd,SystemConstant.charset),MD5Util.MD5Encode(passwd,SystemConstant.charset));
        hqInfoBean.setPwd(null);//不能把密码返回给前端
        return ResultObject.setOK(hqInfoBean);

    }

    /***
     * 通过短信验证码找回账号密码
     * @param request
     * @param response
     * @return
     */
    @SsoFilter(isDoFilter = false)
    @RequestMapping("/findHqPasswd")
    public Object findHqPasswd(HttpServletRequest request, HttpServletResponse response){
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
        HqInfoBean hqInfoBean = hqService.queryHqByMobile(mobile);
        if(hqInfoBean==null){
            logger.error("hqInfoBean is not exists,mobile={}",mobile);
            return ResultObject.setNotOK("手机号尚未注册");
        }
        if(StringUtils.isEmpty(msgCode)){
            logger.error("msgCode is empty");
            return ResultObject.setNotOK("请输入短信验证码");
        }
        String msgCode_session = (String) request.getSession().getAttribute(super.MSG_CODE);
        if(StringUtils.isEmpty(msgCode_session)){
            logger.error("msgCode is expire");
            return ResultObject.setNotOK("短信验证码已失效");
        }
        if(!msgCode_session.equals(msgCode)){
            logger.error("msgCode is error");
            return ResultObject.setNotOK("短信验证码错误");
        }
        if(StringUtils.isEmpty(passwd)){
            logger.error("passwd is empty");
            return ResultObject.setNotOK("请输入新密码");
        }
        if(StringUtils.isEmpty(passwd_sure)){
            logger.error("passwd_sure is empty");

            return ResultObject.setNotOK("请确认新密码");
        }
        if(!RegExpValidatorUtils.IsPassword(passwd)){
            logger.error("passwd is uninvlid");
            return ResultObject.setNotOK("密码必须是6~16位字母和数字组合");
        }
        if(!passwd.equals(passwd_sure)){
            logger.error("passwd not equals passwd_sure");
            return ResultObject.setNotOK("两次密码不一致");
        }

        hqService.findHqPasswd(mobile,MD5Util.MD5Encode(passwd,SystemConstant.charset));//修改密码
        hqInfoBean.setPwd(null);
        return ResultObject.setOK(hqInfoBean);

    }

    /***
     * 生成短信验证码
     * @param request
     * @param response
     * @param mobile 手机号
     * @return
     */
    @SsoFilter(isDoFilter = false)
    @RequestMapping("/generateMsgcode")
    public Object generateMsgcode(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("mobile")String mobile){
        if(!RegExpValidatorUtils.isPhone(mobile)){
            logger.error("mobile is error");
            return ResultObject.setNotOK("手机号格式有误");
        }
        if(hqService.queryHqByMobile(mobile)==null){
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
            return ResultObject.setNotOK("短信验证码获取失败");
        }
        request.getSession().setAttribute(super.MSG_CODE,msgcode);

        return ResultObject.setOK(null);

    }

    /***
     * 查询总部用户信息
     * @param request
     * @param response
     * @param id 用户id
     * @return
     */
    @RequestMapping("/loadHqInfo")
    public Object loadHqInfo(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("id") Integer id){

        try{
            HqInfoBean hqInfoBean = hqService.loadHqInfoById(id);
            if(hqInfoBean==null){
                logger.error("hqInfoBean is null,id={}",id);
                return ResultObject.setNotOK("账号不存在");
            }
            Integer founderId = hqInfoBean.getFounder();
            HqInfoBean founder = hqService.loadHqInfoById(founderId);
            if(founder==null||StringUtils.isEmpty(founder.getHqName())){
                return ResultObject.setOK(hqInfoBean);
            }
            hqInfoBean.setFounderName(founder.getHqName());
            return ResultObject.setOK(hqInfoBean);
        }catch (BaseException be){
            logger.error("load hqInfo fail,e={}",be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("load hqInfo fail,e={}",e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }

    }

}

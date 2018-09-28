package com.casaba.agent.controller;


import com.casaba.common.constants.SystemConstant;
import com.casaba.common.enums.AgentStatusEnum;
import com.casaba.common.result.ResultObject;
import com.casaba.agent.core.bean.AgentBean;
import com.casaba.agent.core.service.AgentService;
import com.casaba.agent.aspect.SsoFilter;
import com.casaba.agent.util.VerifyCodeUtil;
import com.casaba.agent.util.jwt.JwtHelper;
import com.casaba.common.util.MD5Util;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/***
 * 代理商登录controller
 * @author  zhifang.xu
 */
@Controller
@RequestMapping("agent/index")

public class AgentLoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AgentLoginController.class);

    @Autowired
    private AgentService agentService;
    /***
     * 用户登录 将登录信息保存到cookie
     * @param request
     * @param response
     * @param mobile 手机号
     * @param passwd 密码
     * @param verifyCode 验证码
     * @return
     */
    @SsoFilter(isDoFilter = false)
    @RequestMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(value = "mobile") String mobile,
                        @RequestParam(value = "passwd") String passwd,
                        @RequestParam(value = "verifyCode") String verifyCode) {

        HttpSession session = request.getSession();
        String verifyCode_session = (String) session.getAttribute("hq_verifyCode");
        if(verifyCode == null|| !verifyCode.equals(verifyCode_session)){
            return  ResultObject.setNotOK("验证码错误");
        }
        try {
            AgentBean agentBean = agentService.queryAgentInfo(mobile, MD5Util.MD5Encode(passwd,SystemConstant.charset));
            if (agentBean == null) {
                logger.error("mobile or passwd is error,mobile={}", mobile);
                return ResultObject.setNotOK("手机号或密码错误");
            }
            if (!AgentStatusEnum.normal.getCode().equals(agentBean.getStatus())) {
                logger.error("agent status is not normal,mobile or passwd is error,mobile={}", mobile);
                return ResultObject.setNotOK("账号已过期或被冻结");
            }
//        //单点登录设置
//        Map <String, String>map = Maps.newHashMap();
//        map.put("userName",agentInfo.getAgentName());
//        map.put("mobile",agentInfo.getMobile());
//        map.put("passwd",agentInfo.getPwd());
//        String token = JwtHelper.genToken(map);
//        super.addCookie(request,response,token);

            session.setAttribute(BaseController.SESSION_USER, agentBean);
            session.setAttribute(BaseController.SESSION_MOBILE, agentBean.getMobile());
        }catch(Exception e){
            logger.error("agent login error,e={}",e);
            return  ResultObject.setNotOK(SystemConstant.system_error);
        }
        return  ResultObject.setOK(null);

    }

    @SsoFilter(isDoFilter = false)
    @RequestMapping("forwardLogin")
    public String forwardLogin(HttpServletRequest request) {

        return "/login.html";
    }

    /***
     * 生成验证码
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getVerifyCodeImage")
    public Object getVerifyCodeImage(HttpSession session, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);
        //将验证码放到HttpSession里面
        session.setAttribute("hq_verifyCode", verifyCode);
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();

        String base64Img = encoder.encode(outputStream.toByteArray());
        return ResultObject.setOK(base64Img);
    }



}
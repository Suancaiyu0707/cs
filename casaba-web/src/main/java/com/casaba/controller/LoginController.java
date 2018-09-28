package com.casaba.controller;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.casaba.common.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casaba.aspect.SsoFilter;
import com.casaba.common.constants.SystemConstant;
import com.casaba.common.result.ResultObject;
import com.casaba.core.bean.HqInfoBean;
import com.casaba.core.service.HqService;
import com.casaba.util.VerifyCodeUtil;

import sun.misc.BASE64Encoder;

/***
 * 总台账号登录controller
 * @author zhifang.xu
 */
@RestController
@RequestMapping("hq")
public class LoginController extends BaseController {

    private static  final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private HqService hqService;
    /***
     * 用户登录
     * @param request
     * @param response
     * @param mobile 手机号
     * @param passwd 密码
     * @param verifyCode 验证码
     * @return
     */
    @SsoFilter(isDoFilter = false)
    @RequestMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(value = "mobile") String mobile,
                        @RequestParam(value = "passwd") String passwd,
                        @RequestParam(value = "verifyCode") String verifyCode) {

        HttpSession session = request.getSession();
        String verifyCode_session = (String) session.getAttribute("verifyCode");
        if(verifyCode == null|| !verifyCode.equals(verifyCode_session)){
            return  ResultObject.setNotOK("验证码错误");
        }
        try {
            HqInfoBean hqInfoBean = hqService.queryHqInfo(mobile, MD5Util.MD5Encode(passwd,SystemConstant.charset));
            if (hqInfoBean == null) {
                logger.error("mobile or passwd is error,mobile={}", mobile);
                return ResultObject.setNotOK("手机号或密码错误");
            }
            hqService.modifyLastLoginTime(mobile, MD5Util.MD5Encode(passwd,SystemConstant.charset));
            //单点登录设置
//            Map <String, String> map = Maps.newHashMap();
//            map.put("userName", hqInfoBean.getHqName());
//            map.put("mobile", hqInfoBean.getMobile());
//            map.put("passwd", hqInfoBean.getPwd());
//            String token = JwtHelper.genToken(map);
//            super.addCookie(request, response, token);
            session.setAttribute(BaseController.SESSION_HQ_USER, hqInfoBean);
            session.setAttribute(BaseController.SESSION_HQ_MOBILE, hqInfoBean.getMobile());
            hqInfoBean.setPwd(null);
            return ResultObject.setOK(hqInfoBean);
        }catch(Exception e){
            logger.error("login error,e={}",e);
            return  ResultObject.setNotOK(SystemConstant.system_error);
        }

    }

    @SsoFilter(isDoFilter = true)
    @RequestMapping("/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.removeAttribute(BaseController.SESSION_HQ_USER);
        session.removeAttribute(BaseController.SESSION_HQ_MOBILE);
        return ResultObject.setOK(null);

    }

    @SsoFilter(isDoFilter = false)
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {

        return "/index.html";
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
        session.setAttribute("verifyCode", verifyCode);
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();

        String base64Img = encoder.encode(outputStream.toByteArray());
        return ResultObject.setOK(base64Img);
    }

}
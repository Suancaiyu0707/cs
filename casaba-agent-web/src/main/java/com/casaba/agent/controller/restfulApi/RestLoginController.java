package com.casaba.agent.controller.restfulApi;

import com.casaba.agent.aspect.SsoFilter;
import com.casaba.agent.controller.BaseController;
import com.casaba.agent.core.bean.AgentBean;
import com.casaba.agent.core.service.AgentService;
import com.casaba.common.base.User;
import com.casaba.common.result.ResultObject;
import com.casaba.common.util.DesUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/***
 * 用户rpc登录
 * @author zhifang.xu
 */
@RestController
@RequestMapping("/rest")
public class RestLoginController extends BaseController {
    @Autowired
    private AgentService agentService;
    private static final Logger logger = LoggerFactory.getLogger(RestLoginController.class);
    /***
     * 用户越权登录
     * @param key
     * @return
     */
    @SsoFilter(isDoFilter = false)
    @RequestMapping("login")
    public String restLogin(HttpServletRequest request, @RequestParam("key") String key){
        String loginMsg = null;
        Gson gson = new Gson();
        try {
            loginMsg = DesUtil.decryptor(key);
        } catch (Exception e) {
            logger.error("login fail,key={},e={}",key,e);
            return  gson.toJson(ResultObject.setNotOK("msg decry fail"));
        }

        User user = gson.fromJson(loginMsg,User.class);
        String mobile = user.getMobile();
        String passwd = user.getPassword();
        AgentBean agentBean = agentService.queryAgentInfo(mobile,passwd);
        if(agentBean==null){
            logger.error("agent is not exists");
            gson.toJson(ResultObject.setNotOK("手机号或密码错误")) ;
        }
        agentBean.setPwd("");
        HttpSession session = request.getSession();
        session.setAttribute(BaseController.SESSION_USER,agentBean);
        return gson.toJson(ResultObject.setOK(agentBean));
    }
}

package com.casaba.agent.intercept;

import com.casaba.agent.controller.BaseController;
import com.casaba.agent.core.bean.AgentBean;
import com.casaba.agent.core.service.AgentService;
import com.casaba.common.enums.AgentStatusEnum;
import com.casaba.common.util.DesUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Map;
@Component
public class AcessInterceptor extends HandlerInterceptorAdapter{

    private static final Logger logger = LoggerFactory.getLogger(AcessInterceptor.class);
    @Autowired
    private AgentService agentService;

    public AcessInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(BaseController.LOGIN_COOKIE.equals(cookie.getName())){
                    try{
                        String val = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        String loginMsg = DesUtil.decryptor(val);
                        Map<String,String> map = new Gson().fromJson(loginMsg,Map.class);
                        String mobile = map.get("mobile");
                        String passwd = map.get("passwd");//加密了密码
                        AgentBean agentBean = agentService.queryAgentInfo(mobile,passwd);
                        if(agentBean!=null&&AgentStatusEnum.normal.getCode().equals(agentBean.getStatus())){
                            HttpSession session = request.getSession();
                            session.setAttribute(BaseController.SESSION_USER, agentBean);
                            session.setAttribute(BaseController.SESSION_MOBILE, agentBean.getMobile());
                            return true;
                        }else{
                            logger.warn("agent is not exist or agent's status is no normal");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);

                        }
                    }catch (Exception e){
                        logger.warn("system error,e={}",e);
                    }

                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}

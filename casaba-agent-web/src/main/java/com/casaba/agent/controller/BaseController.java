package com.casaba.agent.controller;

import com.casaba.agent.core.bean.AgentBean;
import com.casaba.common.base.User;
import com.casaba.agent.util.UserUtil;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class BaseController {
    private final static int COOKIE_AGE = (int) TimeUnit.DAYS.toSeconds(7);

    public static final String SESSION_USER="SESSION_USER";
    public static final String SESSION_MOBILE="SESSION_MOBILE";
    public final static String HQ_MSG_CODE="HQ_MSG_CODE";

    public void addCookie(HttpServletRequest request, HttpServletResponse response,String token){
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setMaxAge(COOKIE_AGE);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);

    }

    public User getCurrentUser(HttpServletRequest request) {
        User user = UserUtil.getUser(request);
        return user;
    }

    /***
     * 获得当前登录用户
     * @param request
     * @return
     */
    public AgentBean getCurrentAgent(HttpServletRequest request) {
        AgentBean user = (AgentBean) request.getSession().getAttribute(BaseController.SESSION_USER);

        return user;
    }
}

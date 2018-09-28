package com.casaba.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.casaba.common.base.User;
import com.casaba.core.bean.HqInfoBean;
import com.casaba.util.UserUtil;

public class BaseController {

    private final static int COOKIE_AGE = (int) TimeUnit.DAYS.toSeconds(7);

    public final static String MSG_CODE="MSG_CODE";

    public final static String SESSION_HQ_USER="SESSION_HQ_USER";
    public final static String SESSION_HQ_MOBILE="SESSION_HQ_MOBILE";

    public final static String LOGIN_PAGE_URL = "https://localhost";

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
    public HqInfoBean getCurrentHqInfo(HttpServletRequest request) {
        HqInfoBean user = (HqInfoBean) request.getSession().getAttribute(BaseController.SESSION_HQ_USER);

        return user;
    }
}

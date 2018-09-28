package com.casaba.agent.aspect;

import com.alibaba.fastjson.JSONObject;

import com.casaba.agent.controller.BaseController;
import com.casaba.agent.core.bean.AgentBean;
import com.casaba.agent.util.OKHttpUtils;
import com.casaba.agent.util.SsoHttpResult;
import com.casaba.agent.util.SysContentUtil;
import com.casaba.agent.util.UserUtil;
import com.casaba.common.base.User;
import com.casaba.common.result.ResultObject;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class SsoAspect {


	private final static String LOGIN_PAGE_URL = "https://localhost:8080/index";
	private final static int COOKIE_AGE = (int) TimeUnit.DAYS.toSeconds(7);

	private static final Logger logger = LoggerFactory.getLogger(SsoAspect.class);

	@Around(value = "execution(* com.casaba.agent.controller..*.*(..))")
	public Object around(ProceedingJoinPoint point) throws Exception {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取request
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();//获取response

//		HttpServletRequest request = SysContentUtil.getRequest();// 获取request
//		HttpServletResponse response = SysContentUtil.getResponse();// 获取response
		HttpSession session = request.getSession();
		boolean isDoFilter = false;
		// 获得类的SsoFilter注解
		Signature signature = point.getSignature();// 获得方法签名
		Class cls = point.getTarget().getClass();// 利用反射拿到Class对象
		Annotation[] clsAnnotations = cls.getAnnotationsByType(SsoFilter.class);

		// 获得方法的反射对象
		Method targetMethod = ((MethodSignature) (signature)).getMethod();
		// 获得方法上的的StreamLog注解
		Annotation[] methodAnnotations = targetMethod.getAnnotationsByType(SsoFilter.class);
		try {
			if (methodAnnotations.length > 0 || clsAnnotations.length > 0) {
				SsoFilter ssoFilter = methodAnnotations.length > 0 ? (SsoFilter) methodAnnotations[0]
						: (SsoFilter) clsAnnotations[0];
				isDoFilter = ssoFilter.isDoFilter();
				if (isDoFilter) {
					AgentBean agentBean = (AgentBean) session.getAttribute(BaseController.SESSION_USER);
					if(agentBean==null){
						return ResultObject.setLogin("会话失效，请重新登录");
					}
				}

			}
			return point.proceed();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}

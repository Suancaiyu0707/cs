package com.casaba.aspect;


import com.casaba.common.result.ResultObject;
import com.casaba.core.bean.HqInfoBean;
import com.casaba.core.service.HqService;
import com.casaba.controller.BaseController;
import com.casaba.util.SysContentUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class SsoAspect {
	@Autowired
	private HqService hqService;

	private static final Logger logger = LoggerFactory.getLogger(SsoAspect.class);

	@Around(value = "execution(* com.casaba.controller..*.*(..))")
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
					HqInfoBean hqInfoBean = (HqInfoBean) session.getAttribute(BaseController.SESSION_HQ_USER);
					if(hqInfoBean==null){
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
	private String getRedirectUrl(HttpServletRequest request) throws UnsupportedEncodingException {
		String requestURI = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath() // 项目名称
				+ request.getRequestURI() // 请求路径
				+ (request.getQueryString() == null ? "" : "?" + request.getQueryString()); // 参数 ;
		return BaseController.LOGIN_PAGE_URL + "/?redirect_url=" + URLEncoder.encode(requestURI, "UTF-8");
	}


}

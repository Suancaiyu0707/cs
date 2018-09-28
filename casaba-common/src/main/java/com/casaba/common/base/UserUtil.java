package com.casaba.common.base;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日期工具类
 *
 * @author changqing
 */
public class UserUtil {

	public static User getUser(HttpServletRequest request) {
		String token = request.getParameter("token");
		String userAgent = request.getHeader("User-Agent");
		if (token == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (!cookie.getName().equals("token"))
						continue;
					token = cookie.getValue();

				}
			}
		}
		return getUserFromSso(token, userAgent);
	}

	private static User getUserFromSso(String token, String userAgent) {
		if (StringUtils.isBlank(token) || StringUtils.isBlank(userAgent))
			return null;
		String url = "https://sso.casaba.cn/service/checkToken";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("token", token);
		jsonObject.put("userAgent", userAgent);
		System.out.println(jsonObject.toJSONString());
		String result = null;
		try {
			result = OKHttpUtils.post(url, jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		SsoHttpResult ssoHttpResult = JSONObject.parseObject(result, SsoHttpResult.class);
		JSONObject data = ssoHttpResult.getData();

		if (ssoHttpResult.getStatus() == 0 || data == null) {
			return null;
		}
		User user = JSONObject.parseObject(data.toJSONString(), User.class);
		return user;
	}


}

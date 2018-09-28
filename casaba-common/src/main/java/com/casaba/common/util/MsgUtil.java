package com.casaba.common.util;

import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgUtil {
    private static final String msgUrl = "";

    public static String getMsgCode() throws IOException {
        //return OKHttpUtils.get(msgUrl);
        return "123456";
    }
    
    
    private static final Logger logger = LoggerFactory.getLogger(MsgUtil.class);

	/**
	 * 	发送短信方法
	 * @param mobile 手机号 
	 * @param content  短信内容
	 */
	public static void sendSms(String mobile,String content) {
		
		logger.info("手机号："+mobile+" , 内容"+content);
		
	}


	public static String generatorMsgCode() {
	    return getRandNum(6);
    }
    public static String getRandNum(int charCount) {
	    String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
	}
    public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

}

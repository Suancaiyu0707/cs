package com.casaba.common.exception;


/**
 * 商户相关异常类
 * @author zhifang.xu
 * @version 1.0
 * @created 2018/09/23
 */
public class AgentServiceException extends BaseException{
    private static final long serialVersionUID = 1L;


    public AgentServiceException(String errorMsg) {
        super(errorMsg);
    }
    public AgentServiceException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public AgentServiceException(String errorCode, Throwable caused) {
        super(errorCode, caused);
    }

    public AgentServiceException(String errorCode, String errorMsg, Throwable caused) {
        super(errorCode, errorMsg, caused);
    }
}

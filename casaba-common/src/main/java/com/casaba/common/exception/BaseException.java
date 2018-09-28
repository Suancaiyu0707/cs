package  com.casaba.common.exception;


/**
 * 异常基类
 * @author tqlei
 *
 */
public class BaseException extends RuntimeException{
	private static final long serialVersionUID = 3565041668191154037L;

	public static final String MONITOR_LOG="$monitor-exchange";
	/**
	 * 异常错误代码，使用4位字符串， 第一位代表产生异常的系统代码 后三位代表具体的错误代码含义 错误代码由具体的常量定义
	 */
	protected String errorCode;

	/** 异常错误信息，由实际抛出异常的类定义 */
	protected String errorMsg;

	public BaseException(String errorCode, String errorMsg) {
		super(errorMsg);

		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public BaseException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}

	public BaseException(String errorCode, Throwable caused) {
		super(caused);

		this.errorCode = errorCode;
	}

	public BaseException(String errorCode, String errorMsg,
			Throwable caused) {
		super(errorMsg, caused);

		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**
	 * 获得异常的错误代码
	 * 
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 获得异常的错误信息
	 * 
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

}

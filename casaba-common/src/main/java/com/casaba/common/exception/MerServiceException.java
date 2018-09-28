package  com.casaba.common.exception;


/**
 * 商户相关异常类
 * @author tqlei
 * @version 1.0
 * @created 2018/09/23
 */
public class MerServiceException extends BaseException{
    private static final long serialVersionUID = -8771976637613166612L;

    
    public MerServiceException(String errorMsg) {
        super(errorMsg);
    }
    public MerServiceException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public MerServiceException(String errorCode, Throwable caused) {
        super(errorCode, caused);
    }

    public MerServiceException(String errorCode, String errorMsg, Throwable caused) {
        super(errorCode, errorMsg, caused);
    }
}

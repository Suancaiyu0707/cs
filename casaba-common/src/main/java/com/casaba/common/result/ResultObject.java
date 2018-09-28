package com.casaba.common.result;


import com.casaba.common.enums.ResultCodeEnum;
import lombok.Data;
import lombok.ToString;

/***
 * 返回结果信息
 */
@Data
@ToString
public class ResultObject {
    /**
     * 返回状态 1-成功 0-失败
     */
    private String status;
    /***
     * 错误信息
     */
    private String msg;
    /***
     * 结果对象
     */
    private Object data;
    public ResultObject(String status){
        this.status = status;
    }
    public ResultObject(String status, String msg){
       this.status = status;
       this.msg = msg;
    }

    public ResultObject(String status, String msg, Object data){
        this.status = status;
        this.data = data;
    }

    public static ResultObject setOK(Object data){
        return new ResultObject(ResultCodeEnum.SUCCESS.getCode(),null,data);
    }

    public static ResultObject setNotOK(String msg){
        return new ResultObject(ResultCodeEnum.FAIL.getCode(),msg);
    }
    public static ResultObject setLogin(String msg){
        return new ResultObject(ResultCodeEnum.LOGIN.getCode(),msg);
    }
}

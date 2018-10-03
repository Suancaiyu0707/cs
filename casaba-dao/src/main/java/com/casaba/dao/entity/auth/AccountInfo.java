package com.casaba.dao.entity.auth;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/***
 * 账号信息
 * @author zhifang.xu
 */
@Data
@ToString
@Alias("accountInfo")
public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /**
     * 账号名称
     */
    private String accountName;
    /**
     * 账号密码
     */
    private String pwd;
    /**
     * 账号状态 0-可用 1-不可用
     */
    private String status;

    private Date createTime;

    private Date updateTime;
    /***
     * 上一次登录时间
     */
    private Date lastLoginTime;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 创建人
     */
    private String operator;

}
package com.casaba.auth.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/***
 * 账号信息
 * @author zhifang.xu
 */
@Data
@ToString
public class AccountBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 账号名称
     */
    private String accountName;
    /**
     * 账号密码
     */
    private String pwd;
    /***
     * 账号状态
     */
    private String status;
    /***
     * 创建时间
     */
    private Date createTime;
    /**
     * 上一次登录时间
     */
    private Date lastLoginTime;
    /**
     * 手机号
     */
    private String mobile;
    /***
     * 创建人
     */
    private String operator;
    /***
     * 角色id
     */
    private int roleId;
    /***
     * 角色名称
     */
    private String roleName;
}

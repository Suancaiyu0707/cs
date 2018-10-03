package com.casaba.dao.entity.auth;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 账号权限信息
 * @author zhifang.xu
 */
@Data
@ToString
@Alias("accountResInfo")
public class AccountResInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 账号id
     */
    private Integer accountId;
    /***
     * 权限id
     */
    private Integer resId;
    /**
     * 状态 0-可用 1-禁用
     */
    private String status;

    private Date createTime;

    private Date updateTime;
}
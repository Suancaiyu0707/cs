package com.casaba.dao.entity.auth;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/***
 * 账号角色信息
 * @author zhifang.xu
 */
@ToString
@Data
@Alias("accountRoleInfo")
public class AccountRoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Integer id;
    /***
     * 账号id
     */
    private Integer accountId;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 状态 0-可用 1-禁用
     */
    private String status;

}
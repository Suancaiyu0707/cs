package com.casaba.dao.entity.auth;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限信息
 * @author zhifang.xu
 */
@Data
@ToString
@Alias("roleResInfo")
public class RoleResInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键Id
     */
    private Integer id;
    /***
     * 角色id
     */
    private Integer roleId;
    /**
     * 权限id
     */
    private Integer resId;
    /***
     * 创建时间
     */
    private Date createTime;

    private Date updateTime;
    /**
     * 0-可用 1-禁用
     */
    private String status;
}
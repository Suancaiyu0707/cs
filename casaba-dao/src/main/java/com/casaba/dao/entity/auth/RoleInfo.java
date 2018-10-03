package com.casaba.dao.entity.auth;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/***
 * 角色信息
 * @author zhifang.xu
 */
@ToString
@Data
@Alias("role")
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;

    private Date createTime;

    private Date updateTime;
    /**
     * 1-可用 1-禁用
     */
    private String status;
    /**
     * 父角色id
     */
    private Integer parentRoleId;
    /**
     * 备注
     */
    private String remark;

}
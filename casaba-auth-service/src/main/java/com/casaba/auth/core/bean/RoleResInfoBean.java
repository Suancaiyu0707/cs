package com.casaba.auth.core.bean;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/***
 * 权限资源信息
 * @author zhifang.xu
 */
@Data
@ToString
public class RoleResInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /***
     * 角色id
     */
    private int roleId;
    /***
     * 角色名称
     */
    private String roleName;
    /**
     * 创建人
     */
    private String operator;
    /***
     * 创建时间
     */
    private Date createTime;
    /***
     * 账号统计
     */
    private Integer accountCount;
    /***
     * 角色持有的权限信息
     */
    List<ResInfoBean> resInfos= Lists.newArrayList();

}

package com.casaba.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class HqInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 名称
     */
    private String hqName;
    /***
     * 密码
     */
    private String pwd;
    /**
     * 手机号
     */
    private String mobile;
    /***
     * 创建时间
     */
    private Date createTime;

    private Date updateTime;
    /***
     * 创建者
     */
    private Integer founder;
    /***
     * 创建人姓名
     */
    private String founderName;
    /***
     * 上一次登录时间
     */
    private Date lastLoginTime;
}

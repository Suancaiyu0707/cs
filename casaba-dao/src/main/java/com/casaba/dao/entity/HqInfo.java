package com.casaba.dao.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/***
 * 总台信息bean
 * @author zhifang.xu
 */
@Data
@ToString
@Alias("hqInfo")

public class HqInfo implements Serializable {

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
    /***
     * 手机号
     */
    private String mobile;

    private Date createTime;

    private Date updateTime;
    /***
     * 创建人
     */
    private Integer founder;
    /***
     * 上一次登录时间
     */
    private Date lastLoginTime;

}
package com.casaba.dao.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 销售员信息
 * @author zhifang.xu
 */
@Data
@ToString
@Alias("saler")
public class SalerInfo  implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Integer id;
    /***
     * 销售员名称
     */
    private String salerName;
    /***
     * 销售员密码
     */
    private String passwd;
    /***
     * 手机号
     */
    private String mobile;
}
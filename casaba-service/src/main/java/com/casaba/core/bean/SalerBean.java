package com.casaba.core.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 销售员bean
 * @author zhifang.xu
 */
@Data
@ToString
public class SalerBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /***
     * 主键id
     */
    private Integer id;
    /***
     * 渠道员名称
     */
    private String canalName;
    /***
     * 手机号
     */
    private String mobile;
    /***
     * 密码
     */
    private String passwd;
    /***
     * 管理区域
     */
    private String managerArea;
    /**
     * 商户数
     */
    private Integer merchantCount;
    /**
     * 线索数
     */
    private Integer clueCount;
    /**
     * 销售额
     */
    private BigDecimal saleAmount;
}

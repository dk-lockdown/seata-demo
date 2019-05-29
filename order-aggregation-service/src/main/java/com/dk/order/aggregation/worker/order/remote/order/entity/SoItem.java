package com.dk.order.aggregation.worker.order.remote.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  订单明细表
 * @author scott lewis 2019-05-27
 */
@Data
public class SoItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * sysno
     */
    private Long sysNo;

    /**
     * so_sysno
     */
    private Long soSysNo;

    /**
     * product_sysno
     */
    private Long productSysNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 成交价
     */
    private BigDecimal dealPrice;

    /**
     * 数量
     */
    private Integer quantity;

    public SoItem() {
    }

}
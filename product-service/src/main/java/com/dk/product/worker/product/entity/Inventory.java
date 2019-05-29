package com.dk.product.worker.product.entity;

import java.io.Serializable;
import lombok.Data;

/**
 *  商品库存
 * @author scott lewis 2019-05-27
 */
@Data
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long sysno;

    /**
     * product_sysno
     */
    private Long productSysNo;

    /**
     * 财务库存
     */
    private Integer accountQty;

    /**
     * 可用库存
     */
    private Integer availableQty;

    /**
     * 分配库存
     */
    private Integer allocatedQty;

    /**
     * 调整锁定库存
     */
    private Integer adjustLockedQty;

    public Inventory() {
    }

}

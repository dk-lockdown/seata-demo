package com.dk.order.aggregation.worker.order.remote.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  商品sku
 * @author scott lewis 2019-05-27
 */
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long sysNo;

    /**
     * 品名
     */
    private String productName;

    /**
     * product_title
     */
    private String productTitle;

    /**
     * 描述
     */
    private String productDesc;

    /**
     * 描述
     */
    private String productDescLong;

    /**
     * default_image_src
     */
    private String defaultImageSrc;

    /**
     * c3_sysno
     */
    private Long c3SysNo;

    /**
     * barcode
     */
    private String barcode;

    /**
     * length
     */
    private Integer length;

    /**
     * width
     */
    private Integer width;

    /**
     * height
     */
    private Integer height;

    /**
     * weight
     */
    private Float weight;

    /**
     * merchant_sysno
     */
    private Long merchantSysNo;

    /**
     * merchant_productid
     */
    private String merchantProductId;

    /**
     * 1，待上架；2，上架；3，下架；4，售罄下架；5，违规下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 修改时间
     */
    private Date gmtModified;

    public Product() {
    }

}

package com.dk.order.worker.order.entity;

import java.io.Serializable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  订单表
 * @author scott lewis 2019-05-27
 */
@Data
public class SoMaster implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * sysno
     */
    private Long sysNo;

    /**
     * so_id
     */
    private String soId;

    /**
     * 下单用户号
     */
    private Long buyerUserSysNo;

    /**
     * 卖家公司编号
     */
    private String sellerCompanyCode;

    /**
     * receive_division_sysno
     */
    private Long receiveDivisionSysNo;

    /**
     * receive_address
     */
    private String receiveAddress;

    /**
     * receive_zip
     */
    private String receiveZip;

    /**
     * receive_contact
     */
    private String receiveContact;

    /**
     * receive_contact_phone
     */
    private String receiveContactPhone;

    /**
     * stock_sysno
     */
    private Long stockSysNo;

    /**
     * 支付方式：1，支付宝，2，微信
     */
    private Integer paymentType;

    /**
     * 订单总额
     */
    private BigDecimal soAmt;

    /**
     * 10，创建成功，待支付；30；支付成功，待发货；50；发货成功，待收货；70，确认收货，已完成；90，下单失败；100已作废
     */
    private Integer status;

    /**
     * 下单时间
     */
    private Date orderDate;

    /**
     * 支付时间
     */
    private Date paymemtDate;

    /**
     * 发货时间
     */
    private Date deliveryDate;

    /**
     * 发货时间
     */
    private Date receiveDate;

    /**
     * 订单来源
     */
    private String appid;

    /**
     * 备注
     */
    private String memo;

    /**
     * create_user
     */
    private String createUser;

    /**
     * gmt_create
     */
    private Date gmtCreate;

    /**
     * modify_user
     */
    private String modifyUser;

    /**
     * gmt_modified
     */
    private Date gmtModified;

    private List<SoItem> soItems;

    public SoMaster() {
    }

}
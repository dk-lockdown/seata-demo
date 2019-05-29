package com.dk.order.aggregation.worker.order.remote.product.req;

import lombok.Data;

@Data
public class AllocateInventoryReq {
    private Long productSysNo;

    private Integer qty;
}

package com.dk.product.worker.product.req;

import lombok.Data;

@Data
public class AllocateInventoryReq {
    private Long productSysNo;

    private Integer qty;
}

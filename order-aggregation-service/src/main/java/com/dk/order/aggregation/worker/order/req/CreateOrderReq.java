package com.dk.order.aggregation.worker.order.req;

import java.util.List;

public class CreateOrderReq {
    public List<ShoppingProduct> shoppingProducts;

    public List<ShoppingProduct> getShoppingProducts() {
        return shoppingProducts;
    }

    public void setShoppingProducts(List<ShoppingProduct> shoppingProducts) {
        this.shoppingProducts = shoppingProducts;
    }

    public class ShoppingProduct {
        private String productSysNo;

        private String quantity;

        public String getProductSysNo() {
            return productSysNo;
        }

        public void setProductSysNo(String productSysNo) {
            this.productSysNo = productSysNo;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
}

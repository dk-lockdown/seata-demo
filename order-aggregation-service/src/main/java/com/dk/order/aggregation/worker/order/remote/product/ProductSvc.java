package com.dk.order.aggregation.worker.order.remote.product;

import com.dk.order.aggregation.worker.order.remote.product.entity.Product;
import com.dk.order.aggregation.worker.order.remote.product.req.AllocateInventoryReq;
import com.dk.foundation.engine.baseentity.StandResponse;
import com.dk.foundation.engine.baseentity.TccRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "product", url = "${product.svc.host}", path = "product")
public interface ProductSvc {
    /**
     * [查詢] 根據主鍵 id 查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    @RequestMapping(value ="/v1/product/load",method = RequestMethod.GET)
    public StandResponse<Product> load(Long sysno);

    @RequestMapping(value ="/v1/product/allocateInventory",method = RequestMethod.POST, headers = {"Seata-Transaction-Mode=TCC"})
    public StandResponse allocateInventory(@RequestBody TccRequest<List<AllocateInventoryReq>> reqs);

    @RequestMapping(value ="/v1/product/commitAllocateInventory",method = RequestMethod.POST, headers = {"Seata-Transaction-Mode=TCC"})
    public StandResponse commitAllocateInventory(@RequestBody TccRequest<List<AllocateInventoryReq>> reqs);

    @RequestMapping(value ="/v1/product/cancelAllocateInventory",method = RequestMethod.POST, headers = {"Seata-Transaction-Mode=TCC"})
    public StandResponse cancelAllocateInventory(@RequestBody TccRequest<List<AllocateInventoryReq>> reqs);
}

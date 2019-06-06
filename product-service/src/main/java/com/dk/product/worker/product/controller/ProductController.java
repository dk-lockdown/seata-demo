package com.dk.product.worker.product.controller;

import com.dk.foundation.engine.baseentity.StandResponse;
import com.dk.foundation.engine.baseentity.TccRequest;
import com.dk.foundation.engine.exception.BusinessException;
import com.dk.product.worker.BaseController;
import com.dk.product.worker.product.entity.Product;
import com.dk.product.worker.product.req.AllocateInventoryReq;
import com.dk.product.worker.product.service.ProductService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品sku
 * @author scott lewis
 * @date 2019/05/27
 */
@RestController
@RequestMapping(value = "/v1/Product")
public class ProductController extends BaseController {

    @Resource
    private ProductService productService;

    /**
     * [查詢] 根據主鍵 id 查詢
     * @author scott lewis
     * @date 2019/05/27
     **/
    @RequestMapping(value = "/load",method = RequestMethod.GET)
    public StandResponse<Product> load(Long sysno){
        return success(productService.load(sysno));
    }

    @RequestMapping(value = "/allocateInventory",method = RequestMethod.POST)
    public StandResponse allocateInventory(@RequestBody TccRequest<List<AllocateInventoryReq>> reqs) throws BusinessException {
        productService.allocateInventory(reqs);
        return success();
    }

    @RequestMapping(value = "/commitAllocateInventory",method = RequestMethod.POST)
    public StandResponse commitAllocateInventory(@RequestBody TccRequest<List<AllocateInventoryReq>> reqs) {
        productService.commitAllocateInventory(reqs);
        return success();
    }

    @RequestMapping(value = "/cancelAllocateInventory",method = RequestMethod.POST)
    public StandResponse cancelAllocateInventory(@RequestBody TccRequest<List<AllocateInventoryReq>> reqs) throws BusinessException {
        productService.cancelAllocateInventory(reqs);
        return success();
    }

    @RequestMapping(value = "/allocateInventory2",method = RequestMethod.POST)
    public StandResponse allocateInventory(@RequestBody List<AllocateInventoryReq> reqs) throws BusinessException {
        productService.allocateInventory(reqs);
        return success();
    }
}
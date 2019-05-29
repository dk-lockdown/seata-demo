package com.dk.order.aggregation.worker.order.action.impl;

import com.alibaba.fastjson.JSON;
import com.dk.order.aggregation.worker.order.action.AllocateInventoryTccAction;
import com.dk.order.aggregation.worker.order.remote.product.req.AllocateInventoryReq;
import com.dk.foundation.engine.baseentity.StandResponse;
import com.dk.foundation.engine.baseentity.TccRequest;
import com.dk.order.aggregation.worker.order.remote.product.ProductSvc;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AllocateInventoryTccActionImpl implements AllocateInventoryTccAction {
    @Resource
    ProductSvc productSvc;

    @Override
    public boolean prepare(BusinessActionContext actionContext, String allocateInventoryReq) {
        List<AllocateInventoryReq> reqs = JSON.parseArray(allocateInventoryReq,AllocateInventoryReq.class);
        TccRequest<List<AllocateInventoryReq>> tccRequest = new TccRequest<>();
        tccRequest.setXid(actionContext.getXid());
        tccRequest.setBranchId(actionContext.getBranchId());
        tccRequest.setData(reqs);
        StandResponse result = productSvc.allocateInventory(tccRequest);
        if (result.getSuccess()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        List<AllocateInventoryReq> reqs = JSON.parseArray(actionContext.getActionContext("allocateInventoryReq").toString(),AllocateInventoryReq.class);
        TccRequest<List<AllocateInventoryReq>> tccRequest = new TccRequest<>();
        tccRequest.setXid(actionContext.getXid());
        tccRequest.setBranchId(actionContext.getBranchId());
        tccRequest.setData(reqs);
        productSvc.commitAllocateInventory(tccRequest);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        List<AllocateInventoryReq> reqs = JSON.parseArray(actionContext.getActionContext("allocateInventoryReq").toString(),AllocateInventoryReq.class);
        TccRequest<List<AllocateInventoryReq>> tccRequest = new TccRequest<>();
        tccRequest.setXid(actionContext.getXid());
        tccRequest.setBranchId(actionContext.getBranchId());
        tccRequest.setData(reqs);
        productSvc.cancelAllocateInventory(tccRequest);
        return false;
    }
}

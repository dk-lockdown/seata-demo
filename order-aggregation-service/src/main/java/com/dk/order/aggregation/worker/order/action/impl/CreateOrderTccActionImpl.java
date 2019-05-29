package com.dk.order.aggregation.worker.order.action.impl;

import com.alibaba.fastjson.JSON;
import com.dk.order.aggregation.worker.order.action.CreateOrderTccAction;
import com.dk.order.aggregation.worker.order.remote.order.OrderSvc;
import com.dk.order.aggregation.worker.order.remote.order.entity.SoMaster;
import com.dk.foundation.engine.baseentity.StandResponse;
import com.dk.foundation.engine.baseentity.TccRequest;
import com.dk.foundation.engine.exception.BusinessException;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CreateOrderTccActionImpl implements CreateOrderTccAction {
    @Resource
    OrderSvc orderSvc;


    @Override
    public boolean prepare(BusinessActionContext actionContext, List<SoMaster> soMasters, String soSysNos) throws BusinessException {
        if (soMasters==null||soMasters.size()==0) {
            throw new BusinessException("订单不能为空");
        }

        TccRequest<List<SoMaster>> tccRequest = new TccRequest<>();
        tccRequest.setXid(actionContext.getXid());
        tccRequest.setBranchId(actionContext.getBranchId());
        tccRequest.setData(soMasters);

        StandResponse<List<Long>> result = orderSvc.insert(tccRequest);
        if (result.getSuccess()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        /**
         * 有了prepare阶段的前置订单检查，此处不用检查订单是否为空
         */
        List<Long> sysnos = JSON.parseArray(actionContext.getActionContext("SoSysNos").toString(),Long.class);

        TccRequest<List<Long>> tccRequest = new TccRequest<>();
        tccRequest.setXid(actionContext.getXid());
        tccRequest.setBranchId(actionContext.getBranchId());
        tccRequest.setData(sysnos);

        StandResponse<List<Long>> result = orderSvc.updateSoStatusToCreateSuccess(tccRequest);
        if (result.getSuccess()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        /**
         * 有了prepare阶段的前置订单检查，此处不用检查订单是否为空
         */
        List<Long> sysnos = JSON.parseArray(actionContext.getActionContext("SoSysNos").toString(),Long.class);

        TccRequest<List<Long>> tccRequest = new TccRequest<>();
        tccRequest.setXid(actionContext.getXid());
        tccRequest.setBranchId(actionContext.getBranchId());
        tccRequest.setData(sysnos);

        StandResponse<List<Long>> result = orderSvc.updateSoStatusToCreateFail(tccRequest);
        if (result.getSuccess()) {
            return true;
        }
        return false;
    }
}

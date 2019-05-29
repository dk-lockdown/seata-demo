package com.dk.order.aggregation.worker.order.action;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.List;

@LocalTCC
public interface AllocateInventoryTccAction {
    /**
     * Prepare boolean.
     *
     * @param actionContext the action context
     * @param allocateInventoryReq             json string
     * @return the boolean
     */
    @TwoPhaseBusinessAction(name = "AllocateInventoryTccAction" , commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext actionContext, @BusinessActionContextParameter(paramName = "allocateInventoryReq") String allocateInventoryReq);

    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    public boolean commit(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    public boolean rollback(BusinessActionContext actionContext);
}

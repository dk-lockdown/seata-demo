package com.dk.order.aggregation.worker.order.action;

import com.dk.foundation.engine.exception.BusinessException;
import com.dk.order.aggregation.worker.order.remote.order.entity.SoMaster;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.List;

@LocalTCC
public interface CreateOrderTccAction {
    /**
     * Prepare boolean.
     *
     * @param actionContext the action context
     * @param soMasters
     * @param soSysNos  json string
     * @return the boolean
     */
    @TwoPhaseBusinessAction(name = "CreateOrderTccAction" , commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext actionContext, List<SoMaster> soMasters, @BusinessActionContextParameter(paramName = "SoSysNos") String soSysNos) throws BusinessException;

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

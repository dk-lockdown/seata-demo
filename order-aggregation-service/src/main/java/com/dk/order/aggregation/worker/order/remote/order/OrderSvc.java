package com.dk.order.aggregation.worker.order.remote.order;

import com.dk.order.aggregation.worker.order.remote.order.entity.SoMaster;
import com.dk.foundation.engine.baseentity.StandResponse;
import com.dk.foundation.engine.baseentity.TccRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "order", url = "${order.svc.host}", path = "order")
public interface OrderSvc {
    /**
     * [新增]
     * @author scott lewis
     * @date 2019/05/27
     **/
    @RequestMapping(value = "/v1/So/insert",method = RequestMethod.POST, headers = {"Seata-Transaction-Mode=TCC"})
    public StandResponse<List<Long>> insert(@RequestBody TccRequest<List<SoMaster>> soMasters);

    @RequestMapping(value = "/v1/So/updateSoStatusToCreateSuccess",method = RequestMethod.POST, headers = {"Seata-Transaction-Mode=TCC"})
    public StandResponse updateSoStatusToCreateSuccess(@RequestBody TccRequest<List<Long>> sysnos);

    @RequestMapping(value = "/v1/So/updateSoStatusToCreateFail",method = RequestMethod.POST, headers = {"Seata-Transaction-Mode=TCC"})
    public StandResponse updateSoStatusToCreateFail(@RequestBody TccRequest<List<Long>> sysnos);

    @RequestMapping(value = "/v1/So/insert2",method = RequestMethod.POST)
    public StandResponse<List<Long>> insert(@RequestBody List<SoMaster> soMasters);
}

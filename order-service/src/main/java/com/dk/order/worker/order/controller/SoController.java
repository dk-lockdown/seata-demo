package com.dk.order.worker.order.controller;

import com.dk.foundation.engine.baseentity.StandResponse;
import com.dk.foundation.engine.baseentity.TccRequest;
import com.dk.foundation.engine.exception.BusinessException;
import com.dk.order.worker.BaseController;
import com.dk.order.worker.order.entity.SoMaster;
import com.dk.order.worker.order.service.SoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单表
 * @author scott lewis
 * @date 2019/05/27
 */
@RestController
@RequestMapping(value = "/v1/So")
public class SoController extends BaseController {

    @Resource
    private SoService soService;

    /**
     * [新增]
     * @author scott lewis
     * @date 2019/05/27
     **/
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public StandResponse<List<Long>> insert(@RequestBody TccRequest<List<SoMaster>> soMasters) throws BusinessException {
        List<Long> result = soService.createSo(soMasters);
        return success(result);
    }

    @RequestMapping(value = "/updateSoStatusToCreateSuccess",method = RequestMethod.POST)
    public StandResponse updateSoStatusToCreateSuccess(@RequestBody TccRequest<List<Long>> sysnos) {
        soService.updateSoStatusToCreateSuccess(sysnos);
        return success();
    }

    @RequestMapping(value = "/updateSoStatusToCreateFail",method = RequestMethod.POST)
    public StandResponse updateSoStatusToCreateFail(@RequestBody TccRequest<List<Long>> sysnos) {
        soService.updateSoStatusToCreateFail(sysnos);
        return success();
    }

    @RequestMapping(value = "/insert2",method = RequestMethod.POST)
    public StandResponse<List<Long>> insert(@RequestBody List<SoMaster> soMasters) throws BusinessException {
        List<Long> result = soService.createSo(soMasters);
        return success(result);
    }
}
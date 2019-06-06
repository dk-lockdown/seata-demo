package com.dk.order.aggregation.worker.order.controller;

import com.dk.foundation.engine.baseentity.StandResponse;
import com.dk.foundation.engine.exception.BusinessException;
import com.dk.order.aggregation.worker.StandResponseBuilder;
import com.dk.order.aggregation.worker.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/v1/order")
@Api(value = "OrderController", description = "下单服务", produces = "application/json;charset=UTF-8")
public class OrderController {
    @Autowired
    OrderService orderService;

    @ApiOperation(value = "下单Tcc模式")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public @ResponseBody
    StandResponse<List<Long>> createSo() throws BusinessException {
        return StandResponseBuilder.ok(orderService.createSo(false));
    }

    @ApiOperation(value = "下单At模式")
    @RequestMapping(value = "create2", method = RequestMethod.GET)
    public @ResponseBody
    StandResponse<List<Long>> createSo2() throws BusinessException {
        return StandResponseBuilder.ok(orderService.createSo(true));
    }
}

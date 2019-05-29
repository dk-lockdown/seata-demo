package com.dk.order.aggregation.worker;

import com.dk.foundation.engine.baseentity.StandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;


public class BaseController implements InitializingBean {

    final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public BaseController() {
    }

    public <E> StandResponse<E> success() {
        return StandResponseBuilder.ok();
    }


    public <E> StandResponse<E> success(E data) {
        return StandResponseBuilder.ok(data);
    }


    public <E> StandResponse<E> fail() {
        return StandResponseBuilder.result(StandResponse.INTERNAL_SERVER_ERROR,"系统错误");
    }

    public <E> StandResponse<E> fail(Integer code,String message) {
        return StandResponseBuilder.result(code,message);
    }




    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

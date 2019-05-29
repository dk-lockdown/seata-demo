package com.dk.order.worker;

import com.dk.foundation.engine.baseentity.StandResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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


    @ExceptionHandler({ConstraintViolationException.class})
    public @ResponseBody
    StandResponse exception(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        logger.error("#######ERROR#######", e);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST");
        return StandResponseBuilder.result(StandResponse.BUSINESS_EXCEPTION, ((ConstraintViolation) (e.getConstraintViolations().toArray()[0])).getMessage());
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

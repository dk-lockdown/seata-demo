package com.dk.product.worker;

import com.dk.foundation.engine.baseentity.StandResponse;

public class StandResponseBuilder {

    public static <E> StandResponse<E> ok() {
    return result(true,StandResponse.SUCCESS,"SUCCESS",null);
}

    public static <E> StandResponse<E> ok(E data) {
        return result(true,StandResponse.SUCCESS,"SUCCESS", data);
    }

    public static <E> StandResponse<E> result(int code,String msg) {
        return result(code,msg, null);
    }

    public static <E> StandResponse<E> result(Integer code,String msg, E data) {
        StandResponse response = new StandResponse();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static <E> StandResponse<E> result(Boolean success,Integer code,String msg, E data) {
        StandResponse response = new StandResponse();
        response.setSuccess(success);
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }
}
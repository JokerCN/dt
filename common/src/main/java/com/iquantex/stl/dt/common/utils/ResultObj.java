package com.iquantex.stl.dt.common.utils;

import lombok.Data;

@Data
public class ResultObj<T> {

    private int code;
    private String msg;
    private T data;

    public static final int CODE_ERROR = 500;
    public static final int CODE_SUCCESS = 200;

    public static <T> ResultObj<T> error(String msg){
        ResultObj<T> r =  new ResultObj<>();
        r.code = CODE_ERROR;
        r.msg = msg;
        return r;
    }

    public static <T> ResultObj<T> success(T data){
        ResultObj<T> r =  new ResultObj<>();
        r.code = CODE_SUCCESS;
        r.data = data;
        return r;
    }
}

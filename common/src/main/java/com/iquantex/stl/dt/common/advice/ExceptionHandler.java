package com.iquantex.stl.dt.common.advice;

import com.iquantex.stl.dt.common.exceptions.DtException;
import com.iquantex.stl.dt.common.utils.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DtException.class)
    @ResponseBody
    ResultObj<Object> handleNacosTestException(DtException e){
        return ResultObj.error(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    ResultObj<Object> handleConstraintViolationException(ConstraintViolationException e){
        return ResultObj.error(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AssertionError.class)
    @ResponseBody
    ResultObj<Object> handleAssertionError(AssertionError e){
        return ResultObj.error(e.getMessage());
    }
}

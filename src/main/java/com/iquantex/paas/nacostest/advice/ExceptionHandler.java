package com.iquantex.paas.nacostest.advice;

import com.iquantex.paas.nacostest.exceptions.NacosTestException;
import com.iquantex.paas.nacostest.utils.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NacosTestException.class)
    @ResponseBody
    ResultObj<Object> handleNacosTestException(NacosTestException e){
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

package com.iquantex.paas.nacostest.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Validated
@Slf4j
public class TimeUtils {

    public static Long calLatency(@NotNull Date start, @NotNull Date end){
        return end.getTime() - start.getTime();
    }
}

package com.iquantex.paas.nacostest.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@Slf4j
public class SchedulerTest {

    @Test
    @Ignore
    public void trigger() {

        Date date = new Date();

        Scheduler.trigger(date.getTime(), DateUtils.addSeconds(date, 5).getTime(), 200L, () -> {
            log.info("> job triggered.");
            try {
                Thread.sleep(200L);
            } catch (Exception e){
                log.info(e.getMessage());
            }
        });
    }
}
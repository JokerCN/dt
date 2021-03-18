package com.iquantex.stl.dt.common.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Scheduler {

    @SneakyThrows
    public static void trigger(Long startTime, Long endTime, Long interval, Runnable runnable){
        log.info("> Scheduler.trigger: start = {}, endTime = {}, interval = {}", startTime, endTime, interval);
        long waitTime = endTime - new Date().getTime();
        if(waitTime > 0){
            ScheduledExecutorService executor =  Executors.newScheduledThreadPool(3);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    executor.scheduleAtFixedRate(runnable, 0, interval, TimeUnit.MILLISECONDS);
                }
            }, new Date(startTime));

            executor.awaitTermination(waitTime, TimeUnit.MILLISECONDS);
            executor.shutdown();
        }
    }
}

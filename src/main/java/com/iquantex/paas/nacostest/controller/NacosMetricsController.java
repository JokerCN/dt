package com.iquantex.paas.nacostest.controller;


import com.iquantex.paas.nacostest.dao.entity.ListInstanceMetric;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import com.iquantex.paas.nacostest.service.NacosMetricsService;
import com.iquantex.paas.nacostest.utils.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangyiwei
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/nacos")
@Validated
@Slf4j
public class NacosMetricsController {

    private NacosMetricsService nacosMetricsService;

    @Autowired
    public NacosMetricsController(NacosMetricsService nacosMetricsService){
        this.nacosMetricsService = nacosMetricsService;
    }

    @GetMapping("/register_instance_metrics")
    @ResponseBody
    public ResultObj<List<RegisterInstanceMetric>> registerInstanceMetrics(
            @Min(value = 0) @RequestParam @NotNull Long testNo
            ,@Min(value = 0) @RequestParam @NotNull Long startTime
            ,@Min(value = 0) @RequestParam @NotNull Long endTime){
        log.info("> call registerInstanceMetrics: [testNo={}, startTime={}, endTime={}]", testNo, startTime, endTime);
        return ResultObj.success(nacosMetricsService.listRegisterInstanceMetrics(testNo, startTime, endTime));
    }

    @GetMapping("/list_instance_metrics")
    @ResponseBody
    public ResultObj<List<ListInstanceMetric>> listInstanceMetrics(
            @Min(value = 0) @RequestParam @NotNull Long testNo
            ,@Min(value = 0) @RequestParam @NotNull Long startTime
            ,@Min(value = 0) @RequestParam @NotNull Long endTime){
        log.info("> call listInstanceMetrics: [testNo={}, startTime={}, endTime={}]", testNo, startTime, endTime);
        return ResultObj.success(nacosMetricsService.listListInstanceMetrics(testNo, startTime, endTime));
    }

}


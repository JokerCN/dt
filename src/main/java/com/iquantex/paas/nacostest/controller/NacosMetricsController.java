package com.iquantex.paas.nacostest.controller;


import com.iquantex.paas.nacostest.dao.entity.ListInstanceMetric;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import com.iquantex.paas.nacostest.service.NacosMetricsService;
import com.iquantex.paas.nacostest.utils.ResultObj;
import com.iquantex.paas.nacostest.utils.SerializeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yangyiwei
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/nacos")
@Validated
@Slf4j
@ResponseBody
public class NacosMetricsController {

    private NacosMetricsService nacosMetricsService;

    @Autowired
    public NacosMetricsController(NacosMetricsService nacosMetricsService){
        this.nacosMetricsService = nacosMetricsService;
    }

    @GetMapping("/register_instance_metrics")
    public ResultObj<List<RegisterInstanceMetric>> registerInstanceMetrics(
            @Min(value = 0) @RequestParam @NotNull Long testNo
            ,@Min(value = 0) @RequestParam @NotNull Long startTime
            ,@Min(value = 0) @RequestParam @NotNull Long endTime){
        log.info("> call registerInstanceMetrics: [testNo={}, startTime={}, endTime={}]", testNo, startTime, endTime);
        return ResultObj.success(nacosMetricsService.listRegisterInstanceMetrics(testNo, startTime, endTime));
    }

    @GetMapping("/list_instance_metrics")
    public ResultObj<List<ListInstanceMetric>> listInstanceMetrics(
            @Min(value = 0) @RequestParam @NotNull Long testNo
            ,@Min(value = 0) @RequestParam @NotNull Long startTime
            ,@Min(value = 0) @RequestParam @NotNull Long endTime){
        log.info("> call listInstanceMetrics: [testNo={}, startTime={}, endTime={}]", testNo, startTime, endTime);
        return ResultObj.success(nacosMetricsService.listListInstanceMetrics(testNo, startTime, endTime));
    }

    @PostMapping(value = "/batch_insert_register_instance_metrics", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultObj<Integer> batchInsertRegisterInstanceMetricEntry(
            @RequestBody @NotNull List<@Valid RegisterInstanceMetric> metrics){
        log.info("> call batchInsertRegisterInstanceMetricEntry: {}", SerializeUtils.toJson(metrics));
        return CollectionUtils.isEmpty(metrics)
                ? ResultObj.success(0)
                : ResultObj.success(nacosMetricsService.batchInsertRegisterInstanceMetrics(metrics));
    }

    @PostMapping(value = "/batch_insert_list_instance_metrics", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultObj<Integer> batchInsertListInstanceMetricEntry(
            @RequestBody @NotNull List<@Valid ListInstanceMetric> metrics){
        log.info("> call batchInsertListInstanceMetricEntry: {}", SerializeUtils.toJson(metrics));
        return CollectionUtils.isEmpty(metrics)
                ? ResultObj.success(0)
                : ResultObj.success(nacosMetricsService.batchInsertListInstanceMetrics(metrics));
    }

}


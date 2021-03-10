package com.iquantex.paas.nacostest.controller;

import com.iquantex.paas.nacostest.service.StrategyService;
import com.iquantex.paas.nacostest.utils.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@ResponseBody
@RequestMapping("/strategy")
@Validated
@Slf4j
public class StrategyController {

    private StrategyService strategyService;

    @Autowired
    public StrategyController(StrategyService strategyService){
        this.strategyService = strategyService;
    }

    @GetMapping("/trigger_strategy")
    public ResultObj<Boolean> triggerStrategy(
            @Min(1)
            @NotNull
            @RequestParam Long testNo,
            @Min(10)
            @NotNull
            @RequestParam Long interval,
            @NotBlank
            String serviceName,
            @NotBlank
            String instanceIp,
            @Min(8080)
            @NotNull
            Integer instancePort){
        log.info("> triggerStrategy: testNo = {}, interval = {}", testNo, interval);
        strategyService.triggerStrategy(testNo, interval, serviceName, instanceIp, instancePort);
        return ResultObj.success(true);
    }

}

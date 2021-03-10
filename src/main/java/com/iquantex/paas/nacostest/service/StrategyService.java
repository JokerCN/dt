package com.iquantex.paas.nacostest.service;

import com.iquantex.paas.nacostest.service.strategy.APStrategy;
import com.iquantex.paas.nacostest.service.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StrategyService {

    private Strategy apStrategy;

    @Autowired
    public StrategyService(APStrategy apStrategy){
        this.apStrategy = apStrategy;
    }

    public void triggerStrategy(Long testNo, Long interval, String serviceName, String instanceIp, Integer instancePort){
        apStrategy.run(testNo, interval, serviceName, instanceIp, instancePort);
    }
}

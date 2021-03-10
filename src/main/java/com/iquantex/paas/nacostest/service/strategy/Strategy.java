package com.iquantex.paas.nacostest.service.strategy;

public interface Strategy {

    void run(Long testNo, Long interval, String serviceName, String instanceIp, Integer instancePort);
}

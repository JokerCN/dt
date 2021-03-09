package com.iquantex.paas.nacostest.service.strategy;

import com.iquantex.paas.nacostest.dao.mapper.ListInstanceMetricMapper;
import com.iquantex.paas.nacostest.dao.mapper.RegisterInstanceMetricMapper;
import com.iquantex.paas.nacostest.service.NacosMetricsService;
import com.iquantex.paas.nacostest.service.strategy.manager.IpAddressManager;
import com.iquantex.paas.nacostest.utils.Request;
import com.iquantex.paas.nacostest.utils.RequestSender;
import com.iquantex.paas.nacostest.utils.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@Slf4j
public class APStrategyTest {

    @MockBean
    RegisterInstanceMetricMapper registerInstanceMetricMapper;

    @MockBean
    ListInstanceMetricMapper listInstanceMetricMapper;

    @Autowired
    APStrategy apStrategy;

    @Autowired
    RequestSender requestSender;

    @Test
    @SneakyThrows
    @Ignore
    public void makeRegisterInstanceRequest() {
        Request request = apStrategy.makeRegisterInstanceRequest("127.0.0.1");
        Response response = requestSender.sendRequest(request).get();
        log.info("> register instance :{}", response.getResponseEntity().getBody());
    }

    @Test
    @SneakyThrows
    @Ignore
    public void makeListInstanceRequest() {
        Request request = apStrategy.makeListInstanceRequest("127.0.0.1");
        Response response = requestSender.sendRequest(request).get();
        log.info("> list instance : {}", response.getResponseEntity().getBody());
    }

    @Configuration
    @Import({APStrategy.class, IpAddressManager.class, RequestSender.class})
    static class Config{

    }

    @Test
    @Ignore
    public void run() {
        given(registerInstanceMetricMapper.insertBatchRegisterInstanceMetrics(any())).willReturn(1);
        given(listInstanceMetricMapper.insertBatchListInstanceMetrics(any())).willReturn(10);

        apStrategy.run(1L, 200L);

        verify(registerInstanceMetricMapper).insertBatchRegisterInstanceMetrics(any());
        verify(listInstanceMetricMapper).insertBatchListInstanceMetrics(any());
    }
}
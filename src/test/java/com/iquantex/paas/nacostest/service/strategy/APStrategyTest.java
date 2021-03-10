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
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Test
    public void testExist() {
        String body = "{\"hosts\":[{\"ip\":\"200.200.200.200\",\"port\":8080,\"valid\":true,\"healthy\":true,\"marked\":false,\"instanceId\":\"20.18.7.10#8080#DEFAULT#DEFAULT_GROUP@@nacos.naming.serviceName\",\"metadata\":{},\"enabled\":true,\"weight\":1.0,\"clusterName\":\"DEFAULT\",\"serviceName\":\"nacos.naming.serviceName\",\"ephemeral\":true}],\"dom\":\"nacos.naming.serviceName\",\"name\":\"DEFAULT_GROUP@@nacos.naming.serviceName\",\"cacheMillis\":3000,\"lastRefTime\":1615342792626,\"checksum\":\"0f579c87395fb94ff691d287b4d67cee\",\"useSpecifiedURL\":false,\"clusters\":\"\",\"env\":\"\",\"metadata\":{}}";
        Response response = new Response(new ResponseEntity<>(body, HttpStatus.OK));
        Assert.assertTrue(apStrategy.testExist(response));

        String bodyFalse = "{\"hosts\":[{\"ip\":\"200.200.200.201\",\"port\":8080,\"valid\":true,\"healthy\":true,\"marked\":false,\"instanceId\":\"20.18.7.10#8080#DEFAULT#DEFAULT_GROUP@@nacos.naming.serviceName\",\"metadata\":{},\"enabled\":true,\"weight\":1.0,\"clusterName\":\"DEFAULT\",\"serviceName\":\"nacos.naming.serviceName\",\"ephemeral\":true}],\"dom\":\"nacos.naming.serviceName\",\"name\":\"DEFAULT_GROUP@@nacos.naming.serviceName\",\"cacheMillis\":3000,\"lastRefTime\":1615342792626,\"checksum\":\"0f579c87395fb94ff691d287b4d67cee\",\"useSpecifiedURL\":false,\"clusters\":\"\",\"env\":\"\",\"metadata\":{}}";
        response.setResponseEntity(new ResponseEntity<>(bodyFalse, HttpStatus.OK));
        Assert.assertFalse(apStrategy.testExist(response));

        String bodyFalse2 = "{\"name\":\"DEFAULT_GROUP@@nacos.naming.serviceName1\",\"clusters\":\"\",\"cacheMillis\":3000,\"hosts\":[]}";
        response.setResponseEntity(new ResponseEntity<>(bodyFalse2, HttpStatus.OK));
        Assert.assertFalse(apStrategy.testExist(response));
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
package com.iquantex.paas.nacostest.service;

import com.iquantex.paas.nacostest.dao.mapper.ListInstanceMetricMapper;
import com.iquantex.paas.nacostest.dao.mapper.RegisterInstanceMetricMapper;
import com.iquantex.paas.nacostest.data.ListInstanceMetricData;
import com.iquantex.paas.nacostest.data.RegisterInstanceMetricData;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@Slf4j
public class NacosMetricsServiceTest {

    @MockBean
    RegisterInstanceMetricMapper registerInstanceMetricMapper;

    @MockBean
    ListInstanceMetricMapper listInstanceMetricMapper;

    @Autowired
    NacosMetricsService nacosMetricsService;

    @Test
    public void listListInstanceMetricsTest() {
        log.info("> listListInstanceMetricsTest");
        var mockData = ListInstanceMetricData.mockData(10, Function.identity());
        given(listInstanceMetricMapper.listListInstanceMetrics(any(), any(), any()))
                .willReturn(mockData);

        var results = nacosMetricsService.listListInstanceMetrics(1L, 1L, 1L);
        verify(listInstanceMetricMapper).listListInstanceMetrics(any(), any(), any());
        Assert.assertEquals(10, results.size());
    }

    @Test
    public void batchInsertListInstanceMetricsTest() {
        log.info("> batchInsertListInstanceMetrics");
        given(listInstanceMetricMapper.insertBatchListInstanceMetrics(any()))
                .willReturn(10);

        Integer result = nacosMetricsService.batchInsertListInstanceMetrics(ListInstanceMetricData.mockData(10, Function.identity()));

        verify(listInstanceMetricMapper).insertBatchListInstanceMetrics(any());
        Assert.assertEquals(10, (int) result);
    }

    @Configuration
    @Import(NacosMetricsService.class)
    static class Config{

    }

    @Test
    public void listRegisterInstanceMetricsTest() {
        log.info("> listRegisterInstanceMetricsTest");
        given(registerInstanceMetricMapper.listRegisterInstanceMetrics(anyLong(), any(), any()))
                .willReturn(RegisterInstanceMetricData.mockData(10, Function.identity()));

        var results = nacosMetricsService.listRegisterInstanceMetrics(1L, 1L, 1L);

        verify(registerInstanceMetricMapper).listRegisterInstanceMetrics(anyLong(), any(), any());
        Assert.assertEquals(10, results.size());
        Assert.assertTrue(results.get(0).getSequence() < results.get(1).getSequence());
    }
}
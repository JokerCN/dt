package com.iquantex.paas.nacostest.service;

import com.iquantex.paas.nacostest.dao.entity.ListInstanceMetric;
import com.iquantex.paas.nacostest.dao.entity.Metric;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import com.iquantex.paas.nacostest.dao.mapper.ListInstanceMetricMapper;
import com.iquantex.paas.nacostest.dao.mapper.RegisterInstanceMetricMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NacosMetricsService {

    private RegisterInstanceMetricMapper registerInstanceMetricMapper;
    private ListInstanceMetricMapper listInstanceMetricMapper;

    @Autowired
    public NacosMetricsService(RegisterInstanceMetricMapper registerInstanceMetricMapper
            , ListInstanceMetricMapper listInstanceMetricMapper){
        this.registerInstanceMetricMapper = registerInstanceMetricMapper;
        this.listInstanceMetricMapper = listInstanceMetricMapper;
    }

    @Transactional(readOnly = true)
    public List<RegisterInstanceMetric> listRegisterInstanceMetrics(Long testNo, Long startTime, Long endTime){
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        return registerInstanceMetricMapper.listRegisterInstanceMetrics(testNo, startDate, endDate)
                .stream()
                .sorted(Comparator.comparing(Metric::getSequence))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ListInstanceMetric> listListInstanceMetrics(Long testNo, Long startTime, Long endTime){
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        return listInstanceMetricMapper.listListInstanceMetrics(testNo, startDate, endDate)
                .stream()
                .sorted(Comparator.comparing(Metric::getSequence))
                .collect(Collectors.toList());
    }

    @Transactional
    //TODO: add test
    public Integer batchInsertRegisterInstanceMetrics(List<RegisterInstanceMetric> metrics){
        return registerInstanceMetricMapper.insertBatchRegisterInstanceMetrics(metrics);
    }

    @Transactional
    public Integer batchInsertListInstanceMetrics(List<ListInstanceMetric> metrics){
        return listInstanceMetricMapper.insertBatchListInstanceMetrics(metrics);
    }


}

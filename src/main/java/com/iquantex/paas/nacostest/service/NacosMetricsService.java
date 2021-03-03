package com.iquantex.paas.nacostest.service;

import com.iquantex.paas.nacostest.dao.entity.ListInstanceMetric;
import com.iquantex.paas.nacostest.dao.entity.Metric;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
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

    @Autowired
    public NacosMetricsService(RegisterInstanceMetricMapper registerInstanceMetricMapper){
        this.registerInstanceMetricMapper = registerInstanceMetricMapper;
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
        throw new UnsupportedOperationException();
    }
}

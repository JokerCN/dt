package com.iquantex.paas.nacostest.utils;

import com.github.jsonzou.jmockdata.JMockData;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class SerializeUtilsTest {

    @Test
    public void toJson() {
        RegisterInstanceMetric metric = JMockData.mock(RegisterInstanceMetric.class);
        log.info("> {}", SerializeUtils.toJson(metric));
    }

    @Test
    public void fromJson() {
        String source = "{\"id\":1596,\"testNo\":7215,\"clientId\":\"7NhxljLC\",\"sequence\":5805,\"request\":\"aB5W6nDU\",\"response\":\"mGshsX\",\"latency\":3538,\"" +
                "startTime\":\"2013-04-06 21:02:38\",\"endTime\":\"2030-04-23 05:33:12\",\"success\":true}";
        log.info("> {}", source);
        RegisterInstanceMetric metric = SerializeUtils.fromJson(source, RegisterInstanceMetric.class);
        log.info("> {}", metric.toString());
    }
}
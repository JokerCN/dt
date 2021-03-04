package com.iquantex.paas.nacostest.dao.mapper;

import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.MockConfig;
import com.iquantex.paas.nacostest.dao.entity.Metric;
import com.iquantex.paas.nacostest.data.ListInstanceMetricData;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import static com.iquantex.paas.nacostest.data.ListInstanceMetricData.*;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ListInstanceMetricMapperTest {

    @Autowired
    ListInstanceMetricMapper mapper;

    @Test
    public void insertBatchListInstanceMetrics() {
        truncateTest();
        AtomicLong idGenerator = new AtomicLong(1);

        Long testNo = JMockData.mock(Long.class);
        Date tempTime = JMockData.mock(Date.class);
        tempTime = DateUtils.setHours(tempTime, 2);
        tempTime = DateUtils.setMinutes(tempTime, 5);
        Date startTime = DateUtils.setSeconds(tempTime, 10);

        tempTime = DateUtils.addHours(startTime, 3);
        tempTime = DateUtils.addMinutes(tempTime, 20);
        Date endTime = DateUtils.addSeconds(tempTime, 30);

        MockConfig mockConfig = new MockConfig()
                .subConfig("startTime", "endTime")
                .dateRange(DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm:ss")
                        , DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss"))
                .globalConfig();

        var sources = mockData(30, instance -> {
            instance.setId(idGenerator.getAndIncrement());
            instance.setTestNo(testNo);
            return instance;
        }, mockConfig);

        var count = mapper.insertBatchListInstanceMetrics(sources);
        var results = mapper.listListInstanceMetrics(testNo, startTime, endTime).stream().sorted(Comparator.comparing(Metric::getId));

        Assert.assertEquals(30, (int) count);

        Assert.assertTrue(
                Stream.ofAll(sources)
                        .zip(Stream.ofAll(results))
                        .toJavaList()
                        .stream()
                        .allMatch(tuple -> {
                            var t1 = tuple._1;
                            var t2 = tuple._2;
                            return Objects.equals(t1.getId(), t2.getId())
                                    && Objects.equals(t1.getClientId(), t2.getClientId())
                                    && Objects.equals(t1.getTestNo(), t2.getTestNo())
                                    && Objects.equals(t1.getSequence(), t2.getSequence())
                                    && Objects.equals(t1.getRequest(), t2.getRequest())
                                    && Objects.equals(t1.getResponse(), t2.getResponse())
                                    && Objects.equals(t1.getLatency(), t2.getLatency())
//                                    && Objects.equals(t1.getStartTime(), t2.getStartTime()) // DateTime 存储Date对象会有精度损失
//                                    && Objects.equals(t1.getEndTime(), t2.getEndTime())// DateTime 存储Date对象会有精度损失
                                    && Objects.equals(t1.getTargetService(), t2.getTargetService())
                                    && Objects.equals(t1.getExist(), t2.getExist());
                        }));
        truncateTest();
    }

    @Test
    public void truncateTest(){
        mapper.truncate();
    }
}
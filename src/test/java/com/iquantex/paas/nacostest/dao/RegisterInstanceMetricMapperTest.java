package com.iquantex.paas.nacostest.dao;

import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.MockConfig;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import com.iquantex.paas.nacostest.dao.mapper.RegisterInstanceMetricMapper;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RegisterInstanceMetricMapperTest {

    @Autowired
    private RegisterInstanceMetricMapper mapper;

    @Autowired
    private DataSource dataSource;

    List<RegisterInstanceMetric> metrics;

    @Test
    @Ignore
    public void testConnection(){
        try {
            log.info("> test to connect to db.");
            dataSource.getConnection().isValid(1);
            log.info("> connection established.");
        }catch (SQLException exception){
            log.error("> connect to db failed, {}", exception.getMessage());
        }
    }

    @Test
    @Ignore
    public void testInsertBatchRegisterInstanceMetrics(){
        log.info("> testInsertBatchRegisterInstanceMetrics");
        MockConfig mockConfig = new MockConfig().stringRegex("[a-z0-9A-Z]{5, 200}");
        metrics = Stream.range(0, 10).map(i -> JMockData.mock(RegisterInstanceMetric.class)).toJavaList();
        mapper.insertBatchRegisterInstanceMetrics(metrics);
    }

    @Test
    @Ignore
    public void testSelect(){
        log.info("> testSelect");
        RegisterInstanceMetric metric = mapper.selectRegisterInstanceMetric(1L);
        Assert.assertNotNull(metric);
    }

    @Test
    @Ignore
    public void testTruncate(){
        log.info("> testTruncate");
        mapper.truncate();
    }

    @Test
    public void integrationTest(){
        log.info("> integrationTest");
        testConnection();
        testTruncate();
        testInsertBatchRegisterInstanceMetrics();
        testSelect();
        testTruncate();
    }

    @Test
    public void testListRegisterInstanceMetrics(){
        log.info("> testListRegisterInstanceMetrics");
        Long testNo = JMockData.mock(Long.class);
        Date tempTime = JMockData.mock(Date.class);
        tempTime = DateUtils.setHours(tempTime, 2);
        tempTime = DateUtils.setMinutes(tempTime, 5);
        Date startTime = DateUtils.setSeconds(tempTime, 10);

        tempTime = DateUtils.addHours(startTime, 3);
        tempTime = DateUtils.addMinutes(tempTime, 20);
        Date endTime = DateUtils.addSeconds(tempTime, 30);

        testTruncate();
        // insertion
        List<RegisterInstanceMetric> sources = insertRecordsForListRegisterInstanceMetrics(testNo, startTime, endTime);

        // query records having been inserted
        List<RegisterInstanceMetric> metrics = mapper.listRegisterInstanceMetrics(testNo, startTime, endTime);
        Assert.assertEquals(10, metrics.size());
        Predicate<RegisterInstanceMetric> predicate = metric -> {
            return startTime.compareTo(metric.getStartTime()) <= 0
                    && endTime.compareTo(metric.getEndTime()) >= 0
                    && Objects.equals(metric.getTestNo(), testNo);
        };
        Assert.assertTrue(metrics.stream().allMatch(predicate));

        testTruncate();
    }

    private List<RegisterInstanceMetric> insertRecordsForListRegisterInstanceMetrics(Long testNo, Date startTime, Date endTime){
        log.info("> mock startTime and endTime");
        MockConfig mockConfig = new MockConfig()
                .subConfig("startTime", "endTime")
                .dateRange(DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm:ss")
                        , DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss"))
                .globalConfig();

        metrics = Stream.range(0, 10).map(i -> {
            RegisterInstanceMetric metric = JMockData.mock(RegisterInstanceMetric.class, mockConfig);
            metric.setTestNo(testNo);
            return metric;
        }).toJavaList();
        mapper.insertBatchRegisterInstanceMetrics(metrics);
        return metrics;
    }

}

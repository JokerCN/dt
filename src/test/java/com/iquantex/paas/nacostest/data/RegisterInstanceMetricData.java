package com.iquantex.paas.nacostest.data;

import com.github.jsonzou.jmockdata.JMockData;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import io.vavr.collection.Stream;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Function;

@Validated
public class RegisterInstanceMetricData {

    public static List<RegisterInstanceMetric> mockData(@Min(1) int count, @NotNull  Function<RegisterInstanceMetric, RegisterInstanceMetric> handler){
        return Stream.range(0, count).map(i -> handler.apply(JMockData.mock(RegisterInstanceMetric.class))).toJavaList();
    }
}

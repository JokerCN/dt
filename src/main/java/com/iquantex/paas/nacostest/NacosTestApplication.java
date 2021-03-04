package com.iquantex.paas.nacostest;

import com.iquantex.paas.nacostest.dao.mapper.RegisterInstanceMetricMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackageClasses = RegisterInstanceMetricMapper.class)
public class NacosTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosTestApplication.class, args);
    }

}

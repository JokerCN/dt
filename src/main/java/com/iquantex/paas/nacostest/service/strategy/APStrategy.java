package com.iquantex.paas.nacostest.service.strategy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iquantex.paas.nacostest.dao.entity.ListInstanceMetric;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import com.iquantex.paas.nacostest.dao.mapper.ListInstanceMetricMapper;
import com.iquantex.paas.nacostest.dao.mapper.RegisterInstanceMetricMapper;
import com.iquantex.paas.nacostest.exceptions.NacosTestException;
import com.iquantex.paas.nacostest.service.strategy.manager.IpAddressManager;
import com.iquantex.paas.nacostest.utils.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class APStrategy implements Strategy{

    private RegisterInstanceMetricMapper registerInstanceMetricMapper;
    private ListInstanceMetricMapper listInstanceMetricMapper;
    private IpAddressManager ipAddressManager;
    private RequestSender requestSender;

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Autowired
    public APStrategy(RegisterInstanceMetricMapper registerInstanceMetricMapper,
                      ListInstanceMetricMapper listInstanceMetricMapper,
                      IpAddressManager ipAddressManager,
                      RequestSender requestSender
    ){
        this.registerInstanceMetricMapper = registerInstanceMetricMapper;
        this.listInstanceMetricMapper = listInstanceMetricMapper;
        this.ipAddressManager = ipAddressManager;
        this.requestSender = requestSender;
    }

    @Override
    @SneakyThrows
    public void run(Long testNo, Long interval) {
        log.info("> APStrategy.run: testNo={}, interval={}", testNo, interval);
        List<String> ipList = ipAddressManager.listIpAddress();
        String leader = chooseLeader(ipList);
        Request request = makeRegisterInstanceRequest(leader);
        List<Request> requests = makeListInstanceRequest(ipList);
        List<ListInstanceMetric> listInstanceMetrics = Lists.newArrayList();
        List<RegisterInstanceMetric> registerInstanceMetrics = Lists.newArrayList();

        requests.stream()
                .peek(req -> {
                    executorService.submit(() -> {
                        Date now = new Date();
                        AtomicLong seq = new AtomicLong(1);
                        Scheduler.trigger(now.getTime(), DateUtils.addSeconds(now, 10).getTime(), interval, () -> {
                            Date start = new Date();
                            requestSender.sendRequest(req)
                                    .thenAccept((resp) -> {
                                        Date end = new Date();
                                        Long latency = TimeUtils.calLatency(start, end);
                                        Boolean exist = testExist(resp);
                                        listInstanceMetrics.add(new ListInstanceMetric(
                                                1L,
                                                testNo,
                                                "client",
                                                seq.getAndIncrement(),
                                                req.toString(),
                                                resp.toString(),
                                                latency,
                                                start,
                                                end,
                                                req.getIpAddress(),
                                                exist
                                        ));
                                    });
                        });
                    });
                }).collect(Collectors.toList());

        Date start = new Date();
        requestSender.sendRequest(request)
                .thenAccept(resp -> {
                    Date end = new Date();
                    Long latency = TimeUtils.calLatency(start, end);
                    registerInstanceMetrics.add(new RegisterInstanceMetric(
                            1L,
                            testNo,
                            "client",
                            1L,
                            request.toString(),
                            resp.toString(),
                            latency,
                            start,
                            end,
                            testSuccess(resp)
                    ));
                });
        Thread.sleep(20_000);
        registerInstanceMetricMapper.insertBatchRegisterInstanceMetrics(registerInstanceMetrics);
        listInstanceMetricMapper.insertBatchListInstanceMetrics(listInstanceMetrics);
    }

    String chooseLeader(List<String> ipList){
        log.info("> choose leader from ip list: {}", ipList);
        if(CollectionUtils.isEmpty(ipList))
            throw new NacosTestException("empty ip list");
        else
            return ipList.get(0);
    }

    Request makeRegisterInstanceRequest(String ip){
        log.info("> makeRegisterInstanceRequest: ip = {}", ip);
        Request request = new Request();
        request.setMethod(Request.Method.POST);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("serviceName", "test-ap-strategy");
        map.add("ip", "200.200.200.200");
        map.add("port", "8080");
        HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map, headers);
        request.setHttpEntity(req);
        request.setIpAddress(ip);
        request.setPort(8848);
        request.setPath("/nacos/v1/ns/instance");
        return request;
    }

    Request makeListInstanceRequest(String ip){
        log.info("> makeListInstanceRequest: ip = {}", ip);
        Request request = new Request();
        request.setMethod(Request.Method.GET);
        request.setPath("/nacos/v1/ns/instance/list?serviceName={serviceName}");
        request.setIpAddress(ip);
        request.setPort(8848);
        Map<String, String> variables = Maps.newHashMap();
        variables.put("serviceName", "test-ap-strategy");
        request.setVariables(variables);
        return request;
    }

    List<Request> makeListInstanceRequest(List<String> ipList){
        log.info("> makeListInstanceRequest: ipList = {}", ipList);
        return ipList.stream()
                .map(this::makeListInstanceRequest)
                .collect(Collectors.toList());
    }

    Boolean testExist(Response resp){
        log.info("> testExist: resp = {}", resp);
        return Objects.equals(resp.getResponseEntity().getBody(), "ok");
    }

    //TODO: implementation
    Boolean testSuccess(Response resp){
        log.info("> testSuccess: resp = {}", resp);
        return true;
    }

}

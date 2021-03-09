package com.iquantex.paas.nacostest.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Configuration
public class RequestSender {

    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        this.restTemplate = new RestTemplate();
        return this.restTemplate;
    }

    public ResponseEntity<String> sendRequest1(Request request){
        log.info("> sendRequest: req = {}", request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("serviceName", "abcdefg");
        map.add("ip", "20.18.7.10");
        map.add("port", "8080");
        HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("http://127.0.0.1:8848/nacos/v1/ns/instance"
                        , req, String.class);
        log.info("> {}", responseEntity.getBody());

        Map<String, String> hmap = Maps.newHashMap();
        hmap.put("serviceName", "abcdefg");
        responseEntity = restTemplate.getForEntity("http://127.0.0.1:8848/nacos/v1/ns/instance/list?serviceName={serviceName}"
                , String.class, hmap);

        log.info("> {}", responseEntity.getBody());

        return responseEntity;
    }

    public CompletableFuture<Response> sendRequest(Request request){
        log.info("> sendRequest: req = {}", request);
        return CompletableFuture.supplyAsync(() -> {
            ResponseEntity<String> responseEntity = null;
            switch (request.getMethod()){
                case GET:
                    responseEntity = restTemplate.getForEntity(request.getUrl(), String.class, request.getVariables());
                    break;
                case POST:
                    responseEntity = restTemplate.postForEntity(request.getUrl(), request.getHttpEntity(), String.class);
                    break;
            }
            return new Response(responseEntity);
        });
    }
}

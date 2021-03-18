package com.iquantex.stl.dt.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Configuration
public class RequestSender {

    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        this.restTemplate = new RestTemplate();
        return this.restTemplate;
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

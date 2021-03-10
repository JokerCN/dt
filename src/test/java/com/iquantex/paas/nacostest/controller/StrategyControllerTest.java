package com.iquantex.paas.nacostest.controller;

import com.iquantex.paas.nacostest.service.StrategyService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class StrategyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StrategyService strategyService;

    @Test
    @SneakyThrows
    public void triggerStrategy() {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/strategy/trigger_strategy")
                .param("testNo", "1")
                .param("interval", "100")
                .param("serviceName", "test-nacos-register")
                .param("instanceIp", "210.211.232.222")
                .param("instancePort", "9090")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        jsonPath("$.data").value(true)
                ));

        verify(strategyService).triggerStrategy(any(), any(), any(), any(), any());
    }
}
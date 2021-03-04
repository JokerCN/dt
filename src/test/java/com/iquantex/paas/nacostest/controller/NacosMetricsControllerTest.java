package com.iquantex.paas.nacostest.controller;

import com.iquantex.paas.nacostest.dao.entity.ListInstanceMetric;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import com.iquantex.paas.nacostest.data.ListInstanceMetricData;
import com.iquantex.paas.nacostest.data.RegisterInstanceMetricData;
import com.iquantex.paas.nacostest.service.NacosMetricsService;
import com.iquantex.paas.nacostest.utils.SerializeUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class NacosMetricsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NacosMetricsService nacosMetricsService;

    @SneakyThrows
    @Test
    public void registerInstanceMetricsTest() {
        given(nacosMetricsService.listRegisterInstanceMetrics(any(), any(), any()))
                .willReturn(RegisterInstanceMetricData.mockData(10, Function.identity()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/register_instance_metrics")
                        .param("testNo", "1")
                        .param("startTime", "1")
                        .param("endTime", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , content().contentType(MediaType.APPLICATION_JSON)
                        , jsonPath("$.code").value(200)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/register_instance_metrics")
                        .param("testNo", "-1")
                        .param("startTime", "1")
                        .param("endTime", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , jsonPath("$.code").value(500)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/register_instance_metrics")
                        .param("testNo", "1")
                        .param("startTime", "-1")
                        .param("endTime", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , jsonPath("$.code").value(500)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/register_instance_metrics")
                        .param("testNo", "1")
                        .param("startTime", "1")
                        .param("endTime", "-1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , jsonPath("$.code").value(500)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/register_instance_metrics")
                        .param("testNo", "1")
                        .param("startTime", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().is4xxClientError()
                ));
    }

    @SneakyThrows
    @Test
    public void ListInstanceMetricsTest(){
        given(nacosMetricsService.listListInstanceMetrics(any(), any(), any()))
                .willReturn(ListInstanceMetricData.mockData(10, Function.identity()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/list_instance_metrics")
                        .param("testNo", "1")
                        .param("startTime", "1")
                        .param("endTime", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , content().contentType(MediaType.APPLICATION_JSON)
                        , jsonPath("$.code").value(200)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/list_instance_metrics")
                        .param("testNo", "-1")
                        .param("startTime", "1")
                        .param("endTime", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , jsonPath("$.code").value(500)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/list_instance_metrics")
                        .param("testNo", "1")
                        .param("startTime", "-1")
                        .param("endTime", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , jsonPath("$.code").value(500)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/list_instance_metrics")
                        .param("testNo", "1")
                        .param("startTime", "1")
                        .param("endTime", "-1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , jsonPath("$.code").value(500)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/nacos/list_instance_metrics")
                        .param("testNo", "1")
                        .param("startTime", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().is4xxClientError()
                ));
    }

    @Test
    @SneakyThrows
    public void batchInsertRegisterInstanceMetricEntry() {
        given(nacosMetricsService.batchInsertRegisterInstanceMetrics(any())).willReturn(10);

        List<RegisterInstanceMetric> metrics = RegisterInstanceMetricData.mockData(10, Function.identity());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/nacos/batch_insert_register_instance_metrics")
                        .content(SerializeUtils.toJson(metrics))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                ));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/nacos/batch_insert_register_instance_metrics")
                        .content("[]")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        jsonPath("$.data").value(0)
                ));
    }

    @Test
    @SneakyThrows
    public void batchInsertListInstanceMetricEntry() {
        given(nacosMetricsService.batchInsertListInstanceMetrics(any())).willReturn(20);

        List<ListInstanceMetric> metrics = ListInstanceMetricData.mockData(20, Function.identity());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/nacos/batch_insert_list_instance_metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializeUtils.toJson(metrics)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        jsonPath("$.data").value(20)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/nacos/batch_insert_list_instance_metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        jsonPath("$.data").value(0)
                ));

    }
}
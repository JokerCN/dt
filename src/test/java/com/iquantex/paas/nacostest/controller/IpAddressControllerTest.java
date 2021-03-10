package com.iquantex.paas.nacostest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.iquantex.paas.nacostest.service.strategy.manager.IpAddressManager;
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
import org.springframework.validation.annotation.Validated;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@AutoConfigureMockMvc
public class IpAddressControllerTest {

    @MockBean
    IpAddressManager ipAddressManager;

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void updateIpAddressPool() {
        ArrayNode arrayNode = SerializeUtils.createEmptyArrayNode();
        arrayNode.add("200.200.200.200")
                .add("200.200.200.201")
                .add("200.200.200.202");

        log.info(arrayNode.toString());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/ip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(arrayNode.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , content().contentType(MediaType.APPLICATION_JSON)
                        , jsonPath("$.code").value(200)
                ));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/ip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , content().contentType(MediaType.APPLICATION_JSON)
                        , jsonPath("$.code").value(500)
                ));

        ArrayNode arrayNode2 = SerializeUtils.createEmptyArrayNode();
        arrayNode2.add("200.200.200.200")
                .add("555.555.200.201");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/ip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(arrayNode2.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , content().contentType(MediaType.APPLICATION_JSON)
                        , jsonPath("$.code").value(500)
                ));

        ArrayNode arrayNode3 = SerializeUtils.createEmptyArrayNode();
        arrayNode3.add("abc.efd.200.200")
                .add("192.167.200.201");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/ip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(arrayNode3.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , content().contentType(MediaType.APPLICATION_JSON)
                        , jsonPath("$.code").value(500)
                ));
    }

    @Test
    @SneakyThrows
    public void listIpAddress() {
        given(ipAddressManager.listIpAddress())
                .willReturn(Lists.newArrayList("127.0.0.1", "222.222.222.200"));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/ip")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk()
                        , content().contentType(MediaType.APPLICATION_JSON)
                        , jsonPath("$.code").value(200)
                ));

        verify(ipAddressManager).listIpAddress();
    }
}
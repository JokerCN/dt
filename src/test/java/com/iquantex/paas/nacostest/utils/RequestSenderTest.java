package com.iquantex.paas.nacostest.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RequestSenderTest {

    @Autowired
    RequestSender requestSender;

    @Test
    @Ignore
    public void sendRequest1() {
        requestSender.sendRequest1(new Request());
    }
}
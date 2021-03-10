package com.iquantex.paas.nacostest.service.strategy.manager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class IpAddressManagerTest {

    @Autowired
    IpAddressManager ipAddressManager;

    @Test
    public void updateIpAddressPool() {
        List<String> list = Lists.newArrayList("192.168.1.1", "192.168.1.2");
        ipAddressManager.updateIpAddressPool(list);
        Assert.assertTrue(CollectionUtils.isEqualCollection(ipAddressManager.listIpAddress(), list));
    }
}
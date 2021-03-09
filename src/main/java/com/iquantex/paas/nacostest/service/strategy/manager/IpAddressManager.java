package com.iquantex.paas.nacostest.service.strategy.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class IpAddressManager {

    private List<String> ipList = new ArrayList<String>(){
        {
            this.add("10.233.70.203");
            this.add("10.233.95.128");
            this.add("10.233.100.153");
        }
    };

    public List<String> listIpAddress(){
        return ipList;
    }
}

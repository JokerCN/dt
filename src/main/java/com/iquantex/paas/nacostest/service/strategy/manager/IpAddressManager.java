package com.iquantex.paas.nacostest.service.strategy.manager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class IpAddressManager {

    private final List<String> ipList = new ArrayList<String>(){
        {
            this.add("10.233.70.203");
            this.add("10.233.95.128");
            this.add("10.233.100.153");
//            this.add("127.0.0.1");
        }
    };

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public List<String> listIpAddress(){
        Lock lock = readWriteLock.readLock();
        lock.lock();
        try {
            return Lists.newArrayList(ipList);
        }finally {
            lock.unlock();
        }
    }

    public void updateIpAddressPool(List<String> ipList){
        Lock lock = readWriteLock.writeLock();
        lock.lock();
        try {
            this.ipList.clear();
            this.ipList.addAll(ipList);
        } finally {
            lock.unlock();
        }
    }
}

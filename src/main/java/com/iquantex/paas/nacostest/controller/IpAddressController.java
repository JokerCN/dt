package com.iquantex.paas.nacostest.controller;

import com.iquantex.paas.nacostest.service.strategy.manager.IpAddressManager;
import com.iquantex.paas.nacostest.utils.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/ip")
@Validated
@Slf4j
public class IpAddressController {

    private IpAddressManager ipAddressManager;

    @Autowired
    public IpAddressController(IpAddressManager ipAddressManager){
        this.ipAddressManager = ipAddressManager;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultObj<Boolean> updateIpAddressPool(
            @RequestBody
            @NotEmpty
            @Valid List<
                    @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})(\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})){3}$")
                    String> ipList){
        log.info("> updateIpAddressPool: ipList = {}", ipList);
        ipAddressManager.updateIpAddressPool(ipList);
        return ResultObj.success(true);
    }

    @GetMapping
    public ResultObj<List<String>> listIpAddress(){
        log.info("> listIpAddress");
        return ResultObj.success(ipAddressManager.listIpAddress());
    }
}

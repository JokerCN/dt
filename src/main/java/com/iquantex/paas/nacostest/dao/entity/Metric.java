package com.iquantex.paas.nacostest.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Metric {
    private Long id;
    private Long testNo;
    private String clientId;
    private Long sequence;
    private String request;
    private String response;
    private Long latency;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}

package com.iquantex.paas.nacostest.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Metric {

    @Min(0)
    private Long id;

    @NotNull
    @Min(0)
    private Long testNo;

    @NotBlank
    private String clientId;

    @NotNull
    @Min(0)
    private Long sequence;

    @NotBlank
    private String request;

    @NotBlank
    private String response;

    @NotNull
    @Min(0)
    private Long latency;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}

package com.iquantex.paas.nacostest.dao.entity;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class RegisterInstanceMetric extends Metric{

    @NotNull
    private Boolean success;

    public RegisterInstanceMetric(@Min(0) Long id, @NotNull @Min(0) Long testNo, @NotBlank String clientId, @NotNull @Min(0) Long sequence, @NotBlank String request, @NotBlank String response, @NotNull @Min(0) Long latency, @NotNull Date startTime, @NotNull Date endTime, @NotNull Boolean success) {
        super(id, testNo, clientId, sequence, request, response, latency, startTime, endTime);
        this.success = success;
    }
}

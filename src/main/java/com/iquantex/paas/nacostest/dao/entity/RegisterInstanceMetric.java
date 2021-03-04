package com.iquantex.paas.nacostest.dao.entity;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class RegisterInstanceMetric extends Metric{

    @NotNull
    private Boolean success;
}

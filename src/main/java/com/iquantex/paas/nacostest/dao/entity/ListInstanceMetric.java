package com.iquantex.paas.nacostest.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ListInstanceMetric extends Metric{
    private String targetService;
    private Boolean exist;
}

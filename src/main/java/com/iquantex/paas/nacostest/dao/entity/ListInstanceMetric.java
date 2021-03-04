package com.iquantex.paas.nacostest.dao.entity;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ListInstanceMetric extends Metric{
    private String targetService;
    private Boolean exist;
}

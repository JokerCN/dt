package com.iquantex.paas.nacostest.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iquantex.paas.nacostest.dao.entity.ListInstanceMetric;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ListInstanceMetricMapper extends BaseMapper<ListInstanceMetric> {

    String TABLE_NAME = "`nacos-test-metrics`.`list_instance_metric`";

    @Insert("<script> " +
            "insert into " + TABLE_NAME +
            " (`test_no`, `client_id`, `sequence`, `request`, `response`, `latency`, `start_time`, `end_time`, `target_service`, `exist`) values " +
            "      <foreach item='item' collection='list' separator=','> " +
            "        (#{item.testNo}, #{item.clientId}, #{item.sequence}, #{item.request}, #{item.response}, #{item.latency}, #{item.startTime}, #{item.endTime}, #{item.targetService}, #{item.exist}) " +
            "      </foreach> " +
            "</script> ")
    @Transactional
    Integer insertBatchListInstanceMetrics(Collection<ListInstanceMetric> metrics);

    @Select("select * from " + TABLE_NAME +
            "    where 1 = 1" +
            "    and test_no = #{testNo}" +
            "    and start_time >= #{startTime}" +
            "    and end_time <= #{endTime}")
    List<ListInstanceMetric> listListInstanceMetrics(Long testNo, Date startTime, Date endTime);


    @Update("truncate table " + TABLE_NAME)
    void truncate();

}

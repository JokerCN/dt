package com.iquantex.paas.nacostest.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iquantex.paas.nacostest.dao.entity.RegisterInstanceMetric;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface RegisterInstanceMetricMapper extends BaseMapper<RegisterInstanceMetric> {

    String TABLE_NAME = "`nacos-test-metrics`.`register_instance_metric`";

    @Select("select * from " + TABLE_NAME +
            "        where 1 = 1" +
            "        and id = #{id}")
    RegisterInstanceMetric selectRegisterInstanceMetric(Long id);

    @Insert("<script> " +
            "insert into " + TABLE_NAME +
            "  (`test_no`, `client_id`, `sequence`, `request`, `response`, `latency`, `start_time`, `end_time`) values " +
            "      <foreach item='item' collection='list' separator=','> " +
            "        (#{item.testNo}, #{item.clientId}, #{item.sequence}, #{item.request}, #{item.response}, #{item.latency}, #{item.startTime}, #{item.endTime}) " +
            "      </foreach> " +
            "</script> ")
    @Transactional
    void insertBatchRegisterInstanceMetrics(Collection<RegisterInstanceMetric> set);


    @Update("truncate table " + TABLE_NAME)
    void truncate();

    @Select("select * from " + TABLE_NAME +
            "    where 1 = 1" +
            "    and test_no = #{testNo} " +
            "    and start_time >= #{startTime} " +
            "    and end_time <= #{endTime} ")
    List<RegisterInstanceMetric> listRegisterInstanceMetrics(Long testNo, Date startTime, Date endTime);

}

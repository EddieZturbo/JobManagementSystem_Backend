package com.mq.jobManagement.back_end.mapper;

import com.mq.jobManagement.back_end.common.CommonMapper;
import com.mq.jobManagement.back_end.pojo.Job;
import org.apache.ibatis.annotations.Mapper;

/**
 @author EddieZhang
 @create 2023-11-13 3:50 PM
 */
@Mapper
public interface JobMapper extends CommonMapper<Job,Integer> {
}

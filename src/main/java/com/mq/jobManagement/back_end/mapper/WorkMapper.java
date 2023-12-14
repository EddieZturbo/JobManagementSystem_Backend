package com.mq.jobManagement.back_end.mapper;

import com.mq.jobManagement.back_end.common.CommonMapper;
import com.mq.jobManagement.back_end.pojo.Work;
import org.apache.ibatis.annotations.Mapper;

/**
 @author EddieZhang
 @create 2023-12-07 4:20 PM
 */
@Mapper
public interface WorkMapper extends CommonMapper<Work, String> {
}

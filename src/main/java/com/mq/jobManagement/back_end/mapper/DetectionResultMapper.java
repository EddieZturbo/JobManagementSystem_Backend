package com.mq.jobManagement.back_end.mapper;

import com.mq.jobManagement.back_end.common.CommonMapper;
import com.mq.jobManagement.back_end.pojo.DetectionResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mq
 * @date 2023/12/28 15:08
 */
@Mapper
public interface DetectionResultMapper extends CommonMapper<DetectionResult, String> {
}

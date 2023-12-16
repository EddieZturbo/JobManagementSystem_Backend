package com.mq.jobManagement.back_end.mapper;

import com.mq.jobManagement.back_end.common.CommonMapper;
import com.mq.jobManagement.back_end.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 @author EddieZhang
 @create 2023-12-15 11:58 PM
 */
@Mapper
public interface CourseMapper extends CommonMapper<Course, String> {
}

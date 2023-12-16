package com.mq.jobManagement.back_end.service.impl;

import com.mq.jobManagement.back_end.common.CommonServiceImpl;
import com.mq.jobManagement.back_end.mapper.CourseMapper;
import com.mq.jobManagement.back_end.pojo.Course;
import com.mq.jobManagement.back_end.service.CourseService;
import org.springframework.stereotype.Service;

/**
 @author EddieZhang
 @create 2023-12-15 11:59 PM
 */
@Service
public class CourseServiceImpl extends CommonServiceImpl<Course, String, CourseMapper> implements CourseService {
}

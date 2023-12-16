package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.common.CommonController;
import com.mq.jobManagement.back_end.pojo.Course;
import com.mq.jobManagement.back_end.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 @author EddieZhang
 @create 2023-12-16 12:00 AM
 */
@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController extends CommonController<CourseService, Course,String> {
}

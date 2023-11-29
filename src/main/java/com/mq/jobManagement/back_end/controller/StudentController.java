package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.common.CommonController;
import com.mq.jobManagement.back_end.pojo.Student;
import com.mq.jobManagement.back_end.service.StudentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 @author EddieZhang
 @create 2023-11-12 7:50 PM
 */
@RestController
@RequestMapping("/student")
public class StudentController extends CommonController<StudentService, Student,Long> {

}

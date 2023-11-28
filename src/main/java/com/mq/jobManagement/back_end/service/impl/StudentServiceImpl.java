package com.mq.jobManagement.back_end.service.impl;

import com.mq.jobManagement.back_end.common.CommonServiceImpl;
import com.mq.jobManagement.back_end.mapper.StudentMapper;
import com.mq.jobManagement.back_end.pojo.Student;
import com.mq.jobManagement.back_end.service.StudentService;
import org.springframework.stereotype.Service;

/**
 @author EddieZhang
 @create 2023-11-12 7:52 PM
 */
@Service
public class StudentServiceImpl extends CommonServiceImpl<Student,Integer, StudentMapper> implements StudentService  {
}

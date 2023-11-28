package com.mq.jobManagement.back_end.mapper;

import com.mq.jobManagement.back_end.common.CommonMapper;
import com.mq.jobManagement.back_end.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 @author EddieZhang
 @create 2023-11-12 7:51 PM
 */
@Mapper
public interface StudentMapper extends CommonMapper<Student,Integer> {
}

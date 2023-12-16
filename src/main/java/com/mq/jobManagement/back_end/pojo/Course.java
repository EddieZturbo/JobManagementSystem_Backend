package com.mq.jobManagement.back_end.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mq.jobManagement.back_end.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @author EddieZhang
 @create 2023-12-15 11:56 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course")
public class Course extends CommonEntity<String> {
    @TableField("course_code")
    private String courseCode;
    @TableField("course_name")
    private String courseName;
    @TableField("course_status")
    private String courseStatus;
    @TableField("major_code")
    private String majorCode;
}

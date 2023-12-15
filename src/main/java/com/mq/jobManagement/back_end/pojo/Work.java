package com.mq.jobManagement.back_end.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mq.jobManagement.back_end.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @author EddieZhang
 @create 2023-12-07 4:15 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course_work")
public class Work extends CommonEntity<String> {
    @TableField("work_code")
    private String workCode;
    @TableField("work_name")
    private String workName;
    @TableField("work_status")
    private String workStatus;
    @TableField("course_code")
    private String courseCode;
}

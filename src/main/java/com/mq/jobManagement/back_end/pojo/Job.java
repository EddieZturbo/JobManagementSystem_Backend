package com.mq.jobManagement.back_end.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mq.jobManagement.back_end.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 @author EddieZhang
 @create 2023-11-13 3:21 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("jobs")
public class Job extends CommonEntity<Long> {
        @TableField(exist = false)
        private Long no;//序号
        private Integer score;
        @TableField("student_code")
        private Integer studentCode;
        @TableField("student_name")
        private String studentName;
        @TableField("is_qualify")
        private Integer isQualify;
        @TableField("is_commit")
        private Integer isCommit;
        @TableField("job_status")
        private Integer jobStatus;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        @TableField("commit_time")
        private Date commitTime;
        @TableField("work_code")
        private Integer workCode;
        @TableField("work_name")
        private String workName;
        @TableField("course_code")
        private Integer courseCode;
        @TableField("course_name")
        private String courseName;
}

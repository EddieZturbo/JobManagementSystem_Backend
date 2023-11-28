package com.mq.jobManagement.back_end.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mq.jobManagement.back_end.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 @author EddieZhang
 @create 2023-11-13 3:21 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("jobs")
public class Job extends CommonEntity<Integer> {
        private Integer ranking;
        private Integer score;
        private Integer studentCode;
        private String studentName;
        private Integer isQualify;
        private Integer isCommit;
        private Integer jobStatus;
        private Date commitTime;
        private Integer workCode;
        private String workName;
        private Integer courseCode;
        private String courseName;
}

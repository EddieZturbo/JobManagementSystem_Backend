package com.mq.jobManagement.back_end.service;

import com.mq.jobManagement.back_end.common.CommonService;
import com.mq.jobManagement.back_end.pojo.Job;

/**
 @author EddieZhang
 @create 2023-11-13 3:52 PM
 */
public interface JobService extends CommonService<Job,Long> {
    boolean insertJob(Job job);
}

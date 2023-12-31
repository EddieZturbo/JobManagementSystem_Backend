package com.mq.jobManagement.back_end.service;

import com.mq.jobManagement.back_end.common.CommonService;
import com.mq.jobManagement.back_end.pojo.Job;

import java.util.List;

/**
 @author EddieZhang
 @create 2023-11-13 3:52 PM
 */
public interface JobService extends CommonService<Job, String> {
    String insertJob(Job job);
    List<String> getCommittedWorkName();
}

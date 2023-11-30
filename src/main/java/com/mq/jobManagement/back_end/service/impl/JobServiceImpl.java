package com.mq.jobManagement.back_end.service.impl;

import com.mq.jobManagement.back_end.common.CommonServiceImpl;
import com.mq.jobManagement.back_end.mapper.JobMapper;
import com.mq.jobManagement.back_end.pojo.Job;
import com.mq.jobManagement.back_end.service.JobService;
import org.springframework.stereotype.Service;

/**
 @author EddieZhang
 @create 2023-11-13 3:52 PM
 */
@Service
public class JobServiceImpl extends CommonServiceImpl<Job, String, JobMapper> implements JobService {
    public String insertJob(Job job) {
        return baseMapper.insertJob(job) > 0 ? job.getId() : "-1L";
    }
}

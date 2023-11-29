package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.common.CommonController;
import com.mq.jobManagement.back_end.pojo.Job;
import com.mq.jobManagement.back_end.service.JobService;
import com.mq.jobManagement.back_end.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mq.jobManagement.back_end.utils.ResultCode.ADD_DATA_FAILURE;

/**
 @author EddieZhang
 @create 2023-11-13 3:54 PM
 */
@RestController
@RequestMapping("/job")
public class JobController extends CommonController<JobService, Job,Long> {
    @Autowired
    private JobService jobService;

    @PostMapping("insertJob")
    public Result<Long> insertJob(@RequestBody Job entity) {
        boolean insertJob = jobService.insertJob(entity);
        if (insertJob) {
            Long id = entity.getId();
            return Result.ok(id);
        } else {
            return Result.error(ADD_DATA_FAILURE);
        }
    }
}

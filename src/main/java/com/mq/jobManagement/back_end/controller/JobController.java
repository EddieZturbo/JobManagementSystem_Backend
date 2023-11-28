package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.common.CommonController;
import com.mq.jobManagement.back_end.pojo.Job;
import com.mq.jobManagement.back_end.service.JobService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 @author EddieZhang
 @create 2023-11-13 3:54 PM
 */
@RestController
@RequestMapping("/job")
public class JobController extends CommonController<JobService, Job,Integer> {
}

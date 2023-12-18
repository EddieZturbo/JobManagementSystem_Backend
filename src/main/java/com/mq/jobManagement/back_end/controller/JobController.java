package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.common.CommonController;
import com.mq.jobManagement.back_end.pojo.Job;
import com.mq.jobManagement.back_end.service.JobService;
import com.mq.jobManagement.back_end.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mq.jobManagement.back_end.utils.ResultCode.*;

/**
 @author EddieZhang
 @create 2023-11-13 3:54 PM
 */
@RestController
@RequestMapping("/job")
@Slf4j
public class JobController extends CommonController<JobService, Job, String> {
    @Autowired
    private JobService jobService;

    @PostMapping("insertJob")
    public Result<String> insertJob(@RequestBody Job entity) {
        String insertJob = jobService.insertJob(entity);
        if (!insertJob.equals("-1L")) {
            return Result.ok(insertJob);
        } else {
            return Result.error(ADD_DATA_FAILURE);
        }
    }

    @PostMapping("toRate/{id}")
    public Result<String> toRate(@PathVariable String id, @RequestParam String score) {
        Job job = jobService.get(id);
        if (job != null) {
            job.setScore(Integer.valueOf(score));
            job.setIsQualify(Integer.valueOf(score) >= 60 ? 1 : 0);
            job.setJobStatus(1);//1 means rated
            if (jobService.update(job)) {
                return Result.ok("Update successfully");
            } else {
                return Result.error(UPDATE_DATA_FAILURE);
            }
        }else{
            return Result.error(UPDATE_DATA_FAILURE);
        }
    }

    @GetMapping("getCommittedWorkName")
    public Result<List<String>> getCommittedWorkName() {
        List<String> committedWorkName = jobService.getCommittedWorkName();
        return committedWorkName.size() != 0 ? Result.ok(committedWorkName) : Result.error(DATA_ABSENCE);
    }
}

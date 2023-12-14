package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.common.CommonController;
import com.mq.jobManagement.back_end.pojo.Work;
import com.mq.jobManagement.back_end.service.WorkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 @author EddieZhang
 @create 2023-12-07 4:21 PM
 */
@RestController
@RequestMapping("/work")
public class WorkController extends CommonController<WorkService, Work,String> {
}

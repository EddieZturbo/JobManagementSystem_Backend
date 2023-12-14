package com.mq.jobManagement.back_end.service.impl;

import com.mq.jobManagement.back_end.common.CommonServiceImpl;
import com.mq.jobManagement.back_end.mapper.WorkMapper;
import com.mq.jobManagement.back_end.pojo.Work;
import com.mq.jobManagement.back_end.service.WorkService;
import org.springframework.stereotype.Service;

/**
 @author EddieZhang
 @create 2023-12-07 4:19 PM
 */
@Service
public class WorkServiceImpl extends CommonServiceImpl<Work, String, WorkMapper> implements WorkService {
}

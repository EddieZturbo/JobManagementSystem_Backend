package com.mq.jobManagement.back_end.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mq.jobManagement.back_end.common.CommonMapper;
import com.mq.jobManagement.back_end.pojo.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 @author EddieZhang
 @create 2023-11-13 3:50 PM
 */
@Mapper
public interface JobMapper extends CommonMapper<Job,Long> {

    @Override
    default IPage<Job> page(Map<String, Object> params) {
        IPage<Job> page = new Page<Job>((Integer)params.get("current"), (Integer)params.get("size"));
        return this.selectPage(page, new LambdaQueryWrapper<Job>().eq(Job::getJobStatus, 1));
    }

    /**
     * 插入一条job记录并返回主键
     * @param job
     * @return
     */
    int insertJob(Job job);

}

package com.mq.jobManagement.back_end.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mq.jobManagement.back_end.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @author EddieZhang
 @create 2023-11-12 7:44 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student")
public class Student extends CommonEntity<Integer> {
    private String name;
}
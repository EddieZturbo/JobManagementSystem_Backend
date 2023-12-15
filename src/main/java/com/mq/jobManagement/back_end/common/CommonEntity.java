package com.mq.jobManagement.back_end.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;

/**
 @author EddieZhang
 @create 2023-11-13 10:42 AM
 */
@Data
public class CommonEntity<K> {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private K id;

    @TableField(exist = false)
    private Long no;//序号

    @TableField(exist = false)
    private ArrayList<K> ids;
}

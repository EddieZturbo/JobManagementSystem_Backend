package com.mq.jobManagement.back_end.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.ArrayList;

/**
 @author EddieZhang
 @create 2023-11-13 10:42 AM
 */
@Data
public class CommonEntity<K> {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private K id;

    @TableField(exist = false)
    private ArrayList<K> ids;
}

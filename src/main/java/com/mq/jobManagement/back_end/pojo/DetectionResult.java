package com.mq.jobManagement.back_end.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.Map;


/**
 * @author mq
 * @date 2023/12/28 14:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "detection_result",autoResultMap = true)
public class DetectionResult {

    @TableField("id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("work_code")
    private Integer workCode;
    @TableField("similarity_threshold")
    private String similarityThreshold;
    @TableField(value = "result",typeHandler = JacksonTypeHandler.class)
    private JSONObject result;
    @TableField("last_modified_time")
    private Date lastModifiedTime;
}

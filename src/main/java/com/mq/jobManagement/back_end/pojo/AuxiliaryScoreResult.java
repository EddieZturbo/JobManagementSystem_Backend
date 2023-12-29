package com.mq.jobManagement.back_end.pojo;

/**
 * @author mq
 * @date 2023/12/28 17:33
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "auxiliary_score_result",autoResultMap = true)
public class AuxiliaryScoreResult {
    @TableField("id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("work_code")
    private Integer workCode;
    @TableField("ratio_pl")
    private String ratioPl;
    @TableField("ratio_content")
    private String ratioContent;
    @TableField("ratio_ref")
    private String ratioRef;
    @TableField(value = "result",typeHandler = JacksonTypeHandler.class)
    private JSONObject result;
    @TableField("last_modified_time")
    private Date lastModifiedTime;
}

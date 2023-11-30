package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.service.PlagiarismDetectService;
import com.mq.jobManagement.back_end.utils.Result;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mq.jobManagement.back_end.utils.ResultCode.MODIFY_CHECK_SIMILARITY_METHOD_FAIL;

/**
 * @author mq
 * @date 2023/11/28 16:13
 */
@RestController
public class PlagiarismDetectController {
    @Autowired
    private PlagiarismDetectService plagiarismDetectService;
    Logger logger = LoggerFactory.getLogger(PlagiarismDetectController.class);

    @PostMapping("/modify/check/similarity/method/{methodId}")
    public Result modifyCheckSimilarityMethod(@PathVariable("methodId") String methodId) throws Exception {
        JSONObject json = plagiarismDetectService.modify_check_similarity_method(String.valueOf(methodId));
        logger.info("matrix返回的信息：" + json.toString());
        if (!json.getString("result").equals("success")) {
            return Result.error(MODIFY_CHECK_SIMILARITY_METHOD_FAIL);
        }
        String message = json.getString("message");
        return Result.ok(message);
    }
}

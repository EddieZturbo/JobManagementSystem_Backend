package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.service.PlagiarismDetectService;
import com.mq.jobManagement.back_end.utils.JsonUtil;
import com.mq.jobManagement.back_end.utils.Result;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mq.jobManagement.back_end.utils.ResultCode.CHECK_SIMILARITY_FAIL;
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
    private JsonUtil jsonUtil = new JsonUtil();

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

    /***
     * 检测一个作业里的抄袭情况
     * 通过作业id获取这个作业的全部提交的作业
     * @param methodId
     * @return
     * @throws Exception
     */
    @PostMapping("/check_similarity_async/{jobId}")
    public Result checkSimilarity(@PathVariable("jobId") String jobId, HttpServletRequest request) throws Exception {
        double similar = ServletRequestUtils.getDoubleParameter(request, "similar", 0.2);
        System.out.println(similar);

        List<String> homeworkList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("studentId","1");
        map1.put("name", "777");
        map1.put("account", "yyy");
        map1.put("homeworkId", "1729480862809071638");
        map1.put("assignmentId", "11");
        homeworkList.add(jsonUtil.bean2jsonExt(map1));

        Map<String, String> map2 = new HashMap<>();
        map2.put("studentId","2");
        map2.put("name", "888");
        map2.put("account", "xxx");
        map2.put("homeworkId", "1729480862809071639");
        map2.put("assignmentId", "22");
        homeworkList.add(jsonUtil.bean2jsonExt(map2));

        Map<String, String> out = new HashMap<>();
        out.put("similar", String.valueOf(similar));
        out.put("homework", jsonUtil.list2jsonExt2(homeworkList));
        String homeworkStr = jsonUtil.bean2jsonExt(out);
        System.out.println(homeworkStr);
        JSONObject json = plagiarismDetectService.checkHomeworkSimilaryToMatrix(homeworkStr);
        logger.info("matrix返回的信息：" + json.toString());
        return Result.ok(json);
    }
}

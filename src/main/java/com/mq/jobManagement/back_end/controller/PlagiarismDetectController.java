package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.pojo.Job;
import com.mq.jobManagement.back_end.service.JobService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private JobService jobService;
    Logger logger = LoggerFactory.getLogger(PlagiarismDetectController.class);
    private JsonUtil jsonUtil = new JsonUtil();


    /**
     * 动态修改抄袭检测方法
     * @param methodId
     * @return
     * @throws Exception
     */
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


    /**
     * 动态修改参考文献部分的抄袭检测方法
     * @param methodId
     * @return
     * @throws Exception
     */
    @PostMapping("/modify/reference/similarity/method/{methodId}")
    public Result modifyReferenceSimilarityMethod(@PathVariable("methodId") String methodId) throws Exception {
        JSONObject json = plagiarismDetectService.modify_reference_similarity_method(String.valueOf(methodId));
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
    @PostMapping("/check_similarity_async/{workCode}")
    public Result checkSimilarity(@PathVariable("workCode") String workCode, HttpServletRequest request) throws Exception {
        double similar = ServletRequestUtils.getDoubleParameter(request, "similar", 0.2);
        System.out.println(similar);
        Map<String,Object> param=new HashMap<>();
        param.put("work_code",workCode);
        List<Job> jobList=jobService.list(param);
        List<String> homeworkList = new ArrayList<>();
        for (Job job : jobList) {
            Map<String, String> map = new HashMap<>();
            map.put("studentId",String.valueOf(job.getStudentCode()));
            map.put("name", String.valueOf(job.getStudentName()));
            map.put("account", String.valueOf(job.getStudentCode()));
            map.put("homeworkId", String.valueOf(job.getId()));
            homeworkList.add(jsonUtil.bean2jsonExt(map));
        }
        Map<String, String> out = new HashMap<>();
        out.put("similar", String.valueOf(similar));
        out.put("homework", jsonUtil.list2jsonExt2(homeworkList));
        String homeworkStr = jsonUtil.bean2jsonExt(out);
        System.out.println(homeworkStr);
        JSONObject json = plagiarismDetectService.checkHomeworkSimilaryToMatrix(homeworkStr);
        logger.info("matrix返回的信息：" + json.toString());
        return Result.ok(json);
    }


    /****
     * 获取两个文件的相似高亮显示
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/mark_similarity")
    public Result markSimilarity(@RequestParam("homeworkIdOne") String homeworkIdOne,@RequestParam("homeworkIdTwo") String homeworkIdTwo) throws Exception {
        Map<String, String> out = new HashMap<>();
        out.put("homeworkIdOne", String.valueOf(homeworkIdOne));
        out.put("homeworkIdTwo",String.valueOf( homeworkIdTwo));
        String homeworkStr = jsonUtil.bean2jsonExt(out);
        System.out.println(homeworkStr);
        JSONObject json = plagiarismDetectService.markSimilarity(homeworkStr);
        logger.info("matrix返回的信息：" + json.toString());
        return Result.ok(json);
    }


    /***
     * 对一个作业里面全部提交的作业进行辅助评分
     * 通过作业id获取这个作业的全部提交的作业
     * @param methodId
     * @return
     * @throws Exception
     */
    @PostMapping("/auxiliary_score/{workCode}")
    public Result auxiliaryScore(@PathVariable("workCode") String workCode, HttpServletRequest request) throws Exception {
        double ratio = ServletRequestUtils.getDoubleParameter(request, "ratio", 0.6); //抄袭所在比例
        System.out.println(ratio);
        Map<String,Object> param=new HashMap<>();
        param.put("work_code",workCode);
        List<Job> jobList=jobService.list(param);
        List<String> homeworkList = new ArrayList<>();
        for (Job job : jobList) {
            Map<String, String> map = new HashMap<>();
            map.put("studentId",String.valueOf(job.getStudentCode()));
            map.put("name", String.valueOf(job.getStudentName()));
            map.put("account", String.valueOf(job.getStudentCode()));
            map.put("homeworkId", String.valueOf(job.getId()));
            homeworkList.add(jsonUtil.bean2jsonExt(map));
        }
        Map<String, String> out = new HashMap<>();
        out.put("ratio", String.valueOf(ratio));
        out.put("homework", jsonUtil.list2jsonExt2(homeworkList));
        String homeworkStr = jsonUtil.bean2jsonExt(out);
        System.out.println(homeworkStr);
        JSONObject json = plagiarismDetectService.auxiliaryScore(homeworkStr);
        logger.info("matrix返回的信息：" + json.toString());
        return Result.ok(json);
    }

    /***
     * 检测一个作业里的抄袭情况
     * 通过作业id获取这个作业的全部提交的作业
     * @param methodId
     * @return
     * @throws Exception
     */
    @PostMapping("/check_similarity_async_test/{workCode}")
    public Result checkSimilarity_test(@PathVariable("workCode") String workCode, HttpServletRequest request) throws Exception {
        double similar = ServletRequestUtils.getDoubleParameter(request, "similar", 0.2);
        System.out.println(similar);
        String jsonString="{\n" +
                "        \"failCount\": 0,\n" +
                "        \"result\": {\n" +
                "            \"matrix\": [\n" +
                "                {\n" +
                "                    \"account\": \"1001\",\n" +
                "                    \"maxSimilarity\": \"0.2\",\n" +
                "                    \"name\": \"Eddie\",\n" +
                "                    \"similarList\": [\n" +
                "                        {\n" +
                "                            \"account\": \"1001\",\n" +
                "                            \"name\": \"Eddie\",\n" +
                "                            \"similarity\": \"0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"account\": \"1002\",\n" +
                "                            \"name\": \"Eddie1\",\n" +
                "                            \"similarity\": \"0.2\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"account\": \"1002\",\n" +
                "                    \"maxSimilarity\": \"0.2\",\n" +
                "                    \"name\": \"Eddie1\",\n" +
                "                    \"similarList\": [\n" +
                "                        {\n" +
                "                            \"account\": \"1001\",\n" +
                "                            \"name\": \"Eddie\",\n" +
                "                            \"similarity\": \"0.2\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"account\": \"1001\",\n" +
                "                            \"name\": \"Eddie\",\n" +
                "                            \"similarity\": \"0\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ],\n" +
                "            \"result\": \"success\",\n" +
                "            \"trouble_list\": []\n" +
                "        },\n" +
                "        \"status\": \"Task completed!\",\n" +
                "        \"successCount\": 2,\n" +
                "        \"total\": 2\n" +
                "    }";
        JSONObject json = JSONObject.fromObject(jsonString);
        return Result.ok(json);
    }


    /****
     * 获取两个文件的相似高亮显示
     * 传参：两个作业文件的id
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/mark_similarity_test")
    public Result markSimilarity_test(@RequestParam("homeworkOne") String homeworkIdOne,@RequestParam("homeworkTwo") String homeworkIdTwo) throws Exception {
        String jsonString=" {\n" +
                "        \"code\": \"200\",\n" +
                "        \"result\": \"success\",\n" +
                "        \"highlightPdf\": [\n" +
                "            \"f873102f-6913-4778-8d4a-6d0600138a1b\",\n" +
                "            \"204f7a5b-1897-4e9b-b3e8-80d6a103027d\"\n" +
                "        ],\n" +
                "        \"attachName\": [\n" +
                "            \"1729480862809071638.pdf\",\n" +
                "            \"1729480862809071639.pdf\"\n" +
                "        ]\n" +
                "    }";
        JSONObject json = JSONObject.fromObject(jsonString);
        return Result.ok(json);
    }


}

package com.mq.jobManagement.back_end.controller;

import com.mq.jobManagement.back_end.pojo.AuxiliaryScoreResult;
import com.mq.jobManagement.back_end.pojo.DetectionResult;
import com.mq.jobManagement.back_end.pojo.Job;
import com.mq.jobManagement.back_end.service.AuxiliaryScoreResultService;
import com.mq.jobManagement.back_end.service.JobService;
import com.mq.jobManagement.back_end.service.PlagiarismDetectService;
import com.mq.jobManagement.back_end.utils.JsonUtil;
import com.mq.jobManagement.back_end.utils.Result;
import net.sf.json.JSONArray;
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
import java.util.*;

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
    @Autowired
    private AuxiliaryScoreResultService auxiliaryScoreResultService;
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
        param.put("similarity_threshold",String.valueOf(similar));
        List<DetectionResult> detectionResults=plagiarismDetectService.list(param);
        if(detectionResults!=null && detectionResults.size()==1){
            JSONObject result=detectionResults.get(0).getResult();
            logger.info("本次PD直接从database中获取"+result);
            return Result.ok(result);
        }else{
            Map<String,Object> param_job=new HashMap<>();
            param_job.put("work_code",workCode);
            List<Job> jobList=jobService.list(param_job);
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
            //将matrix返回的数据持久化到数据库中
            DetectionResult detectionResult=new DetectionResult();
            detectionResult.setWorkCode(Integer.valueOf(workCode));
            detectionResult.setSimilarityThreshold(String.valueOf(similar));
            detectionResult.setResult(json);
            detectionResult.setLastModifiedTime(new Date());
            boolean flag=plagiarismDetectService.add(detectionResult);
            logger.info("持久化是否成功:"+flag);
            return Result.ok(json);
        }
    }

    @PostMapping("/check_similarity_async/test/{workCode}")
    public Result checkSimilarityTest(@PathVariable("workCode") String workCode, HttpServletRequest request) throws Exception {
        double similar = ServletRequestUtils.getDoubleParameter(request, "similar", 0.2);
        System.out.println(similar);
        Map<String,Object> param=new HashMap<>();
        param.put("work_code",workCode);
        param.put("similarity_threshold",String.valueOf(similar));
        List<DetectionResult> detectionResults=plagiarismDetectService.list(param);
        if(detectionResults!=null && detectionResults.size()==1){
            //database中有数据就直接从database中取 只可能存在一条数据
            JSONObject result=detectionResults.get(0).getResult();
            System.out.println(result);
            return Result.ok(result);
        }else{
            String jsonString="{\"failCount\":0,\"result\":{\"matrix\":[{\"account\":\"1001\",\"homeworkId\":\"1729480862809071638\",\"maxSimilarity\":\"0.2\",\"name\":\"Eddie\",\"similarList\":[{\"account\":\"1001\",\"homeworkId\":\"1729480862809071638\",\"name\":\"Eddie\",\"similarity\":\"0\"},{\"account\":\"1001\",\"homeworkId\":\"1729480862809071639\",\"name\":\"Eddie\",\"similarity\":\"0.2\"}]},{\"account\":\"1001\",\"homeworkId\":\"1729480862809071639\",\"maxSimilarity\":\"0.2\",\"name\":\"Eddie\",\"similarList\":[{\"account\":\"1001\",\"homeworkId\":\"1729480862809071638\",\"name\":\"Eddie\",\"similarity\":\"0.2\"},{\"account\":\"1001\",\"homeworkId\":\"1729480862809071639\",\"name\":\"Eddie\",\"similarity\":\"0\"}]}],\"result\":\"success\",\"trouble_list\":[]},\"status\":\"Task completed!\",\"successCount\":2,\"total\":2}";
            JSONObject json = JSONObject.fromObject(jsonString);
            //将matrix返回的数据持久化到数据库中
            DetectionResult detectionResult=new DetectionResult();
//            detectionResult.setId(2);
            detectionResult.setWorkCode(Integer.valueOf(workCode));
            detectionResult.setSimilarityThreshold(String.valueOf(similar));
            detectionResult.setResult(json);
            detectionResult.setLastModifiedTime(new Date());
            boolean flag=plagiarismDetectService.add(detectionResult);
            logger.info("持久化是否成功:"+flag);
            return Result.ok(json);
        }
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
        double ratio_pl = ServletRequestUtils.getDoubleParameter(request, "ratio_pl", 0.5); //抄袭所在比例
        double ratio_content = ServletRequestUtils.getDoubleParameter(request, "ratio_content", 0.3); //content所在比例
        double ratio_ref = ServletRequestUtils.getDoubleParameter(request, "ratio_ref", 0.2); //ref所在比例
        Map<String,Object> param=new HashMap<>();
        param.put("work_code",workCode);
        List<Job> jobList=jobService.list(param);
        if(isExistAuxiliaryScore(ratio_pl,ratio_content,ratio_ref,workCode)){
            return Result.ok(jobList);
        }else{
            List<String> homeworkList = new ArrayList<>();
            for (Job job : jobList) {
                Map<String, String> map = new HashMap<>();
                map.put("studentId",String.valueOf(job.getStudentCode()));
                map.put("name", String.valueOf(job.getStudentName()));
                map.put("account", String.valueOf(job.getStudentCode()));
                map.put("homeworkId", String.valueOf(job.getId()));
                homeworkList.add(jsonUtil.bean2jsonExt(map));
            }
            Map<String, String> map = new HashMap<>();
            map.put("content",String.valueOf(ratio_content));
            map.put("plagiarism", String.valueOf(ratio_pl));
            map.put("references", String.valueOf(ratio_ref));
            Map<String, String> out = new HashMap<>();
            out.put("ratio", jsonUtil.bean2jsonExt(map));
            out.put("homework", jsonUtil.list2jsonExt2(homeworkList));
            String homeworkStr = jsonUtil.bean2jsonExt(out);
            System.out.println(homeworkStr);
            JSONObject json = plagiarismDetectService.auxiliaryScore(homeworkStr);
            logger.info("matrix返回的信息：" + json.toString());
            JSONObject resultObject = json.getJSONObject("result");
            JSONArray matrixArray = resultObject.getJSONArray("matrix");
            for (Object item : matrixArray) {
                JSONObject matrixItem = (JSONObject) item;
                String homeworkId = matrixItem.getString("homeworkId");
                double auxiliaryScore = matrixItem.getDouble("auxiliary_score");
                Job job=jobService.get(homeworkId);
                job.setAuxiliaryScore(auxiliaryScore);
                boolean updateStatus=jobService.update(job);
                if(updateStatus){
                    System.out.println("job更新成功");
                }
            }
            //将matrix返回的数据持久化到数据库中
            AuxiliaryScoreResult auxiliaryScoreResult=new AuxiliaryScoreResult();
            auxiliaryScoreResult.setWorkCode(Integer.valueOf(workCode));
            auxiliaryScoreResult.setRatioPl(String.valueOf(ratio_pl));
            auxiliaryScoreResult.setRatioContent(String.valueOf(ratio_content));
            auxiliaryScoreResult.setRatioRef(String.valueOf(ratio_ref));
            auxiliaryScoreResult.setResult(json);
            auxiliaryScoreResult.setLastModifiedTime(new Date());
            boolean flag=auxiliaryScoreResultService.add(auxiliaryScoreResult);
            logger.info("持久化是否成功:"+flag);
            return Result.ok(json);
        }
    }

    public boolean isExistAuxiliaryScore(double ratio_pl ,double ratio_content,double ratio_ref,String workCode){
        Map<String,Object> param=new HashMap<>();
        param.put("work_code",workCode);
        param.put("ratio_pl",String.valueOf(ratio_pl));
        param.put("ratio_content",String.valueOf(ratio_content));
        param.put("ratio_ref",String.valueOf(ratio_ref));
        List<AuxiliaryScoreResult> auxiliaryScoreResults=auxiliaryScoreResultService.list(param);
        if(auxiliaryScoreResults!=null && auxiliaryScoreResults.size()==1){
            return true;
        }else{
            return false;
        }
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

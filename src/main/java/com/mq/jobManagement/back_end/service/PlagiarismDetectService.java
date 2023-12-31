package com.mq.jobManagement.back_end.service;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author mq
 * @date 2023/11/28 16:05
 */
public interface PlagiarismDetectService {
    public JSONObject requestService(Map<String, String> params, String url) throws Exception ;
    public JSONObject modify_check_similarity_method(String methodId) throws Exception;
    public JSONObject modify_reference_similarity_method(String methodId) throws Exception;
    public JSONObject checkHomeworkSimilaryToMatrix(String homework) throws Exception ;
    public JSONObject markSimilarity(String homeworkIds) throws Exception ;

    public JSONObject auxiliaryScore(String homework) throws Exception ;


}

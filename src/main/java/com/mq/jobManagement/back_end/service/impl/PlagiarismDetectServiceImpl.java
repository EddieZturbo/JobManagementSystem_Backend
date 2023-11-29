package com.mq.jobManagement.back_end.service.impl;

import com.mq.jobManagement.back_end.service.PlagiarismDetectService;
import com.mq.jobManagement.back_end.utils.ApiUtil;
import com.mq.jobManagement.back_end.utils.HttpClientUtil;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author mq
 * @date 2023/11/28 16:06
 */
@Service
public class PlagiarismDetectServiceImpl implements PlagiarismDetectService {
    Logger logger = LoggerFactory.getLogger(PlagiarismDetectServiceImpl.class);


    @Override
    public JSONObject requestService(Map<String, String> params, String url) throws Exception  {
        logger.info( " request:" + url);
        CloseableHttpClient client = HttpClientUtil.getClient();
        try {
            HttpPost checkPost = HttpClientUtil.getRequestPost(url, params);
            CloseableHttpResponse downResponse = client.execute(checkPost);
            return HttpClientUtil.getResponseStr(downResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /***
     * 动态修改抄袭检测的方法
     * @param methodId
     * @return
     */
    @Override
    public JSONObject modify_check_similarity_method(String methodId) throws Exception {
        Map<String, String> params = new Hashtable<>();
        params.put("methodId", methodId);
        return this.requestService(params, ApiUtil.Matrix_Modify_Check_Similarity_Method);
    }
}
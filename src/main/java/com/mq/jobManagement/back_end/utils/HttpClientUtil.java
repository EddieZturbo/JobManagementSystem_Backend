package com.mq.jobManagement.back_end.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author mq
 * @date 2023/11/28 15:51
 */
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static CloseableHttpClient closeableHttpClient;
    private static JSONObject ju;

    /**
     * 创建一个client，使用单例模式
     *
     * @return
     */
    public static CloseableHttpClient getClient() {
        if (closeableHttpClient == null) {
            closeableHttpClient = HttpClients.createDefault();
        }
        return closeableHttpClient;
    }

    /**
     * @param url：请求的url
     * @param params：请求的参数，不是数组的
     * @return
     */
    public static HttpPost getRequestPost(String url, Map<String, String> param) throws MalformedURLException, URISyntaxException {
        URL apiUrl = new URL(url);
        URI uri = null;
        if (apiUrl.getPort() != 80) {
            //     public URI(String scheme,
            //               String userInfo, String host, int port,
            //               String path, String query, String fragment)
            uri = new URI(apiUrl.getProtocol(), apiUrl.getUserInfo(), apiUrl.getHost(), apiUrl.getPort(), apiUrl.getPath(), apiUrl.getQuery(), null);
        } else {
//                public URI(String scheme,
//                    String authority,
//                    String path, String query, String fragment)
            uri = new URI(apiUrl.getProtocol(), apiUrl.getHost(), apiUrl.getPath(), apiUrl.getQuery(), null);
        }
//        System.out.println(uri.toString());
        HttpPost request = new HttpPost(uri);
        UrlEncodedFormEntity uefEntity = null;
        if (param != null) {
            //获取参数
            uefEntity = HttpClientUtil.getUrlEntity(param);
            request.setEntity(uefEntity);
        }
        return request;
    }

    /**
     * @param url：请求的url
     * @param param：请求的参数，不是数组的
     * @param params：请求的参数，数组类型的
     * @return
     */
    public static HttpPost getRequestPost(String url, Map<String, String[]> params, Map<String, String> param) {
        HttpPost request = new HttpPost(url);
        HttpEntity httpEntity = null;
        //获取参数
        httpEntity = HttpClientUtil.getUrlListEntity(params, param);
        request.setEntity(httpEntity);
        return request;
    }

    /**
     * 根据请求返回结果
     *
     * @param response
     * @return
     */
    public static JSONObject getResponseStr(CloseableHttpResponse response) {
        ju = new JSONObject();
        HttpEntity entity = response.getEntity();
        try {
            String s = EntityUtils.toString(entity, "utf-8");
            logger.info(s);
            ju = JSONObject.fromObject(s);
            return ju;
        } catch (Exception e) {
            e.printStackTrace();
            ju.put("result", "failed");
            ju.put("message", "获取错误");
            return ju;
        }
    }

    /**
     * 获取请求entity
     *
     * @param params
     * @return
     */
    public static UrlEncodedFormEntity getUrlEntity(Map<String, String> params) {
        List<NameValuePair> formParams = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                formParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }
        logger.info("formParams:" + formParams.toString());
        try {
            return new UrlEncodedFormEntity(formParams, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取请求entity(数组的)
     *
     * @param params
     * @param param
     * @return
     */
    public static HttpEntity getUrlListEntity(Map<String, String[]> params, Map<String, String> param) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                //解决中文乱码问题
                .setMode(HttpMultipartMode.RFC6532)
                .setCharset(Charset.defaultCharset());
        if (params != null) {
            for (Map.Entry<String, String[]> p : params.entrySet()) {
                for (String str : p.getValue()) {
                    builder.addTextBody(p.getKey(), str);
                }
            }
        }
        if (param != null) {
            for (Map.Entry<String, String> p : param.entrySet()) {
                builder.addTextBody(p.getKey(), p.getValue());
            }
        }
        try {
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }






}

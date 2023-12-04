package com.mq.jobManagement.back_end.utils;

/**
 * @author mq
 * @date 2023/11/28 15:43
 */
public class ApiUtil {
    private static String Matrix_PATH = "http://127.0.0.1:5001";

    /**
     * 作业抄袭情况,返回相似度表
     */
    public static String Matrix_Check_Similarity = Matrix_PATH + "/check_similarity";

    /**
     * 作业抄袭情况,返回相似度表，异步接口
     */
    public static String Matrix_Check_Similarity_Async = Matrix_PATH + "/jms/check_similarity_async";


    /**
     * 作业抄袭情况,渲染后给前端一个文件存储在WebLib的编号
     */
    public static String Matrix_Mark_Similarity = Matrix_PATH + "/mark_similarity";


    /**
     * 获取进度
     */
    public static String Matrix_Progress = Matrix_PATH + "/status";
    /****
     * 动态切换抄袭检测方法
     */
    public static String Matrix_Modify_Check_Similarity_Method=Matrix_PATH+"/modify_check_similarity_method";
}

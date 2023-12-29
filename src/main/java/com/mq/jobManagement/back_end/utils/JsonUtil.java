package com.mq.jobManagement.back_end.utils;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author mq
 * @date 2023/12/1 9:17
 */
public class JsonUtil {
    /**
     * 对数组进行处理
     * 将一个{}数组，转换为一个[{},{},{}]
     *
     * @param list：数组
     */
    public String list2jsonExt2(List<String> list) {
        StringBuilder returnStr = new StringBuilder();
        returnStr.append("[");
        if (list != null) {
            for (String t : list) {
                returnStr.append(t + ",");
            }
            if (list.size() > 0) {
                returnStr.setCharAt(returnStr.length() - 1, ']');
            } else {
                returnStr.append("]");
            }
        } else {
            returnStr.append("]");
        }
        return returnStr.toString();
    }

    /**
     * description:只有扩展字段
     */
    public String bean2jsonExt(Map<String, String> ext) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, String> entry : ext.entrySet()) {
            if (entry.getValue() != null && (
                    (entry.getValue().startsWith("[") && entry.getValue().endsWith("]")) ||
                            (entry.getValue().startsWith("{") && entry.getValue().endsWith("}"))
            )
            ) {
                json.append("\"" + entry.getKey() + "\":" + entry.getValue() + ",");
            } else {
                json.append("\"" + entry.getKey() + "\":\"" + (entry.getValue() == null ? "" : entry.getValue()) + "\",");
            }
        }
        if (ext.size() > 0) {
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    public  Map<String, Object> jsonObjectToMap(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        // 使用迭代器遍历 JSONObject 的键值对，并将其放入 Map 中
        Iterator<?> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jsonObject.get(key);
            map.put(key, value);
        }
        return map;
    }
}

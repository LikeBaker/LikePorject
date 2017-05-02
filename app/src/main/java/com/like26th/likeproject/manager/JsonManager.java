package com.like26th.likeproject.manager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/10.
 */

public class JsonManager {

    public static String LOGIN = "checkuser";
    public static String SEND = "say";

    /**
     * 上线通知
     * @param type 客服种类
     * @param uid id
     * @param name 名称
     * @return
     */
    public static JSONObject getLoginJson(String type, String uid, String name) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("uid", uid);
            jsonObject.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * 发送数据
     * @param type
     * @param to
     * @param name
     * @param msg
     * @return
     */
    public static JSONObject getMessageJson(String type, String to, String name, String msg) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("to", to);
            jsonObject.put("name", name);
            jsonObject.put("msg", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getLoginoutJson(String type, String uid, String name){
        JSONObject jsonObject = new JSONObject();
        return  jsonObject;
    }
}


package com.like26th.likeproject.chat.precenter;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/10.
 */

public interface IChatPresenter {

    /**
     * 初始化连接
     * @param ip
     */
    public void initConnect(String ip);

    /**
     * 登陆通知
     * @param jsonObject
     */
    public void login(JSONObject jsonObject);

    /**
     * 发送一条信息
     * @param jsonObject
     */
    public void sendMsg(JSONObject jsonObject);

    /**
     * 下线通知
     */
    public void loginOut();
}

package com.like26th.likeproject.chat.precenter;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.like26th.likeproject.App;
import com.like26th.likeproject.chat.ChatActivity;
import com.like26th.likeproject.chat.ChatService;
import com.like26th.likeproject.manager.JsonManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

import static com.like26th.likeproject.chat.ChatActivity.CHATMSG;

/**
 * Created by Administrator on 2016/12/10.
 */

public class IChatPresenterCompl implements IChatPresenter {

    public static String MSG = "0";
    public static String MSGTO = "1";

    private ChatActivity mChatActivity;
    private ChatService mService;
    private String chatType;
    private WebSocketClient webSocketClient;

    /**
     * 当前客服uid
     */
    private String currentUid;

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public IChatPresenterCompl(ChatActivity mChatActivity) {
        this.mChatActivity = mChatActivity;
    }

    public IChatPresenterCompl(int chatType, ChatService mService) {
        this.mService = mService;
        this.chatType = String.valueOf(chatType);
    }

    public IChatPresenterCompl(ChatActivity _ChatActivity, int chatType, ChatService mService) {
        this.mChatActivity = _ChatActivity;
        this.mService = mService;
        this.chatType = String.valueOf(chatType);
    }

    @Override
    public void initConnect(final String ip) {

        try {
            Draft_17 Draft_17 = new Draft_17();
            URI serverUri = new URI(ip);
//            mClient = new Client(serverUri, Draft_17);
            webSocketClient = new WebSocketClient(serverUri, Draft_17) {

                private Intent intent;

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("ChatService", "open");
                    //通知服务器上线
                    //userId 传mac地址，或1234加随机数
                    String mac;
                    String userId;
                    if (App.mac != null) {
                        userId = App.mac;
                    } else {
                        // TODO: 2017/4/14 此处需测试写法是否正确
                        Toast.makeText(mChatActivity, "mac地址不正确即将退出", Toast.LENGTH_SHORT).show();
                        webSocketClient.close();
                        mChatActivity.finish();
                        return;
                    }

                    //未登录用户命名为游客，已经登陆用户如果未命名，命名为无名氏加随机数
//                    String name = "游客"+ (int)(Math.random() * 10000);
                    String name = "游客"+ (654321);

//                    if (userInfo == null || userInfo.getId() == null) {
//                        name = "游客" + (int)(Math.random() * 11);
//                    }else if (userInfo != null && userInfo.getId() != null){
//                        userId = userInfo.getId();
//                        name = userInfo.getUsername();
//                        if (name.length() <= 0) {
//                            name = "无名氏" + (int)(Math.random() * 11);
//                        }
//                    }

                    JSONObject loginJson = JsonManager.getLoginJson(JsonManager.LOGIN, userId, name);
                    Log.d("IChatPresenterCompl", "loginJson:" + loginJson);
                    webSocketClient.send(loginJson.toString());
                    //发送广播用于更新页面
//                    Intent intent = new Intent().setAction(ChatActivity.CHATBROAST);
//                    intent.putExtra(Constant.BROADCAST_TYPE, ChatActivity.OPEN);
//                    mService.sendBroadcast(intent);
                }

                @Override
                public void onMessage(String s) {
                    intent = new Intent().setAction(ChatActivity.CHATBROADCAST);
//                    Log.d("ChatBroadcast", s);

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        Log.d("IChatPresenterCompl", "jsonObject:" + jsonObject.toString());
                        String type = jsonObject.getString("type");
                        Log.d("IChatPresenterCompl", type);
                        switch (type){
                            case "msg"://接收到信息(特殊信息，你好，链接超时，两分钟，对方不再线等等)
                                try {
                                    String msg = jsonObject.getString("msg");

                                    intent.putExtra(ChatActivity.CHATBCTYPE, "msg");
                                    intent.putExtra(ChatActivity.CHATMSG, msg);
                                } catch (Exception e) {
                                    Log.d("IChatPresenterCompl", e.toString());
                                }

                                //发送广播 更新页面：收到信息
//                                intent.putExtra(Constant.BROADCAST_MSG, msg);
//                                Log.d("IChatPresenterCompl", "receiver:" + msg);
//                                intent.putExtra(Constant.BROADCAST_TYPE, ChatActivity.RECEIVE);
//                                intent.putExtra(Constant.BROADCAST_MESSAGE_TYPE, IChatPresenterCompl.MSG);
                                break;
                            case "usertotle"://获取客服列表
//                                intent.putExtra(Constant.BROADCAST_TYPE, ChatActivity.SERVICECUSTOMLIST);
                                jsonObject.getJSONArray("ulist");
                                JSONArray ulist = jsonObject.getJSONArray("ulist");
                                String uid = "";
                                String uName = "";
                                if (chatType.equals("0")) {
                                    JSONObject info = (JSONObject) ulist.get(0);
                                    uid = info.getString("uid");
                                    setCurrentUid(uid);
                                    uName = info.getString("name");
                                    mChatActivity.setTitleName(uName);

                                } else if (chatType.equals("1")){
                                    JSONObject info = (JSONObject) ulist.get(1);
                                    uid = info.getString("uid");
                                    setCurrentUid(uid);
                                    uName = info.getString("name");
                                    Log.d("IChatPresenterCompl", "uname:"+uName);
                                    intent.putExtra(ChatActivity.CHATBCTYPE, "usertotle");
                                    intent.putExtra(ChatActivity.CHATREFRESHTITLE, uName);
                                } else {
                                    JSONObject info = (JSONObject) ulist.get(2);
                                    uid = info.getString("uid");
                                    setCurrentUid(uid);
                                    uName = info.getString("name");
                                    mChatActivity.setTitleName(uName);
                                    intent.putExtra(ChatActivity.CHATBCTYPE, "usertotle");
                                    intent.putExtra(ChatActivity.CHATREFRESHTITLE, uName);
                                }


//                                Log.d("IChatPresenterCompl", "uid "+uid + " uName "+uName);
//                                intent.putExtra(Constant.BROADCAST_SERVICE_CUSTOMER_UID, uid);
//                                intent.putExtra(Constant.BROADCAST_SERVICE_CUSTOMER_NAME, uName);

                                break;

                            case "msgto":
                                //收到聊天信息
                                String receiveMsg = jsonObject.getString("msg");
                                Log.d("IChatPresenterCompl", receiveMsg);
                                String fromMsg = jsonObject.getString("from");
                                intent.putExtra(ChatActivity.CHATBCTYPE, "msgto");
                                intent.putExtra(ChatActivity.CHATRECEIVEMSG, receiveMsg);
//                                intent.putExtra(Constant.BROADCAST_MSG, receiveMsg);
//                                intent.putExtra(Constant.BROADCAST_TYPE, ChatActivity.RECEIVE);
//                                intent.putExtra(Constant.BROADCAST_MESSAGE_TYPE, IChatPresenterCompl.MSGTO);
                                break;
                            default:break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mService.sendBroadcast(intent);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("IChatPresenterCompl", "强制退出");
                    intent = new Intent().setAction(ChatActivity.CHATBROADCAST);
                    intent.putExtra(ChatActivity.CHATBCTYPE, ChatActivity.CHATCLOSE);
                    mService.sendBroadcast(intent);
                    webSocketClient.close();
//                    Intent intent = new Intent().setAction(ChatActivity.CHATBROAST);
//                    intent.putExtra(Constant.BROADCAST_TYPE, ChatActivity.CLOSE);
//                    mService.sendBroadcast(intent);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("ChatService", "e:" + e);
                    webSocketClient.close();
//                    Intent intent = new Intent().setAction(ChatActivity.CHATBROAST);
//                    intent.putExtra(Constant.BROADCAST_TYPE, ChatActivity.ERROR);
//                    mService.sendBroadcast(intent);
                }
            };

            webSocketClient.connect();
        } catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public void login(JSONObject jsonObject) {
//        mClient.
    }

    @Override
    public void sendMsg(JSONObject jsonObject) {
        try{
            webSocketClient.send(jsonObject.toString());
        } catch (Exception e) {
            Log.d("IChatPresenterCompl", "e:" + e);
        }
    }

    @Override
    public void loginOut() {
        webSocketClient.close();
    }
}

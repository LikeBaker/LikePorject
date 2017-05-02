package com.like26th.likeproject.chat;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.like26th.likeproject.chat.precenter.IChatPresenter;
import com.like26th.likeproject.chat.precenter.IChatPresenterCompl;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ChatService extends Service {

    public static final String IP = "ws://www.jwth-health.com:8484";

    // 0 健康， 1 服务
    private int type = 1;
    private IChatPresenter iChatPresenter;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        iChatPresenter = new IChatPresenterCompl(1, this);

        new Thread(){
            @Override
            public void run() {
                super.run();
                iChatPresenter.initConnect(IP);
            }
        }.start();

        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        public ChatService getService(){
            return ChatService.this;
        }
    }

    public void sendMsg(JSONObject jsonObject){
        iChatPresenter.sendMsg(jsonObject);
    }

    @Override
    public void onDestroy() {
        iChatPresenter.loginOut();
        super.onDestroy();
    }
}

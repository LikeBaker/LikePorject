package com.like26th.likeproject;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.like26th.likeproject.manager.FontManager;
import com.like26th.likeproject.util.preference.PreferenceUtil;

/**
 * Created by Administrator on 2017/2/17.
 */

public class App extends Application {

    public static Context context;
    public static String mac;
    public static PreferenceUtil preferenceUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        mac = getMacAddress();

        init();
    }

    private void init(){
        preferenceUtil = PreferenceUtil.getInstance();
        FontManager.init();
    }

    public String getMacAddress() {
        WifiManager wifiMng = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();
        mac = wifiInfor.getMacAddress();
        String macAddress = mac.replace(":", "-");
        return macAddress;
    }
}

package com.like26th.likeproject.util;

import com.like26th.likeproject.App;
import com.like26th.likeproject.rxjava.module.HomeAdModule;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.like26th.likeproject.util.MD5Util.getMD5;

/**
 * Created by Administrator on 2017/2/17.
 */

public class ApiHelper {
    public static final String BASE_URL = "http://www.jwth-health.com/api/";
    private static String macAddress;
    public static String deviceId = "android";

    private static IApi iApi;

    public static void init (){
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取时间
                .writeTimeout(10, TimeUnit.SECONDS.SECONDS);//设置写入时间
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(App.context.getCacheDir(), cacheSize);
        builder.cache(cache);

        macAddress = App.mac;

        iApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build().create(IApi.class);
    }

    public static Call<HomeAdModule> requestSportAd(){
        String cKey;

        cKey = getMD5(macAddress + deviceId);

        return iApi.sportAd(deviceId, macAddress, cKey);
    }
}

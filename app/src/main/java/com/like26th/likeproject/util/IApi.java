package com.like26th.likeproject.util;

import com.like26th.likeproject.rxjava.module.HomeAdModule;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface IApi {
    @POST("ad/sport")
    @FormUrlEncoded
    Call<HomeAdModule> sportAd(@Field("dev") String dev, @Field("mac") String mac, @Field("sign") String sign);
}

package com.like26th.likeproject.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.like26th.likeproject.R;
import com.like26th.likeproject.rxjava.module.HomeAdModule;
import com.like26th.likeproject.util.ApiHelper;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/2/16.
 */
public class RxjavaActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        ApiHelper.init();

//        官方文档
//        Flowable.just("Hello world").subscribe(System.out::println);

        Flowable.just("Hello world", "hello java", "hellow android")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println(s);
                    }
                });

        rxRetrofit();
    }

    private void rxRetrofit() {
        final Call<HomeAdModule> adCall = ApiHelper.requestSportAd();
        ObservableOnSubscribe<HomeAdModule> source = new ObservableOnSubscribe<HomeAdModule>() {
            @Override
            public void subscribe(ObservableEmitter<HomeAdModule> e) throws Exception {
                try {
                    Response<HomeAdModule> adModuleResponse = adCall.execute();
                    HomeAdModule body = adModuleResponse.body();
                    if (body != null) {
                        Log.d("RxjavaActivity", "body:" + body.getMsg());
                    } else {
                        Log.d("RxjavaActivity", "response is null");
                    }
                    e.onNext(body);
                } catch (Exception error) {
                    Log.e("RxjavaActivity", "error:" + error);
                    e.onError(error);
                }

                e.onComplete();
            }
        };
        Observable<HomeAdModule> homeAdModuleObservable = Observable.create(source);


        homeAdModuleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<HomeAdModule, HomeAdModule>() {
                    @Override
                    public HomeAdModule apply(@NonNull HomeAdModule homeAdModule) throws Exception {
                        if (homeAdModule.getCode().equals("0")){
                            return homeAdModule;
                        } else{
                            return null;
                        }
                    }
                }).subscribe(new Observer<HomeAdModule>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("RxjavaActivity", d.toString()+" d");
                    }

                    @Override
                    public void onNext(HomeAdModule homeAdModule) {
                        if (homeAdModule.getCode().equals("0")) {
                            Toast.makeText(RxjavaActivity.this, homeAdModule.getMsg()+"!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RxjavaActivity.this, homeAdModule.getMsg()+"!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RxjavaActivity", "e:" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("RxjavaActivity", "complete");
                    }
        });
    }
}

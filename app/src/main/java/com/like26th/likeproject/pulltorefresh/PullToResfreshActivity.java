package com.like26th.likeproject.pulltorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.like26th.likeproject.R;
import com.like26th.likeproject.utils.LocalDisplay;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2017/3/20.
 */

public class PullToResfreshActivity extends Activity {
//    @BindView(R.id.store_house_ptr_frame)
//    PtrFrameLayout mPtrFrame;
//    @BindView(R.id.fragment_ptr_home_ptr_frame)
//    PtrFrameLayout fragmentPtrHomePtrFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        ButterKnife.bind(this);

//        mPtrFrame.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return true;
//            }
//            @Override
//            public void onRefreshBegin(final PtrFrameLayout frame) {
//                frame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        frame.refreshComplete();
//                    }
//                }, 100);
//            }
//        });

        final PtrFrameLayout ptrFrameLayout = (PtrFrameLayout)findViewById(R.id.fragment_ptr_home_ptr_frame);
        StoreHouseHeader header = new StoreHouseHeader(getApplicationContext());
        View view = View.inflate(getApplicationContext(), R.layout.item_refresh_head, null);
        header.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        header.initWithString("Ultra PTR");
        header.setTextColor(getResources().getColor(R.color.colorAccent));
        ptrFrameLayout.setDurationToCloseHeader(1500);
        ptrFrameLayout.setHeaderView(view);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setEnabledNextPtrAtOnce(true);
        ptrFrameLayout.setPullToRefresh(false);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                }, 10000);
            }
        });
    }
}

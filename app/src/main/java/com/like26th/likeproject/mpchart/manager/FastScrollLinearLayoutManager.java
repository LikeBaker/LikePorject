package com.like26th.likeproject.mpchart.manager;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Administrator on 2017/3/21.
 */

public class FastScrollLinearLayoutManager extends LinearLayoutManager {

    public FastScrollLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()){

            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                Log.d("FastScrollLinearLayoutM", "targetPosition:" + targetPosition);
                targetPosition = 10;
                return super.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                //计算滑动速度，默认25毫秒每像素

//                return super.calculateSpeedPerPixel(displayMetrics);
                return 1f;
            }

            @Override
            protected int calculateTimeForScrolling(int dx) {
                if (dx > 10) {
                    dx = 10;
                }

                return super.calculateTimeForScrolling(dx);
            }
        };

        linearSmoothScroller.setTargetPosition(position);
        Log.d("FastScrollLinearLayoutM", "position:" + position);
        startSmoothScroll(linearSmoothScroller);
    }
}

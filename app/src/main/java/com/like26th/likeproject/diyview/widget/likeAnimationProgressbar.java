package com.like26th.likeproject.diyview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.like26th.likeproject.App;

/**
 * Created by Administrator on 2017/2/28.
 */

public class likeAnimationProgressbar extends ViewGroup {

    private TextView indicator;
    private ProgressBar progressBar;

    private float percent;

    public void setPercent(float percent) {
        this.percent = percent;

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 0);
        translateAnimation.setDuration(2000);
        translateAnimation.setFillAfter(true);
        indicator.setAnimation(translateAnimation);
        indicator.startAnimation(translateAnimation);
    }

    public likeAnimationProgressbar(Context context) {
        super(context);
    }

    public likeAnimationProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        indicator = (TextView) getChildAt(0);
        progressBar = (ProgressBar) getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        LayoutParams indicatorLayoutParams = indicator.getLayoutParams();
        int indicatorWid = MeasureSpec.makeMeasureSpec(indicatorLayoutParams.width, MeasureSpec.EXACTLY);
        int indicatorHei = MeasureSpec.makeMeasureSpec(indicatorLayoutParams.height, MeasureSpec.EXACTLY);
        indicator.measure(indicatorWid, indicatorHei);

        LayoutParams progressBarLayoutParams = progressBar.getLayoutParams();
        int progressbarWid = MeasureSpec.makeMeasureSpec(progressBarLayoutParams.width, MeasureSpec.EXACTLY);
        int progressbarHei = MeasureSpec.makeMeasureSpec(progressBarLayoutParams.height, MeasureSpec.EXACTLY);
        progressBar.measure(progressbarWid, progressbarHei);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int indicatorWidth = indicator.getMeasuredWidth();
        int indicatorHeight = indicator.getMeasuredHeight();
        indicator.layout(10, 10, indicatorWidth - 10, indicatorHeight);

        //此处当宽度为match_parent,measure时，width返回值为-1，暂时用屏幕宽度代替match_parent
        WindowManager wm = (WindowManager) App.context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        int progressbarWidth = progressBar.getMeasuredWidth();
        int progressbarHeight = progressBar.getMeasuredHeight();
        progressBar.layout(10, indicatorHeight, width-10, indicatorHeight + progressbarHeight - 10);
    }
}

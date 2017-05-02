package com.like26th.likeproject.diyview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.view.View.MeasureSpec.EXACTLY;

/**
 * Created by Administrator on 2017/2/27.
 */

public class DiyGroupView extends ViewGroup {

    private View viewOne;
    private View viewTwo;

    public DiyGroupView(Context context) {
        super(context);
    }

    public DiyGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        //xml 布局文件解析完毕后调用
        super.onFinishInflate();
        viewOne = getChildAt(0);
        viewTwo = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        LayoutParams layoutParams = viewOne.getLayoutParams();
        int viewOneWidth = MeasureSpec.makeMeasureSpec(layoutParams.width, EXACTLY);
        int viewTwoHeight = MeasureSpec.makeMeasureSpec(layoutParams.height, EXACTLY);
        viewOne.measure(viewOneWidth, viewTwoHeight);

        LayoutParams layoutParams1 = viewTwo.getLayoutParams();
        int twoWidth = MeasureSpec.makeMeasureSpec(layoutParams1.width, EXACTLY);
        int twoheight = MeasureSpec.makeMeasureSpec(layoutParams1.height, EXACTLY);
        viewTwo.measure(twoWidth, twoheight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int oneMeasuredWidth = viewOne.getMeasuredWidth();
        int oneMeasuredHeight = viewOne.getMeasuredHeight();
        viewOne.layout(0, 0, oneMeasuredWidth, oneMeasuredHeight);


        int twoMeasuredWidth = viewTwo.getMeasuredWidth();
        int twoMeasuredHeight = viewTwo.getMeasuredHeight();
        viewTwo.layout(0, oneMeasuredHeight, twoMeasuredWidth, oneMeasuredHeight + twoMeasuredHeight);
    }
}

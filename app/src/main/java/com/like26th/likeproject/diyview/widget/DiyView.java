package com.like26th.likeproject.diyview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/2/27.
 */

public class DiyView extends View {

    //创建View时被调用
    public DiyView(Context context) {
        super(context);
    }

    public DiyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1); // 笔画大小

        canvas.drawLine(30, 30, 60, 120, paint);

    }
}

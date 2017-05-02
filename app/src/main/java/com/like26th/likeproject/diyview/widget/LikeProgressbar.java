package com.like26th.likeproject.diyview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.like26th.likeproject.R;

/**
 * Created by Administrator on 2017/3/1.
 */

public class LikeProgressbar extends View{
    public LikeProgressbar(Context context) {
        super(context);
    }

    public LikeProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.item_progressbar, null);
    }

    /**
     * 根据得分选择动画
     * @param percent 得分
     */
    private void moveAnimation(float percent) {

    }
}

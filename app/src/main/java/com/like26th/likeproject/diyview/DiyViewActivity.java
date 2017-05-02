package com.like26th.likeproject.diyview;

import android.app.Activity;
import android.os.Bundle;

import com.like26th.likeproject.R;
import com.like26th.likeproject.diyview.widget.likeAnimationProgressbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/24.
 */

public class DiyViewActivity extends Activity {
    @BindView(R.id.likePorgressbar)
    likeAnimationProgressbar likePorgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diyview);
        ButterKnife.bind(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        likePorgressbar.setPercent(1);
    }
}

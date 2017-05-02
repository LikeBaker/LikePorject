package com.like26th.likeproject.fontawesome;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.like26th.likeproject.R;
import com.like26th.likeproject.manager.FontManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/13.
 */

public class FontAwesomeActivity extends Activity {
    @BindView(R.id.tv_font)
    TextView tvFont;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fontawesome);
        ButterKnife.bind(this);

        FontManager.markAsIconContainer(new View[]{tvFont});
    }
}

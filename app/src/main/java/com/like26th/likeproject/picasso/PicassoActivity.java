package com.like26th.likeproject.picasso;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.like26th.likeproject.App;
import com.like26th.likeproject.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/21.
 */
public class PicassoActivity extends Activity {
    public static final String imgUrl = "http://s2.buzzhand.net/uploads/4b/a/758292/14366857171227.jpg";
//    public static final String imgUrl = "http://s2.buzzhand.net/uploads/4b/a/758292/1436685717122.jpg";

    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        ButterKnife.bind(this);

        Picasso.with(App.context).load(imgUrl).placeholder(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.xsearch_loading).error(R.mipmap.ic_launcher).into(img);
    }
}

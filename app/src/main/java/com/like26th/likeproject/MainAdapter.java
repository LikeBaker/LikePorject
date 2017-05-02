package com.like26th.likeproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.like26th.likeproject.chat.ChatActivity;
import com.like26th.likeproject.diyview.DiyViewActivity;
import com.like26th.likeproject.fileprovide.InitApkActivity;
import com.like26th.likeproject.fontawesome.FontAwesomeActivity;
import com.like26th.likeproject.greendao.GreenDaoActivity;
import com.like26th.likeproject.mpchart.MpChartActivity;
import com.like26th.likeproject.picasso.PicassoActivity;
import com.like26th.likeproject.pulltorefresh.PullToResfreshActivity;
import com.like26th.likeproject.rxjava.RxjavaActivity;

/**
 * Created by Administrator on 2017/2/16.
 */

public class MainAdapter extends RecyclerView.Adapter {

    @BindView(R.id.item_tv)
    TextView itemTv;
    private Activity activity;
    private String[] arrs;

    private int tempPos;

    public MainAdapter(Activity activity, String[] list) {
        this.activity = activity;
        this.arrs = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Viewholder(View.inflate(activity, R.layout.item_main, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Viewholder vh = (Viewholder) holder;
        vh.item.setText(arrs[position]);
        vh.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0 : activity.startActivity(new Intent(activity, RxjavaActivity.class));break;
                    case 1 : activity.startActivity(new Intent(activity, PicassoActivity.class));break;
                    case 2 : activity.startActivity(new Intent(activity, MpChartActivity.class));break;
                    case 3 : activity.startActivity(new Intent(activity, DiyViewActivity.class));break;
                    case 4 : activity.startActivity(new Intent(activity, GreenDaoActivity.class));break;
                    case 5 : activity.startActivity(new Intent(activity, ChatActivity.class));break;
                    case 6 : activity.startActivity(new Intent(activity, PullToResfreshActivity.class));break;
                    case 7 : activity.startActivity(new Intent(activity, InitApkActivity.class));break;
                    case 8 : activity.startActivity(new Intent(activity, FontAwesomeActivity.class));break;
                    default:break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrs.length;
    }

    class Viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_tv)
        TextView item;

        public Viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

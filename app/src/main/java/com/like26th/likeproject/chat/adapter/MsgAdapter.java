package com.like26th.likeproject.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.like26th.likeproject.R;
import com.like26th.likeproject.chat.ChatActivity;
import com.like26th.likeproject.chat.module.Msg;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/15.
 */

public class MsgAdapter extends RecyclerView.Adapter {
    private ChatActivity chatActivity;
    private List<Msg> list;

    public MsgAdapter(ChatActivity _chatActivity, List<Msg> _list) {
        this.chatActivity = _chatActivity;
        this.list = _list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_LEFT.ordinal()) {
            return new LeftHolder(View.inflate(chatActivity, R.layout.item_left_msg, null));
        } else if (viewType == ITEM_TYPE.ITEM_RIGHT.ordinal()) {
            return new LeftHolder(View.inflate(chatActivity, R.layout.item_right_msg, null));
        } else {
            return new HintHolder(View.inflate(chatActivity, R.layout.item_hint, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LeftHolder) {
            LeftHolder leftHolder = (LeftHolder) holder;
            leftHolder.setIsRecyclable(false);
            Msg msg = list.get(position);
            leftHolder.msg.setText(msg.getMsg());
            leftHolder.clickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chatActivity.clearEdFocus();
                }
            });
        } else if (holder instanceof RightHolder) {
            RightHolder rightHolder = (RightHolder) holder;
            rightHolder.setIsRecyclable(false);
            Msg msg = list.get(position);
            rightHolder.msg.setText(msg.getMsg());
            rightHolder.clickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chatActivity.clearEdFocus();
                }
            });
        } else {
            //hint
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = list.get(position).getType();
        if (type == 0) {
            return ITEM_TYPE.ITEM_LEFT.ordinal();
        } else {
            return ITEM_TYPE.ITEM_RIGHT.ordinal();
        }
    }

    public void add(int pos) {
        notifyItemInserted(pos);
        notifyDataSetChanged();
    }

    class LeftHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head)
        ImageView head;
        @BindView(R.id.msg)
        TextView msg;
        @BindView(R.id.click_layout)
        RelativeLayout clickLayout;

        LeftHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class RightHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head)
        ImageView head;
        @BindView(R.id.msg)
        TextView msg;
        @BindView(R.id.click_layout)
        RelativeLayout clickLayout;

        public RightHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HintHolder extends RecyclerView.ViewHolder {

        public HintHolder(View itemView) {
            super(itemView);
        }
    }

    private enum ITEM_TYPE {
        ITEM_LEFT, ITEM_RIGHT, ITEM_HINT;
    }
}

package com.like26th.likeproject.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like26th.likeproject.R;
import com.like26th.likeproject.chat.ChatActivity;
import com.like26th.likeproject.manager.FontManager;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ItemPageGridEmojiAdapter extends RecyclerView.Adapter {

    private ChatActivity mActivity;

    public ItemPageGridEmojiAdapter(ChatActivity _activity) {
        this.mActivity = _activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(View.inflate(mActivity, R.layout.item_emoji, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler vh = (ViewHodler) holder;
        if (position == 20) {
            vh.emoji.setTextSize(15);
            FontManager.markAsIconContainer(new View[]{vh.emoji});

            vh.clickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.remove();
                }
            });

        } else {
            vh.emoji.setText(FontManager.getEmojiText(position));
            vh.clickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.appendEdContent(vh.emoji.getText().toString().trim());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 21;
    }

    private class ViewHodler extends RecyclerView.ViewHolder{

        TextView emoji;
        RelativeLayout clickLayout;

        public ViewHodler(View itemView) {
            super(itemView);
            emoji = (TextView) itemView.findViewById(R.id.tv_emoji);
            clickLayout = (RelativeLayout) itemView.findViewById(R.id.click_layout);
        }
    }
}

package com.like26th.likeproject.chat.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.like26th.likeproject.chat.ChatActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public class EmojiAdapter extends PagerAdapter {
    private ChatActivity mActivity;
    private List<View> mlist;

    public EmojiAdapter(ChatActivity chatActivity, List<View> emojiList) {
        this.mActivity = chatActivity;
        this.mlist = emojiList;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(mlist.get(position));
        return mlist.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mlist.get(position));
    }
}

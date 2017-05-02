package com.like26th.likeproject.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like26th.likeproject.App;

/**
 * Created by liuzhen000 on 2016/6/30
 */
public class FontManager {
    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    private static Typeface typeface;

    public static void init(){
        typeface = getTypeface(App.context, FONTAWESOME);
    }

    public static Typeface getTypeface(Context context, String font) {
        typeface = Typeface.createFromAsset(context.getAssets(), font);
        return typeface;
    }

    public static void markAsIconContainer(View v, Typeface typeface) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child, typeface);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTypeface(typeface);
        }
    }

    public static void  markAsIconContainer(View[] views){
        for(int i=0; i<views.length; i++){
            View v = views[i];
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int j = 0; j < vg.getChildCount(); j++) {
                    View child = vg.getChildAt(j);
                    markAsIconContainer(child, typeface);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(typeface);
            }
        }
    }

    /**
     * 获取emoji表情
     * @param position
     * @return
     */
    public static String getEmojiText(int position){
        position ++;
        int unicodeJoy = 0;
        switch (position) {
            case 1:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_one));
                unicodeJoy = 0x1F601;
                break;
            case 2:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_two));
                unicodeJoy = 0x1F602;
                break;
            case 3:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_thi));
                unicodeJoy = 0x1F603;
                break;
            case 4:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_for));
                unicodeJoy = 0x1F604;
                break;
            case 5:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_fiv));
                unicodeJoy = 0x1F605;
                break;
            case 6:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_six));
                unicodeJoy = 0x1F606;
                break;
            case 7:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_sev));
                unicodeJoy = 0x1F607;
                break;
            case 8:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_eig));
                unicodeJoy = 0x1F608;
                break;
            case 9:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_nin));
                unicodeJoy = 0x1F609;
                break;
            case 10:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_ten));
                unicodeJoy = 0x1F60A;
                break;
            case 11:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_ele));
                unicodeJoy = 0x1F60B;
                break;
            case 12:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_twenty_one));
                unicodeJoy = 0x1F60C;
                break;
            case 13:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_thiteen));
                unicodeJoy = 0x1F60D;
                break;
            case 14:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_forteen));
                unicodeJoy = 0x1F60F;
                break;
            case 15:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_fivteen));
                unicodeJoy = 0x1F612;
                break;
            case 16:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_sixteen));
                unicodeJoy = 0x1F613;
                break;
            case 17:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_sevteen));
                unicodeJoy = 0x1F614;
                break;
            case 18:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_eigteen));
                unicodeJoy = 0x1F616;
                break;
            case 19:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_ninteen));
                unicodeJoy = 0x1F618;
                break;
            case 20:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_twenty));
                unicodeJoy = 0x1F61A;
                break;
//            case 21:
//                unicodeJoy = Integer.parseInt(App.mContext.getResources().getString(R.string.emoji_twenty_one));
//                unicodeJoy = 0x1F621;
//                break;
            default:break;
        }

        if (position == 21) {
            return "&#xf060;";
        }

        String emojiString = new String(Character.toChars(unicodeJoy));
        return emojiString;
    }
}

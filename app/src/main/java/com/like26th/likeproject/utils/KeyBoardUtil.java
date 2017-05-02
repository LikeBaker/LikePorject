package com.like26th.likeproject.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.like26th.likeproject.App;

/**
 * Created by Administrator on 2017/4/11.
 */

public class KeyBoardUtil {

    /**
     * 显示或隐藏键盘
     */
    public static void showOrHideKeyboard(){
        InputMethodManager imm = (InputMethodManager) App.context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏键盘
     */
    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) App.context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 弹出键盘
     */
    public static void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) App.context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }
}

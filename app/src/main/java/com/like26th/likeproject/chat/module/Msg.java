package com.like26th.likeproject.chat.module;

/**
 * Created by Administrator on 2017/3/15.
 */

public class Msg {
    private int type;
    private String Msg;

    public Msg(int type, String msg) {
        this.type = type;
        Msg = msg;
    }

    /**
     * 0 left, 1 right
     * @return left or right
     */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}

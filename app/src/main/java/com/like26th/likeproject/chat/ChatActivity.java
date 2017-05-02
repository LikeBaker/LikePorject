package com.like26th.likeproject.chat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.like26th.likeproject.App;
import com.like26th.likeproject.R;
import com.like26th.likeproject.chat.adapter.EmojiAdapter;
import com.like26th.likeproject.chat.adapter.ItemPageGridEmojiAdapter;
import com.like26th.likeproject.chat.adapter.MsgAdapter;
import com.like26th.likeproject.chat.module.Msg;
import com.like26th.likeproject.manager.JsonManager;
import com.like26th.likeproject.util.preference.PreferenceKey;
import com.like26th.likeproject.utils.KeyBoardUtil;
import com.like26th.likeproject.utils.LocalDisplay;
import com.like26th.likeproject.utils.StringUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ChatActivity extends Activity {

    public static final String CHATBROADCAST = "chatbroadcase";
    public static final String CHATBCTYPE = "chatbctype";
    public static final String CHATREFRESHTITLE = "chatrefreshtitle";
    public static final String CHATCLOSE = "chatclose";
    public static final String CHATMSG = "chatmsg";
    public static final String CHATRECEIVEMSG = "chatreceivemsg";

    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.input_layout)
    RelativeLayout input_layout;
    @BindView(R.id.rv_msg)
    RecyclerView rvMsg;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.ed_msg)
    EditText edMsg;
    @BindView(R.id.fragment_ptr_home_ptr_frame)
    PtrFrameLayout ptrFrameLayout;
    @BindView(R.id.btn_emoji)
    Button btnEmoji;
    @BindView(R.id.vp_emoji)
    ViewPager vpEmoji;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_msg_bubble)
    TextView tvMsgBubble;
//    @BindView(R.id.rv_emoji)
//    RecyclerView emojiRv;

    private MsgAdapter msgAdapter;
    private ArrayList<Msg> list;
    private LinearLayoutManager layoutManager;
    private boolean isBottomPos;
    //    private FastScrollLinearLayoutManager fastScrollLinearLayoutManager;
    private List<OnSoftKeyboardStateChangedListener> mKeyboardStateListener;
    private boolean isSofrKeyboardShow;
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean isKeyBoardshow;
    private boolean isKeyboardShowing;
    private boolean showEmojiLayout;
    private RecyclerView.LayoutManager layoutManager1;

    private ChatService chatService;
    private ServiceConnection serviceConnection;
    private ChatActivity.chatBrocastReceiver chatBrocastReceiver;
    private int unreadMsgCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        renderingUI();
        initListener();
        initRefreshLayout();
        initSofrKeyboardStateListener();
        bindChatService();
    }

    private void bindChatService() {

        Intent intent = new Intent(this, ChatService.class);
//        intent.putExtra(Constant.CHAT_INDEX, index);
        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                chatService = ((ChatService.LocalBinder) iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        chatBrocastReceiver = new chatBrocastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CHATBROADCAST);
        registerReceiver(chatBrocastReceiver, intentFilter);
    }

    public void setTitleName(final String name) {
        Log.d("ChatActivity", "name " + name);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleName.setText(name);
            }
        });
    }

    private void initSofrKeyboardStateListener() {
        mKeyboardStateListener = new ArrayList<>();
        isSofrKeyboardShow = false;
        //判断窗口可见区域大小
        //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态
        //获取屏幕高度
        mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态
                WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();//获取屏幕高度
                int heiDifference = screenHeight - (r.bottom - r.top);
                //键盘高度为740
                isKeyboardShowing = heiDifference > screenHeight / 3;
                if (isKeyboardShowing) {
//                    Log.d("ChatActivity", "keyboard is showing");
                    isKeyBoardshow = true;
                    if (showEmojiLayout) {
//                        if (emojiRv.getVisibility() == View.VISIBLE) {
//                            emojiRv.setVisibility(View.GONE);
//                        }
                    }
                    if (layoutManager != null && list != null) {
                        if (layoutManager.findLastVisibleItemPosition() != list.size() - 1) {
                            layoutManager.scrollToPosition(list.size() - 1);
                        }
                    }
                } else {
                    isKeyboardShowing = false;
                    if (showEmojiLayout) {
//                        if (emojiRv.getVisibility() == View.GONE) {
//                            emojiRv.setVisibility(View.VISIBLE);
//                        }
                    }
//                    Log.d("ChatActivity", "keyboard isn't showing");
//                    if (layoutManager != null && list != null) {
//                        if (layoutManager.findLastVisibleItemPosition() != list.size() -1) {
//                            layoutManager.scrollToPosition(list.size() -1);
//                        }
//                    }
                }

                //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示键盘的状态发生了变化
                if ((isKeyboardShowing && !isSofrKeyboardShow) || (!isKeyboardShowing && isSofrKeyboardShow)) {
                    isKeyboardShowing = isSofrKeyboardShow;
                    for (int i = 0; i < mKeyboardStateListener.size(); i++) {
                        OnSoftKeyboardStateChangedListener listener = mKeyboardStateListener.get(i);
                        listener.setSoftKeyBoardStateChanged(isKeyboardShowing, screenHeight);
                    }
                }
            }
        };

        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
    }

    private void initRefreshLayout() {
//        final PtrFrameLayout ptrFrameLayout = (PtrFrameLayout) findViewById(R.id.fragment_ptr_home_ptr_frame);
        StoreHouseHeader header = new StoreHouseHeader(getApplicationContext());
        View view = View.inflate(getApplicationContext(), R.layout.item_refresh_head, null);
        header.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        header.initWithString("Ultra PTR");
        header.setTextColor(getResources().getColor(R.color.colorAccent));
        ptrFrameLayout.setDurationToCloseHeader(100);
        ptrFrameLayout.setHeaderView(view);
        ptrFrameLayout.addPtrUIHandler(header);
//        ptrFrameLayout.setEnabledNextPtrAtOnce(true);
//        ptrFrameLayout.setPullToRefresh(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (vpEmoji.getVisibility() == View.VISIBLE) {
                vpEmoji.setVisibility(View.GONE);
                btnEmoji.setText("emoji");
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void initListener() {
        rvMsg.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = layoutManager.findLastVisibleItemPosition();
                if (pos == (list.size() - 1)) {
                    Log.d("ChatActivity", "is bottom");
                    isBottomPos = true;
                } else {
                    Log.d("ChatActivity", "isn't bottom");
                    isBottomPos = false;
                }

            }
        });

        //切换表情输入框
        edMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
//                    Log.d("ChatActivity", "ed 获取焦点");
                    vpEmoji.setVisibility(View.GONE);
                    btnEmoji.setText("emoji");
                } else {
//                    Log.d("ChatActivity", "ed 失去焦点");
                }
            }
        });

        //下拉刷新
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ptrFrameLayout.refreshComplete();
                        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                        int firstVisibleItemPosition = fastScrollLinearLayoutManager.findFirstVisibleItemPosition();
//                        int lastVisibleItemPosition = fastScrollLinearLayoutManager.findLastVisibleItemPosition();
//                        int screenItemCount = lastVisibleItemPosition - firstVisibleItemPosition;
//                        int lastSize = list.size();
                        list.add(0, new Msg(1, "add data"));
                        list.add(0, new Msg(0, "add data"));
                        list.add(0, new Msg(1, "add data"));
                        list.add(0, new Msg(0, "add data"));
                        msgAdapter.add(list.size());
//                        rvMsg.scrollToPosition(5);
                        int itemCount = msgAdapter.getItemCount();
                        //position位置为当前可见为条目最低部的位置
//                        rvMsg.scrollToPosition(screenItemCount + 3);
                        int firstVisibleItemPosition1 = layoutManager.findFirstVisibleItemPosition();
                        int lastVisibleItemPosition1 = layoutManager.findLastVisibleItemPosition();
                        Log.d("ChatActivity", "lastVisibleItemPosition1:" + lastVisibleItemPosition1);
                        Log.d("ChatActivity", "list.size():" + list.size());
                        int visibilityCount = lastVisibleItemPosition1 - firstVisibleItemPosition;

                        RecyclerView.LayoutManager layoutManager = rvMsg.getLayoutManager();

                        layoutManager.scrollToPosition(visibilityCount + 2);
                    }
                }, 1000);
            }
        });
    }

    private void renderingUI() {
        list = new ArrayList<>();
        // TODO: 2017/4/17 读取历史记录 一次读取固定条数
//        for (int i = 0; i < 30; i++) {
//            list.add(i, new Msg(0, "this is a left msg " + i));
//        }
//
//        for (int i = 30; i < 60; i++) {
//            list.add(i, new Msg(1, "this, is a right msg " + i));
//        }

        layoutManager = new LinearLayoutManager(this);
//        fastScrollLinearLayoutManager = new FastScrollLinearLayoutManager(this);
        rvMsg.setLayoutManager(layoutManager);
        msgAdapter = new MsgAdapter(this, list);
        rvMsg.setAdapter(msgAdapter);
        //每次进入页面后都定位到最后的位置
        layoutManager.scrollToPosition(list.size() - 1);

        //emoji
        btnEmoji.setText("emoji");
        List<View> emojiList = new ArrayList<>();
        emojiList.add(getiEmojiView(0));
        vpEmoji.setAdapter(new EmojiAdapter(this, emojiList));
        ViewGroup.LayoutParams layoutParams = vpEmoji.getLayoutParams();
        layoutParams.height = 743;
        vpEmoji.setLayoutParams(layoutParams);
    }

    private View getiEmojiView(int i) {
        if (i == 0) {
            View view = View.inflate(this, R.layout.item_emoji_rv, null);
            RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_page_emoji);
            rv.setLayoutManager(new GridLayoutManager(this, 7));
            rv.setAdapter(new ItemPageGridEmojiAdapter(this));
//            RelativeLayout clickLayout = (RelativeLayout) view.findViewById(R.id.click_layout);
//            TextView
            return view;
        } else {
            return null;
        }
    }

    public void clearEdFocus() {
        edMsg.clearFocus();
        KeyBoardUtil.hideKeyboard(input_layout);
        vpEmoji.setVisibility(View.GONE);
        if (layoutManager != null && list != null) {
            layoutManager.scrollToPosition(list.size() - 1);
        }
    }

    /**
     * 添加一个字符
     *
     * @param str 添加字符
     */
    public void appendEdContent(String str) {
        edMsg.append(str);
    }

    /**
     * 删除一个字符
     */
    public void remove() {
        int index = edMsg.getSelectionStart();
        if (index > 0) {
            Editable editable = edMsg.getText();
//            if (StringUtil.containsEmoji(editable.toString())){
//                editable.delete(index-2, index);
//            } else {
//                editable.delete(index-1, index);
//            }

            CharSequence convertContent = edMsg.getText();
            //转化为SpannableStringBuilder对象
            SpannableStringBuilder sp = new SpannableStringBuilder(convertContent);
            //获取内容中所有的表情对象
            ImageSpan[] imageSpans = sp.getSpans(0, sp.length(), ImageSpan.class);
            System.out.println("imageSpans.length = " + imageSpans.length);

            for (int i = 0; i < imageSpans.length; i++) {
                ImageSpan span = imageSpans[i];
                int start = sp.getSpanStart(span);//表情字符串的第一个字符索引
                int end = sp.getSpanEnd(span);//表情字符串中的最后一个字符索引+1，例如表情"[微笑]"，start=0,end=4
                int spanLength = end - start;//表情字符串的长度
                Log.d("ChatActivity", "start:" + start);
                Log.d("ChatActivity", "end:" + end);
                Log.d("ChatActivity", "spanLength:" + spanLength);

            }

        }
    }

    @OnClick({R.id.btn_send, R.id.tv_hint, R.id.btn_emoji, R.id.ed_msg, R.id.fragment_ptr_home_ptr_frame, R.id.rv_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String message = edMsg.getText().toString().trim();
                if (message.length() <= 0) {
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }


                // TODO: 2017/4/17 发送消息
                try {
                    String serviceId = App.preferenceUtil.getString(PreferenceKey.CURRENTSERVICEID, null);
                    if (serviceId != null) {
                        JSONObject messageJson = JsonManager.getMessageJson(JsonManager.SEND, serviceId, "游客" + (654321), message);
                        chatService.sendMsg(messageJson);
                    } else {
                        // TODO: 2017/4/17 如果客服id 为null，则退出聊天
                    }
                } catch (Exception e) {
                    Log.e("ChatActivity", e.toString());
                }

                list.add(list.size(), new Msg(1, message));
                msgAdapter.add(list.size());
//                msgAdapter.notifyItemInserted(list.size());
//                if (isBottomPos) {
//                    rvMsg.smoothScrollToPosition(list.size());
//                } else {
//                    tvHint.setVisibility(View.VISIBLE);
//                }
//                rvMsg.smoothScrollToPosition(list.size());
//                int position = fastScrollLinearLayoutManager.findFirstVisibleItemPosition();
                //滚动到屏幕最下方
                int firstVisibleItemPosition = this.layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = this.layoutManager.findLastVisibleItemPosition();
                int screenVisibilityCount = lastVisibleItemPosition - firstVisibleItemPosition;
//                View view1 = rvMsg.getChildAt(position);
//                if (view1 != null) {
//                int top = view.getTop();
                layoutManager1 = rvMsg.getLayoutManager();
//                Log.d("ChatActivity", "screenVisibilityCount + 2:" + (screenVisibilityCount + 2));
                //条目的位置是固定的
//                layoutManager.scrollToPosition(list.size() - 1);
//                }
//                fastScrollLinearLayoutManager.smoothScrollToPosition(rvMsg, RecyclerView., list.size());
                rvMsg.scrollToPosition(list.size() - 1);
                edMsg.setText("");
                break;
            case R.id.tv_hint:
                rvMsg.smoothScrollToPosition(list.size());
                tvHint.setVisibility(View.GONE);
                break;
            case R.id.btn_emoji:
                edMsg.requestFocus();

                if (vpEmoji.getVisibility() == View.VISIBLE) {
                    vpEmoji.setVisibility(View.GONE);
                    btnEmoji.setText("emoji");
                    KeyBoardUtil.showKeyboard();
                } else {
                    KeyBoardUtil.hideKeyboard(input_layout);
                    vpEmoji.setVisibility(View.VISIBLE);
                    btnEmoji.setText("keyboard");
                    if (layoutManager != null && list != null) {
                        layoutManager.scrollToPosition(list.size() - 1);
                    }
                }

//                showEmojiLayout = true;
//                KeyBoardUtil.showKeyboard();
                break;
            case R.id.ed_msg:
                vpEmoji.setVisibility(View.GONE);
                btnEmoji.setText("emoji");
                break;
            case R.id.fragment_ptr_home_ptr_frame:
                edMsg.clearFocus();
                break;
            case R.id.rv_msg:
                edMsg.clearFocus();
                break;
            case R.id.tv_msg_bubble:
                //点击提示气泡 页面滚动到最后一个可见条目位置  int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                int firpos = this.layoutManager.findFirstVisibleItemPosition();
                int lastpos = this.layoutManager.findLastVisibleItemPosition();
                int screencount = lastpos - firpos;

                Log.d("chatBrocastReceiver", "lastVisibleItemPosition:" + lastpos);
                Log.d("chatBrocastReceiver", "list.size():" + list.size());

                RecyclerView.LayoutManager layoutManager = rvMsg.getLayoutManager();

                layoutManager.scrollToPosition(screencount + 2);


                tvMsgBubble.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /***********获取软键盘高度**********/
    public interface OnSoftKeyboardStateChangedListener {
        public void setSoftKeyBoardStateChanged(boolean isKeyBoardShow, int keyboardHeight);
    }

    //注册软键盘状态变化监听
    public void addSoftKeyboardStateChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener == null) {
            mKeyboardStateListener.add(listener);
        }
    }

    //取消软键盘变化监听
    public void removeSofrKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener == null) {
            mKeyboardStateListener.remove(listener);
        }
    }

    private void renderingEmoji() {
        //渲染表情库
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearEdFocus();
//        if (vpEmoji.getVisibility() == View.GONE) {
//            clearEdFocus();
//        } else {
////            clearEdFocus();
//            edMsg.requestFocus();
//            KeyBoardUtil.hideKeyboard(input_layout);
//        }
    }

    @Override
    protected void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutChangeListener);
        } else {
            getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(mLayoutChangeListener);
        }

        unregisterReceiver(chatBrocastReceiver);unbindService(serviceConnection);


        super.onDestroy();
    }

    private class chatBrocastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra(CHATBCTYPE);
            Log.d("chatBrocastReceiver", "receiver type" + type);
            if (type == null) {
                return;
            }

            switch (type) {
                case "usertotle":
                    setTitleName(intent.getStringExtra(CHATREFRESHTITLE));
                    break;
                case CHATCLOSE:
                    finish();
                    break;
                case "msg":
                    Toast.makeText(context, intent.getStringExtra(CHATMSG), Toast.LENGTH_SHORT).show();
                    break;
                case "msgto":
                    //收到消息
                    if (isBottomPos) {
                        unreadMsgCount = 0;
                        list.add(list.size(), new Msg(0, intent.getStringExtra(ChatActivity.CHATRECEIVEMSG)));
                        msgAdapter.add(list.size());

                        //position位置为当前可见为条目最低部的位置
//                        rvMsg.scrollToPosition(screenItemCount + 3);
                        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                        Log.d("chatBrocastReceiver", "lastVisibleItemPosition:" + lastVisibleItemPosition);
                        Log.d("chatBrocastReceiver", "list.size():" + list.size());

                        RecyclerView.LayoutManager layoutManager = rvMsg.getLayoutManager();

//                    layoutManager.scrollToPosition(visibilityCount + 2);
                    } else {
                        unreadMsgCount++;
                        list.add(list.size(), new Msg(0, intent.getStringExtra(ChatActivity.CHATRECEIVEMSG)));
                        msgAdapter.add(list.size());
                        //提示有未读消息，未读消息会累加
                        tvMsgBubble.setVisibility(View.VISIBLE);
                        tvMsgBubble.setText(String.valueOf(unreadMsgCount));
                    }

                    break;
                default:
                    break;
            }
        }
    }
}

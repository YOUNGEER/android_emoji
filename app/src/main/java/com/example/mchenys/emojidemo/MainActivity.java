package com.example.mchenys.emojidemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mchenys.emojidemo.adapter.EmojiVpAdapter;
import com.example.mchenys.emojidemo.utils.EmotionKeyboardSwitchHelper;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mEmojiBtn, mSendBtn;
    private EditText mMsgEdt;
    private TextView mInfoTv;
    private ViewPager mEmojiVp;
    private FrameLayout mEmojiFl;
    private LinearLayout mVpPointLl;
    private EmotionKeyboardSwitchHelper mEmotionKeyboardSwitchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmotionKeyboardSwitchHelper = EmotionKeyboardSwitchHelper.with(this);
        initView();
        initListener();
    }

    private void initListener() {
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfoTv.append(mMsgEdt.getText() + "\r\n");
                mMsgEdt.setText("");
            }
        });
        mEmotionKeyboardSwitchHelper.bind(mInfoTv, mMsgEdt, mEmojiBtn, mEmojiFl);
    }

    private void initView() {
        mEmojiBtn = (Button) findViewById(R.id.btn_emoji);
        mSendBtn = (Button) findViewById(R.id.btn_send);
        mMsgEdt = (EditText) findViewById(R.id.edt_msg);
        mInfoTv = (TextView) findViewById(R.id.tv_info);
        mEmojiVp = (ViewPager) findViewById(R.id.vp_emoji);
        mEmojiFl = (FrameLayout) findViewById(R.id.fl_emoji);
        mVpPointLl = (LinearLayout) findViewById(R.id.ll_point);
        initViewPager();
    }

    /**
     * 设置ViewPager表情
     */
    private void initViewPager() {
        EmojiVpAdapter adapter = new EmojiVpAdapter(this);
        mEmojiVp.setAdapter(adapter);
        //表情点击监听
        adapter.setOnEmojiClickListener(new EmojiVpAdapter.OnEmojiClickListener() {
            @Override
            public void onClick(String emoji) {
                if ("del".equals(emoji)) {
                    //表示点击的是删除按钮
                    KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL);
                    mMsgEdt.onKeyDown(KeyEvent.KEYCODE_DEL, event);
                } else {
                    mMsgEdt.append(emoji);
                }
            }
        });
        mEmojiVp.setCurrentItem(0);
        //关联指示器点
        adapter.setupWithPagerPoint(mEmojiVp, mVpPointLl);
    }


    @Override
    public void onBackPressed() {
        if (mEmotionKeyboardSwitchHelper.onBackPress()) {
            super.onBackPressed();
        }
    }
}

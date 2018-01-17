package com.koudai.operate.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.model.UserMoneyBean;
import com.koudai.operate.utils.FormatUtil;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class RollingUpTextView extends LinearLayout {
    private TextView tv_above;
    private TextView tv_below;
    private TranslateAnimation initAnim;
    private TranslateAnimation aboveAnim;
    private TranslateAnimation belowAnim;
    private int mCurrentIndex;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startAnim();
        }
    };
    private List<UserMoneyBean> mList = new ArrayList<>();
    private MyThread mThread;

    public RollingUpTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_rolling_textview, this, true);
        tv_above = (TextView) view.findViewById(R.id.tv_above);
        tv_below = (TextView) view.findViewById(R.id.tv_below);
        setAnimation();
    }

    public void setData(List<UserMoneyBean> list) {
        if (mList.size() == 0) {
            mList = list;
            startAnim();
        }
    }

    private void setAnimation() {
        initAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1
        );
        initAnim.setDuration(1000);
        initAnim.setFillAfter(true);
        aboveAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0
        );
        aboveAnim.setDuration(1000);
        aboveAnim.setFillAfter(true);
        belowAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, -2
        );
        belowAnim.setDuration(1000);
        belowAnim.setFillAfter(true);
    }

    public void startAnim() {
        tv_above.clearAnimation();
        tv_below.clearAnimation();
        mThread = new MyThread();
        mThread.start();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = mCurrentIndex % mList.size();
                        if (index % 2 == 0) {
                            tv_above.setText(FormatUtil.getStyleText(mList.get(index).toString()));
                            tv_below.setText(FormatUtil.getStyleText(mList.get((index + 1) % mList.size()).toString()));
                        } else {
                            tv_above.setText(FormatUtil.getStyleText(mList.get((index + 1) % mList.size()).toString()));
                            tv_below.setText(FormatUtil.getStyleText(mList.get(index).toString()));
                        }
                        if (mCurrentIndex % 2 == 0) {
                            tv_above.startAnimation(initAnim);
                            tv_below.startAnimation(initAnim);
                        } else {
                            tv_above.startAnimation(aboveAnim);
                            tv_below.startAnimation(belowAnim);
                        }
                    }
                });
                mCurrentIndex++;
                if (mCurrentIndex >= mList.size()) {
                    mCurrentIndex = 0;
                    break;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            mHandler.sendEmptyMessage(1);
        }
    }
}

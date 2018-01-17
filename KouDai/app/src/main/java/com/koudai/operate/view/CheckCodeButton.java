package com.koudai.operate.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class CheckCodeButton extends Button {
    public static final int REFRESH_TEXT = 3;
    private Handler handler = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            switch (paramAnonymousMessage.what) {
                default:
                    return;
                case 3:
            }
            CheckCodeButton.this.setText((String) paramAnonymousMessage.obj);
        }
    };
    private OnStateChanedListener mOnStateChanedListener;
    private int mResidueTime = this.timeout;
    private Timer mTimer;
    private int timeout = 60;

    public CheckCodeButton(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        setText("获取验证码");
    }

    public void setOnStateChanedListener(OnStateChanedListener paramOnStateChanedListener) {
        this.mOnStateChanedListener = paramOnStateChanedListener;
    }

    public void setTimeout(int paramInt) {
        this.timeout = paramInt;
    }

    public void start() {
        setEnabled(false);
        this.mTimer = new Timer();
        this.mTimer.schedule(new TimerTask() {
                                 public void run() {
//                                     CheckCodeButton.access$010(CheckCodeButton.this);
                                     if (CheckCodeButton.this.mResidueTime == 0) {
//                                         CheckCodeButton.access$002(CheckCodeButton.this, CheckCodeButton.this.timeout);
                                         CheckCodeButton.this.mTimer.cancel();
                                         Message localMessage2 = Message.obtain();
                                         localMessage2.what = 3;
                                         localMessage2.obj = "重新获取";
                                         CheckCodeButton.this.handler.sendMessage(localMessage2);
                                         CheckCodeButton.this.handler.post(new Runnable() {
                                             public void run() {
                                                 CheckCodeButton.this.setEnabled(true);
                                                 if (CheckCodeButton.this.mOnStateChanedListener != null)
                                                     CheckCodeButton.this.mOnStateChanedListener.onStop(CheckCodeButton.this);
                                             }
                                         });
                                         return;
                                     }
                                     Message localMessage1 = Message.obtain();
                                     localMessage1.what = 3;
                                     localMessage1.obj = (CheckCodeButton.this.mResidueTime + "秒后重发");
                                     CheckCodeButton.this.handler.sendMessage(localMessage1);
                                 }
                             }
                , 0L, 1000L);
    }

    public static abstract interface OnStateChanedListener {
        public abstract void onStop(CheckCodeButton paramCheckCodeButton);
    }
}
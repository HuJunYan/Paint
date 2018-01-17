package com.koudai.operate.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.model.ProGroupBean;

/**
 * Created by admin on 2016/8/21.
 */
public class ProItemView extends RelativeLayout {
    private Context mContext;
    private View mRootView;
    private View view_line;
    private TextView tv_pro_name;
    private TextView tv_new_price;
    private TextView tv_change_range;
    private LinearLayout ll_up;
    private LinearLayout ll_down;
    private boolean isChecked;
    private ProGroupBean mProInfo;
    private String name;
    private ImageView iv_up;
    private ImageView iv_down;
    private Handler mHandler = new Handler();
    private long mDuration = 800;
    private AnimationSet upAnimation = null;
    private AnimationSet downAnimation = null;

    public ProItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.view_pro_item, this, true);
        findViews();
        setImageAnimtion();
        startUpAnim();
        startDownAnim();
    }

    private void setImageAnimtion() {
        upAnimation = new AnimationSet(true);
        upAnimation.setDuration(mDuration);
        upAnimation.setStartTime(0);
        upAnimation.setInterpolator(new DecelerateInterpolator());
        AlphaAnimation alpha = new AlphaAnimation(0.1f, 1);
        alpha.setRepeatCount(Animation.INFINITE);
        alpha.setRepeatMode(Animation.RESTART);
        final TranslateAnimation ta1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.2f,
                Animation.RELATIVE_TO_SELF, 0
        );
        ta1.setRepeatMode(Animation.RESTART);
        ta1.setRepeatCount(Animation.INFINITE);
//        ta1.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                animation.setDuration(mDuration);
//            }
//        });
        upAnimation.addAnimation(ta1);
        upAnimation.addAnimation(alpha);

        downAnimation = new AnimationSet(true);
        downAnimation.setDuration(mDuration);
        downAnimation.setStartTime(0);
        AlphaAnimation alphadown = new AlphaAnimation(0.1f, 1);
        alphadown.setRepeatCount(Animation.INFINITE);
        alphadown.setRepeatMode(Animation.RESTART);
        TranslateAnimation ta1down = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -0.2f,
                Animation.RELATIVE_TO_SELF, 0
        );
        ta1down.setRepeatMode(Animation.RESTART);
        ta1down.setRepeatCount(Animation.INFINITE);
//        ta1down.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                animation.setDuration(mDuration);
//            }
//        });
        downAnimation.addAnimation(ta1down);
        downAnimation.addAnimation(alphadown);
    }

    private void startUpAnim() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iv_up.startAnimation(upAnimation);
                    }
                });
            }
        }).start();
    }

    private void startDownAnim() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iv_down.startAnimation(downAnimation);
                    }
                });
            }
        }).start();
    }

    private void findViews() {
        view_line = mRootView.findViewById(R.id.view_line);
        tv_pro_name = (TextView) mRootView.findViewById(R.id.tv_pro_name);
        tv_new_price = (TextView) mRootView.findViewById(R.id.tv_new_price);
        tv_change_range = (TextView) mRootView.findViewById(R.id.tv_change_range);
        iv_up = (ImageView) mRootView.findViewById(R.id.iv_up);
        iv_down = (ImageView) mRootView.findViewById(R.id.iv_down);
        ll_up = (LinearLayout) mRootView.findViewById(R.id.ll_up);
        ll_down = (LinearLayout) mRootView.findViewById(R.id.ll_down);
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
//        if (isChecked) {
//            setmDuration(800);
//        } else {
//            setmDuration(1200);
//        }
        view_line.setVisibility(isChecked ? VISIBLE : GONE);
    }

    //    new Thread(new Runnable() {
//        @Override
//        public void run() {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    AnimationSet animSet = new AnimationSet(true);
//                    animSet.setDuration(mDuration);
//                    animSet.setStartTime(0);
//                    animSet.setRepeatCount(Integer.MAX_VALUE);
//                    animSet.setRepeatMode(Animation.RESTART);
//                    AlphaAnimation alpha = new AlphaAnimation(0.5f, 1);
//                    TranslateAnimation ta1 = new TranslateAnimation(
//                            Animation.RELATIVE_TO_SELF, 0,
//                            Animation.RELATIVE_TO_SELF, 0,
//                            Animation.RELATIVE_TO_SELF, 0,
//                            Animation.RELATIVE_TO_SELF, 0.5f
//                    );
////                        ta1.setDuration(mDuration);
////                        ta1.setStartTime(0);
////                        ta1.setRepeatCount(Integer.MAX_VALUE);
////                        ta1.setRepeatMode(Animation.RESTART);
//                    animSet.addAnimation(ta1);
//                    animSet.addAnimation(alpha);
//                    iv_up.startAnimation(animSet);
//                }
//            });
//        }
//    }).start();


    public void setProInfo(ProGroupBean proInfo) {
        this.mProInfo = proInfo;
        tv_pro_name.setText(mProInfo.getPro_name());
        tv_new_price.setText(mProInfo.getLatest_price() + "");
        String rise = String.valueOf(mProInfo.getRise());
        if (mProInfo.getRise() >= 0) {
            rise = "+" + rise;
        }
        tv_change_range.setText(rise + "%");
        if (mProInfo.isShowUpOrDown()) {
            if (mProInfo.isUp()) {
                ll_up.setVisibility(VISIBLE);
                ll_down.setVisibility(INVISIBLE);
            } else {
                ll_up.setVisibility(INVISIBLE);
                ll_down.setVisibility(VISIBLE);
            }
        }
        if (mProInfo.getRise() >= 0) {
            tv_new_price.setTextColor(Color.parseColor("#FFF54337"));
            tv_change_range.setTextColor(Color.parseColor("#FFF54337"));
        } else {
            tv_new_price.setTextColor(Color.parseColor("#FF12bc65"));
            tv_change_range.setTextColor(Color.parseColor("#FF12bc65"));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        tv_pro_name.setText(name);
    }

    public void setmDuration(long mDuration) {
        this.mDuration = mDuration;
    }
}

package com.koudai.operate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.koudai.operate.R;

/**
 * Created by admin on 2016/8/5.
 */
public class ExpandableView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private View ll_group;
    private View ll_child;

    private boolean isDown = false;
    private boolean isEnd = true;

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.view_expandable, this, true);
        init();
    }

    private void init() {
        ll_child = mRootView.findViewById(R.id.ll_child);
        ll_group = mRootView.findViewById(R.id.ll_group);
        ll_group.setOnClickListener(this);
    }

    private void scaleAnim() {
        isEnd = false;
        if (ll_child.getVisibility() == GONE) {
            ll_child.setVisibility(VISIBLE);
        }
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, isDown ? 1 : 0, isDown ? 0 : 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(300l);
        isDown = !isDown;
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isEnd = true;
                if (!isDown) {
                    ll_child.setVisibility(GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animSet.addAnimation(scaleAnimation);
        ll_child.startAnimation(animSet);
    }

    @Override
    public void onClick(View view) {
        if (isEnd) {
            scaleAnim();
        }
    }
}

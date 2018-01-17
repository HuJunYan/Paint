package com.koudai.operate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koudai.operate.R;

/**
 * Created by admin on 2016/8/21.
 */
public class ScaleView extends RelativeLayout {
    private Context mContext;
    private View mRootView;

    private TextView tv_buy_up_scale;
    private TextView tv_buy_down_scale;
    private View view_up_scale;
    private View view_down_scale;
    private int mWidth;
    private int mUpScale;

    public ScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.view_scale, this, true);
        findViews();
    }

    public void setUpScale(int mUpScale) {
        this.mUpScale = mUpScale;
        tv_buy_up_scale.setText(mUpScale + "%");
        tv_buy_down_scale.setText((100 - mUpScale) + "%");
        if (mWidth != 0) {
            view_up_scale.getLayoutParams().width = mWidth * mUpScale / 100;
            view_down_scale.getLayoutParams().width = mWidth - (mWidth * mUpScale / 100);
        }
    }

    private void findViews() {
        tv_buy_up_scale = (TextView) mRootView.findViewById(R.id.tv_buy_up_scale);
        tv_buy_down_scale = (TextView) mRootView.findViewById(R.id.tv_buy_down_scale);
        view_up_scale = mRootView.findViewById(R.id.view_up_scale);
        view_down_scale = mRootView.findViewById(R.id.view_down_scale);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int width = this.getWidth();
        if (width != 0) {
            mWidth = width;
            view_up_scale.getLayoutParams().width = mWidth * mUpScale / 100;
            view_down_scale.getLayoutParams().width = mWidth - (mWidth * mUpScale / 100);
        }
    }
}

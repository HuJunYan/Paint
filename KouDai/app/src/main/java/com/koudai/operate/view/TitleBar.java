package com.koudai.operate.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;

/**
 * Created by Administrator on 2016/8/4.
 */
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private ImageView iv_left;
    private TextView tv_title;
    private TextView tv_right;
    private TextView tv_left;
    private Context mContext;
    private View view;
    private TitleBarListener mListener;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.view_title_bar, this);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        findViews(view);
        initData(attrs);
    }

    private void findViews(View view) {
        iv_left = (ImageView) view.findViewById(R.id.iv_left);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        tv_left = (TextView) view.findViewById(R.id.tv_left);
        iv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_left.setOnClickListener(this);
    }

    private void initData(AttributeSet attrs) {
        TypedArray arr = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        Drawable img = arr.getDrawable(R.styleable.TitleBar_tbImage);
        String text = arr.getString(R.styleable.TitleBar_tbText);
        String rightText = arr.getString(R.styleable.TitleBar_tbRightText);
        String leftText = arr.getString(R.styleable.TitleBar_tbLeftText);
        int color = arr.getColor(R.styleable.TitleBar_tbTextColor, 0);
        if (img != null) {
            iv_left.setImageDrawable(img);
            iv_left.setVisibility(View.VISIBLE);
        }
        if (text != null) {
            tv_title.setText(text);
        }
        if (rightText != null) {
            tv_right.setText(rightText);
            tv_right.setVisibility(VISIBLE);
        }
        if (leftText != null) {
            tv_left.setText(leftText);
            tv_left.setVisibility(VISIBLE);
        }
        if (color != 0) {
            tv_title.setTextColor(color);
            tv_right.setTextColor(color);
        }
        arr.recycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                ((Activity) mContext).finish();
                ((Activity) mContext).overridePendingTransition(R.anim.not_exit_push_left_in, R.anim.push_right_out);
                break;
            case R.id.tv_left:
                if (mListener != null) {
                    mListener.onLeftClick();
                }
                break;
            case R.id.tv_right:
                if (mListener != null) {
                    mListener.onRightClick();
                }
                break;
        }
    }

    public void setTitle(String text) {
        tv_title.setText(text);
    }

    public void setTieleBarListener(TitleBarListener listener) {
        mListener = listener;
    }

    public interface TitleBarListener {
        void onLeftClick();

        void onRightClick();
    }
}

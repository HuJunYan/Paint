package com.koudai.operate.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koudai.operate.R;

/**
 * Created by Administrator on 2016/8/13.
 */
public class NumberChooseView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private TextView tv_left;
    private TextView tv_value;
    private TextView tv_right;
    private View mRootView;

    private int mValue;
    private int mMinValue;
    private int mMaxValue;
    private int mRange;
    private String mSuffix;

    private ValueChangeListener mListener;

    public NumberChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_number_choose, this, true);
        init(attrs);
    }

    public void setOnValueChangeListener(ValueChangeListener mListener) {
        this.mListener = mListener;
    }

    private void init(AttributeSet attrs) {
        tv_left = (TextView) mRootView.findViewById(R.id.tv_left);
        tv_value = (TextView) mRootView.findViewById(R.id.tv_value);
        tv_right = (TextView) mRootView.findViewById(R.id.tv_right);

        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.NumberChooseView);
        mValue = array.getInt(R.styleable.NumberChooseView_ncvValue, 0);
        mMinValue = array.getInt(R.styleable.NumberChooseView_ncvMinValue, 0);
        mMaxValue = array.getInt(R.styleable.NumberChooseView_ncvMaxValue, 0);
        mRange = array.getInt(R.styleable.NumberChooseView_ncvRange, 0);
        String suffix = array.getString(R.styleable.NumberChooseView_ncvSuffix);
        mSuffix = suffix == null ? "" : suffix;
        tv_value.setText(mValue + mSuffix);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.getLayoutParams().width = this.getHeight() * 5;
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                if (mValue > mMinValue) {
                    mValue -= mRange;
                    changeValue();
                }
                break;
            case R.id.tv_right:
                if (mValue < mMaxValue) {
                    mValue += mRange;
                    changeValue();
                }
                break;
        }
    }

    private void changeValue() {
        tv_value.setText(mValue + mSuffix);
        if (mListener != null) {
            mListener.onValueChange(mValue);
        }
    }

    public void setValue(int value) {
        mValue = value;
        tv_value.setText(mValue + mSuffix);
    }

    public void setMaxValue(int maxValue) {
        if (mValue > maxValue) {
            mValue = maxValue;
            changeValue();
        }
        this.mMaxValue = maxValue;
    }

    public int getValue() {
        return mValue;
    }

    public interface ValueChangeListener {
        void onValueChange(int value);
    }
}

package com.koudai.operate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.koudai.operate.R;
import com.koudai.operate.model.ProGroupBean;
import com.koudai.operate.model.ProInfoItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/21.
 */
public class ProGroupView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private View mRootView;
    private View view2;

    private ProItemView pro_silver;
    private ProItemView pro_copper;
    private ProItemView pro_oil;

    private List<ProItemView> mListView;
    private List<ProGroupBean> mProList;
    private int checkedIndex;
    private boolean isClickAble = true;
    private OnCheckedChangedListener mListener;

    public ProGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.view_pro_group, this, true);
        findViews();
    }


    private void findViews() {
        view2 = mRootView.findViewById(R.id.view2);
        pro_silver = (ProItemView) mRootView.findViewById(R.id.pro_silver);
        pro_copper = (ProItemView) mRootView.findViewById(R.id.pro_copper);
        pro_oil = (ProItemView) mRootView.findViewById(R.id.pro_oil);
        mListView = new ArrayList<ProItemView>();
        mListView.add(pro_silver);
        mListView.add(pro_copper);
        mListView.add(pro_oil);
        pro_silver.setOnClickListener(this);
        pro_copper.setOnClickListener(this);
        pro_oil.setOnClickListener(this);
        setCheckedIndex(0);
    }

    private void setData() {
        if (mProList.size() == 2) {
            mListView.remove(2);
            pro_oil.setVisibility(GONE);
            view2.setVisibility(GONE);
        }
        for (int i = 0; i < mListView.size(); i++) {
            mListView.get(i).setProInfo(mProList.get(i));
        }
    }

    @Override
    public void onClick(View view) {
        if (!isClickAble) {
            return;
        }
        switch (view.getId()) {
            case R.id.pro_silver:
                setCheckedIndex(0);
                if (mListener != null) {
                    mListener.onCheckedChanged(0);
                }
                break;
            case R.id.pro_copper:
                setCheckedIndex(1);
                if (mListener != null) {
                    mListener.onCheckedChanged(1);
                }

                break;
            case R.id.pro_oil:
                setCheckedIndex(2);
                if (mListener != null) {
                    mListener.onCheckedChanged(2);
                }
                break;
        }
    }

    public interface OnCheckedChangedListener {
        void onCheckedChanged(int index);
    }

    public boolean isClickAble() {
        return isClickAble;
    }

    public void setChangeAble(boolean clickAble) {
        isClickAble = clickAble;
    }

    public void setOnCheckedChangedListener(OnCheckedChangedListener listener) {
        this.mListener = listener;
    }

    public List<ProGroupBean> getProList() {
        return mProList;
    }

    public void setProList(List<ProGroupBean> mProList) {
        this.mProList = mProList;
        if (mProList.size() > 0) {
            setData();
        }
    }

    public int getCheckedIndex() {
        return checkedIndex;
    }

    public void setCheckedIndex(int checkedIndex) {
        for (int i = 0; i < mListView.size(); i++) {
            mListView.get(i).setChecked(i == checkedIndex);
        }
        this.checkedIndex = checkedIndex;
    }
}

package com.koudai.operate.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.adapter.MyViewPagerAdapter;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.fragment.InviteFragment;
import com.koudai.operate.fragment.MyFragment;
import com.koudai.operate.fragment.OrderListFragment;
import com.koudai.operate.fragment.TradeFragment;
import com.koudai.operate.service.SocketService;
import com.koudai.operate.utils.TimeCount;
import com.koudai.operate.utils.ToastUtil;
import com.koudai.operate.utils.UserUtil;
import com.koudai.operate.utils.ViewUtil;
import com.koudai.operate.view.NoScrollViewPager;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private View iv_guide_reg;
    private RadioGroup rg_control_button;
    private RadioButton rb_trade, rb_order, rb_invite, rb_my;
    private TradeFragment mTradeFragment;
    private OrderListFragment mOrderFragment;
    private InviteFragment mInviteFragment;
    private MyFragment myFragment;
    private NoScrollViewPager vp_main;
    private ArrayList<Fragment> mFragmentList;
    private MyViewPagerAdapter mViewPagerAdapter;
    private Intent mIntent;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long time = 0;
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("isFirst")) {
            time = 200;
            ViewUtil.createLoadingDialog(this, "", true);
        }
        new TimeCount(time, time) {
            @Override
            public void onFinish() {
                initData();
            }
        }.start();
    }

    private void initData() {
        InitView();
        mIntent = new Intent(MainActivity.this, SocketService.class);
        startService(mIntent);
        vp_main.setNoScroll(true);
        sendBroadcast(new Intent("welcome"));
        if (!UserUtil.getIsGuideRegBig(this)) {
            UserUtil.setIsGuideRegBig(this, true);
            initDialog();
            mDialog.show();
        } else if (!UserUtil.getIsGuideReg(this)) {
            initDialog();
            iv_guide_reg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserUtil.getIsGuideReg(this)) {
            iv_guide_reg.setVisibility(View.GONE);
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        rg_control_button = (RadioGroup) findViewById(R.id.rg_control_button);
        rb_trade = (RadioButton) findViewById(R.id.rb_trade);
        rb_order = (RadioButton) findViewById(R.id.rb_order);
        rb_invite = (RadioButton) findViewById(R.id.rb_invite);
        rb_my = (RadioButton) findViewById(R.id.rb_my);
        vp_main = (NoScrollViewPager) findViewById(R.id.vp_main);
        iv_guide_reg = findViewById(R.id.iv_guide_reg);
    }

    @Override
    protected void setListensers() {
        rg_control_button.setOnCheckedChangeListener(this);
        vp_main.addOnPageChangeListener(this);
        iv_guide_reg.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        stopService(mIntent);
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                rb_trade.setChecked(true);
                break;
            case 1:
                rb_order.setChecked(true);
                break;
            case 2:
                rb_invite.setChecked(true);
                break;
            case 3:
                rb_my.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void addFragment() {
        if (mTradeFragment == null) {
            mTradeFragment = new TradeFragment();
        }
        if (mOrderFragment == null) {
            mOrderFragment = new OrderListFragment();
        }
        if (mInviteFragment == null) {
            mInviteFragment = new InviteFragment();
        }
        if (myFragment == null) {
            myFragment = new MyFragment();
        }

        if (mFragmentList == null) {
            mFragmentList = new ArrayList<Fragment>();// 初始化数据
            mFragmentList.add(mTradeFragment);
            mFragmentList.add(mOrderFragment);
            mFragmentList.add(mInviteFragment);
            mFragmentList.add(myFragment);
        }
    }

    private void setViewPager() {
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
            vp_main.setAdapter(mViewPagerAdapter);
            vp_main.setOffscreenPageLimit(4);
        }
    }

    private void InitView() {
        addFragment();
        setViewPager();
    }

    public void turnToPage(int index,boolean isScroll) {
        vp_main.setCurrentItem(index, isScroll);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_trade:
                vp_main.setCurrentItem(0, false);
                break;
            case R.id.rb_order:
                vp_main.setCurrentItem(1, false);
                break;
            case R.id.rb_invite:
                vp_main.setCurrentItem(2, false);
                break;
            case R.id.rb_my:
                vp_main.setCurrentItem(3, false);
                break;
        }
    }

    boolean isFirstBack = true;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isFirstBack) {
                isFirstBack = false;
                ToastUtil.showToast(mContext, "再按一次返回键退出");
                new TimeCount(3000, 3000) {
                    @Override
                    public void onFinish() {
                        isFirstBack = true;
                    }
                }.start();
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean isExtendsBaseOnKeyDown() {
        return false;
    }

    Dialog mDialog;
    TextView bt_close_guide;
    TextView bt_guide_reg;

    private void initDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(this, R.style.dialog);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_guide_reg, null);
            bt_close_guide = (TextView) view.findViewById(R.id.bt_close_guide);
            bt_guide_reg = (TextView) view.findViewById(R.id.bt_guide_reg);
            bt_close_guide.setOnClickListener(this);
            bt_guide_reg.setOnClickListener(this);
            mDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mDialog.setCanceledOnTouchOutside(false);
            Window window = mDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    iv_guide_reg.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_guide_reg:
                gotoActivity(this, RegisterActivity.class, null);
            case R.id.bt_close_guide:
                mDialog.cancel();
                break;
            case R.id.iv_guide_reg:
                iv_guide_reg.setVisibility(View.GONE);
                mDialog.show();
                break;
        }
    }
}

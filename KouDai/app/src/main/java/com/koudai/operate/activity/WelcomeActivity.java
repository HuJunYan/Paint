package com.koudai.operate.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.model.ResAccessTokenBean;
import com.koudai.operate.model.ResVersionBean;
import com.koudai.operate.net.api.AccessToken;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.LogUtil;
import com.koudai.operate.utils.TimeCount;
import com.koudai.operate.utils.UpdateVersionUtil;
import com.koudai.operate.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, UpdateVersionUtil.UpdateVersionListener {
    private ImageView iv_image;
    private ImageView iv_zero;
    private ImageView iv_first;
    private ImageView iv_second;
    private ImageView iv_third;
    private FrameLayout fl_welcome;
    private ViewPager vp_welcome;

    private TextView bt_welcome;
    private TextView tv_time;

    private List<View> mViewList = new ArrayList<>();
    private MyReceiver myReceiver;
    private UpdateVersionUtil mUpdateVersionUtil;
    private long startTime;
    private long endTime;
    private Handler mHandler = new Handler();
    private TimeCount mTimeCount;
    private final int PAGE_SIZE = 4;
    private Runnable mTimerTask = new Runnable() {


        @Override
        public void run() {
            if (vp_welcome.getCurrentItem() == PAGE_SIZE - 1) {
                bt_welcome.performClick();
            }
        }
    };
    private View zeroView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zeroView = LayoutInflater.from(this).inflate(R.layout.view_welcome_zero, null);
        ImageView iv_zero_img = (ImageView) zeroView.findViewById(R.id.iv_zero_img);
        switch (NetConstantValue.CLIENTID) {
            case "9":
                iv_image.setImageResource(R.mipmap.view_welcome_load_yyb);
                iv_zero_img.setImageResource(R.mipmap.view_welcome_load_yyb);
                LogUtil.d("ret", "zeroView==view_welcome_load_yyb");
                break;
            case "12":
                iv_image.setImageResource(R.mipmap.view_welcome_load_huawei);
                iv_zero_img.setImageResource(R.mipmap.view_welcome_load_huawei);
                LogUtil.d("ret", "zeroView==view_welcome_load_huawei");
                break;
            case "13":
                iv_image.setImageResource(R.mipmap.view_welcome_load_360);
                iv_zero_img.setImageResource(R.mipmap.view_welcome_load_360);
                LogUtil.d("ret", "zeroView==view_welcome_load_360");
                break;
            default:
                iv_image.setImageResource(R.mipmap.view_welcome_load);
                iv_zero_img.setImageResource(R.mipmap.view_welcome_load);
                LogUtil.d("ret", "zeroView==view_welcome_load");
                break;
        }
        UserUtil.setTicekt200(this, 0);
        UserUtil.setTicekt10(this, 0);
        UserUtil.setAvailableBalance(this, "0");
        if (UserUtil.getIsLogin(this)) {
            UserUtil.setIsGuideReg(mContext, true);
            UserUtil.setIsGuideRegBig(mContext, true);
        }
        if (!UserUtil.getIsOldUser(this) && !UserUtil.getIsLogin(this)) {
            loadViewPager();
        }
        startTime = System.currentTimeMillis();
        checkVersion();
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.welcome_orange;
    }

    private void checkVersion() {
        new AccessToken(this).checkUpdateVersion(new BaseNetCallBack<ResVersionBean>() {
            @Override
            public void onSuccess(ResVersionBean paramT) {
                String url = paramT.getResponse().getData().getUrl();
                if (url == null || url.equals("")) {
                    checkToken();
                } else {
                    mUpdateVersionUtil = new UpdateVersionUtil(WelcomeActivity.this, url, paramT.getResponse().getData().getContent(), String.valueOf(paramT.getResponse().getData().getIs_update()));
                    mUpdateVersionUtil.setUpdateVersionListener(WelcomeActivity.this);
                    mUpdateVersionUtil.checkUpdateInfo();
                }
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
                checkToken();
            }
        });
    }

    private void checkToken() {
        if (UserUtil.getIsLogin(this)) {
            UserUtil.setIsEnable(this, true);
            AccountUtil.setIsNeedAccess(false);
            new TimeCount(2000, 2000) {
                @Override
                public void onFinish() {
                    gotoActivity(WelcomeActivity.this, MainActivity.class, null);
                    WelcomeActivity.this.finish();
                }
            }.start();
        } else {
            if (UserUtil.getIsOldUser(this)) {
                getAccessToken(true);
            } else {
                getAccessToken(false);
            }
        }
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, new IntentFilter("welcome"));
    }

    private void loadViewPager() {
        iv_image.setVisibility(View.GONE);
        fl_welcome.setVisibility(View.VISIBLE);
        LayoutInflater inflater = LayoutInflater.from(this);
        mViewList.add(zeroView);
        mViewList.add(inflater.inflate(R.layout.view_welcome_fir, null));
        mViewList.add(inflater.inflate(R.layout.view_welcome_sec, null));
        View view = inflater.inflate(R.layout.view_welcome_thi, null);
        bt_welcome = (TextView) view.findViewById(R.id.bt_welcome);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        bt_welcome.setOnClickListener(this);
        mViewList.add(view);
        vp_welcome.setOffscreenPageLimit(PAGE_SIZE);
        vp_welcome.setAdapter(new MyViewPagerAdapter());
    }

    @Override
    protected void onDestroy() {
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
        super.onDestroy();
    }

    private void getAccessToken(final boolean isFinishAble) {
        AccountUtil.setIsNeedAccess(true);
        new AccessToken(this).getAccessToken(new BaseNetCallBack<ResAccessTokenBean>() {
            @Override
            public void onSuccess(ResAccessTokenBean paramT) {
                UserUtil.setUserInfo(WelcomeActivity.this, paramT);
                if (isFinishAble) {
                    gotoMain();
                }
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
                if (isFinishAble) {
                    gotoMain();
                }
            }

        });
    }

    private void gotoMain() {
        endTime = System.currentTimeMillis();
        if (endTime - startTime > 2000) {
            gotoActivity(WelcomeActivity.this, MainActivity.class, null);
            WelcomeActivity.this.finish();
        } else {
            new TimeCount(2000 - endTime + startTime, 2000 - endTime + startTime) {
                @Override
                public void onFinish() {
                    gotoActivity(WelcomeActivity.this, MainActivity.class, null);
                    WelcomeActivity.this.finish();
                }
            }.start();
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void findViews() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        fl_welcome = (FrameLayout) findViewById(R.id.fl_welcome);
        vp_welcome = (ViewPager) findViewById(R.id.vp_welcome);
        iv_zero = (ImageView) findViewById(R.id.iv_zero);
        iv_first = (ImageView) findViewById(R.id.iv_first);
        iv_second = (ImageView) findViewById(R.id.iv_second);
        iv_third = (ImageView) findViewById(R.id.iv_third);
    }

    @Override
    protected void setListensers() {
        vp_welcome.addOnPageChangeListener(this);
        vp_welcome.setOnTouchListener(new View.OnTouchListener() {
            float x1 = 0;
            float x2 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if (vp_welcome.getCurrentItem() == PAGE_SIZE - 1 && x2 - x1 < 0) {
                            stopTimerTask();
                            bt_welcome.performClick();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        stopTimerTask();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFirst", true);
        gotoActivity(mContext, MainActivity.class, bundle);
        UserUtil.setIsOldUser(this, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        iv_zero.setImageResource(R.mipmap.ic_circle);
        iv_first.setImageResource(R.mipmap.ic_circle);
        iv_second.setImageResource(R.mipmap.ic_circle);
        iv_third.setImageResource(R.mipmap.ic_circle);
        switch (position) {
            case 0:
                stopTimerTask();
                iv_zero.setImageResource(R.mipmap.ic_circle_ckeck);
                break;
            case 1:
                stopTimerTask();
                iv_first.setImageResource(R.mipmap.ic_circle_ckeck);
                break;
            case 2:
                stopTimerTask();
                iv_second.setImageResource(R.mipmap.ic_circle_ckeck);
                break;
            case 3:
                startTimerTask();
                iv_third.setImageResource(R.mipmap.ic_circle_ckeck);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onUpdateCancel() {
        checkToken();
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position), 0);
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

    private void stopTimerTask() {
        mHandler.removeCallbacks(mTimerTask);
        if (mTimeCount != null) {
            mTimeCount.finishTimeCount();
        }
    }

    private void startTimerTask() {
        stopTimerTask();
        mHandler.postDelayed(mTimerTask, 6000);
        mTimeCount = new TimeCount(tv_time, 6000, 1000, "0");
        mTimeCount.start();
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

}

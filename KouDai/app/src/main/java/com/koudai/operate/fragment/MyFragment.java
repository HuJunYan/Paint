package com.koudai.operate.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.activity.LoginActivity;
import com.koudai.operate.activity.RealizeMeActivity;
import com.koudai.operate.activity.RechargeActivity;
import com.koudai.operate.activity.RechargeHistoryActivity;
import com.koudai.operate.activity.RegisterActivity;
import com.koudai.operate.activity.SettingActivity;
import com.koudai.operate.activity.TicketListActivity;
import com.koudai.operate.activity.TradeDetailsActivity;
import com.koudai.operate.activity.WebViewActivity;
import com.koudai.operate.activity.WithDrawalActivity;
import com.koudai.operate.base.BaseFragment;
import com.koudai.operate.constant.Globparams;
import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.FormatUtil;
import com.koudai.operate.utils.UserUtil;
import com.koudai.operate.view.TitleBar;
import com.tencent.connect.auth.QQAuth;
import com.tencent.open.wpa.WPA;

/**
 * Created by Administrator on 2016/8/3.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, TitleBar.TitleBarListener {
    private View rl_login;
    private View rl_no_login;
    private View rl_trace_detail;
    private View rl_ticket;
    private View rl_fund_record;
    private View rl_help;
    private View rl_about;
    private View rl_realize_me;
    private TextView tv_nickname;
    private TextView tv_mobile;
    private TextView tv_amount;
    private TextView tv_ticket;
    private Button btn_login;
    private Button btn_regist;
    private TitleBar tb_title;
    private boolean isVisible = false;
    private final int REQ_SETTING = 20;
    private final int REQ_Ticekt = 21;

    private MyReceiver myReceiver;

    @Override
    protected void initVariable() {
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Globparams.REFRESH_ACCOUNT_SUCCESS_ACTION);
        mContext.registerReceiver(myReceiver, filter);
        AccountUtil.setIsMyFragmentRefresh(true);
    }

    /**
     * 三个fragment切换触发
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            refreshView();
            if (UserUtil.getIsLogin(mContext) && !AccountUtil.isGetAccountInfo()) {
                mContext.sendBroadcast(new Intent(Globparams.NEED_REFRESH_ACCOUNT_ACTION));
            }
        }
    }

    /**
     * 进入其他Activity返回后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView() {
        if (UserUtil.getIsEnable(mContext) && !btn_login.isEnabled()) {
            btn_login.setEnabled(true);
            btn_regist.setEnabled(true);
        }
        if (UserUtil.getIsLogin(mContext)) {
            btn_login.setText("提现");
            btn_regist.setText("充值");
            rl_login.setVisibility(View.VISIBLE);
            rl_no_login.setVisibility(View.GONE);
            if (AccountUtil.isGetAccountInfo()) {
                setAccountInfo();
            }
        } else {
            onLogoutState();
        }
    }

    private void onLogoutState() {
        tv_amount.setText("-.--");
        tv_ticket.setText("--张");
        tv_mobile.setText("手机:");
        tv_nickname.setText("******");
        btn_login.setText("登录");
        btn_regist.setText("注册");
        rl_login.setVisibility(View.GONE);
        rl_no_login.setVisibility(View.VISIBLE);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_me;
    }

    @Override
    protected void findViews(View rootView) {
        btn_login = (Button) rootView.findViewById(R.id.btn_login);
        btn_regist = (Button) rootView.findViewById(R.id.btn_regist);
        rl_trace_detail = rootView.findViewById(R.id.rl_trace_detail);
        rl_login = rootView.findViewById(R.id.rl_login);
        rl_no_login = rootView.findViewById(R.id.rl_no_login);
        rl_ticket = rootView.findViewById(R.id.rl_ticket);
        rl_fund_record = rootView.findViewById(R.id.rl_fund_record);
        rl_about = rootView.findViewById(R.id.rl_about);
        rl_realize_me = rootView.findViewById(R.id.rl_realize_me);
        rl_help = rootView.findViewById(R.id.rl_help);
        tv_nickname = (TextView) rootView.findViewById(R.id.tv_nickname);
        tv_mobile = (TextView) rootView.findViewById(R.id.tv_mobile);
        tv_amount = (TextView) rootView.findViewById(R.id.tv_amount);
        tv_ticket = (TextView) rootView.findViewById(R.id.tv_ticket);
        tb_title = (TitleBar) rootView.findViewById(R.id.tb_title);
    }

    @Override
    protected void setListensers() {
        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        rl_trace_detail.setOnClickListener(this);
        rl_login.setOnClickListener(this);
        rl_ticket.setOnClickListener(this);
        rl_fund_record.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_realize_me.setOnClickListener(this);
        tb_title.setTieleBarListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) {
            mContext.unregisterReceiver(myReceiver);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                gotoTargetForResult(2, UserUtil.getIsLogin(mContext) ? WithDrawalActivity.class : LoginActivity.class);
                break;
            case R.id.rl_login:
                gotoTargetForResult(REQ_SETTING, SettingActivity.class);
                break;
            case R.id.btn_regist:
                gotoTargetForResult(3, UserUtil.getIsLogin(mContext) ? RechargeActivity.class : RegisterActivity.class);
                break;
            case R.id.rl_trace_detail:
                gotoActivity(mContext, TradeDetailsActivity.class, null);
                break;
            case R.id.rl_ticket:
                startActivityForResult(new Intent(mContext, TicketListActivity.class), REQ_Ticekt);
                break;
            case R.id.rl_fund_record:
                gotoActivity(mContext, RechargeHistoryActivity.class, null);
                break;
            case R.id.rl_about:
                loadUrl(NetConstantValue.getAboutUrl(), "关于我们");
                break;
            case R.id.rl_help:
                loadUrl(NetConstantValue.getFAQUrl(), "帮助信息");
                break;
            case R.id.rl_realize_me:
                gotoActivity(mContext, RealizeMeActivity.class, null);
                break;
        }
    }

    private void loadUrl(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url + "&time=" + FormatUtil.getDataFromTimestamp(System.currentTimeMillis()));
        bundle.putString("title", title);
        gotoActivity(mContext, WebViewActivity.class, bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case REQ_SETTING:
                    tv_nickname.setText(UserUtil.getNickName(mContext));
                    break;
                case REQ_Ticekt:
                    tv_ticket.setText(UserUtil.getTotalTicket(mContext) + "张");
                    break;
            }
        }
    }

    private void gotoTargetForResult(int reqCode, Class className) {
        Intent intent = new Intent(mContext, className);
        startActivityForResult(intent, reqCode);
    }


    private void setAccountInfo() {
        tv_nickname.setText(UserUtil.getNickName(mContext));
        tv_mobile.setText("手机:" + UserUtil.getMoblie(mContext));
        tv_amount.setText(UserUtil.getAvailableBalance(mContext));
        tv_ticket.setText(UserUtil.getTotalTicket(mContext) + "张");
    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {
        QQAuth mqqAuth = QQAuth.createInstance("1105699558", mContext); // 10000000为你申请的APP_ID,mContext是上下文
        WPA mWPA = new WPA(getActivity(), mqqAuth.getQQToken());
        String ESQ = "3439666984";
        mWPA.startWPAConversation(getActivity(), ESQ, "");
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Globparams.REFRESH_ACCOUNT_SUCCESS_ACTION:
                    setAccountInfo();
                    break;
            }
        }
    }
}

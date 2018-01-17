package com.koudai.operate.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.constant.Globparams;
import com.koudai.operate.model.LatestProPriceBean;
import com.koudai.operate.model.OrderInfoBean;
import com.koudai.operate.model.ProGroupBean;
import com.koudai.operate.model.ProInfoItemBean;
import com.koudai.operate.model.ResCreateProBean;
import com.koudai.operate.net.api.TradeAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.ArithUtil;
import com.koudai.operate.utils.UserUtil;
import com.koudai.operate.view.CustomGuideView;
import com.koudai.operate.view.GuideView;
import com.koudai.operate.view.NumberChooseView;
import com.koudai.operate.view.TitleBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BuyActivity extends BaseActivity implements NumberChooseView.ValueChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TitleBar tb_title;
    private TextView tv_available_amount;
    private TextView tv_now_price;
    private TextView tv_service_amount;
    private TextView tv_order_price;
    private TextView tv_recharge;
    private TextView tv_result_limit;
    private TextView tv_result_large;
    private TextView tv_max_value;
    private View ll_ticket;

    private NumberChooseView ncv_amount;
    private NumberChooseView ncv_stop_down;
    private NumberChooseView ncv_stop_up;

    private RadioButton rb_left;
    private RadioButton rb_right;
    private RadioButton rb_1_3;
    private RadioButton rb_1_2;
    private RadioButton rb_all;
    private CheckBox cb_ticket;
    private Button btn_submit;
    private RadioGroup rg_number;


    private int mTradeType;
    private int mCurrentIndex;
    private ProGroupBean mGroup;
    private List<ProInfoItemBean> mList;
    private ProInfoItemBean mCurrentPro;
    private MyReceiver myReceiver;
    private int mAvaliableMaxValue;
    private boolean isAutoChange;
    private int statusColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        statusColor = R.color.buy_red;
        Bundle bundle = getIntent().getExtras();
        mTradeType = bundle.getInt("type", 1);
        if (mTradeType == 2) {
            statusColor = R.color.buy_green;
        }
        super.onCreate(savedInstanceState);
        isShowing = bundle.getBoolean("showGuide");
        if (isShowing) {
            showGuideView();
        }
        mGroup = (ProGroupBean) bundle.getSerializable("pro");
        mList = mGroup.getGoods_list();
        if (mTradeType == 1) {
            tb_title.setTitle("买 涨");
            tb_title.setBackgroundResource(R.color.buy_red);
            rb_left.setBackgroundResource(R.drawable.radio_red_selector);
            rb_right.setBackgroundResource(R.drawable.radio_red_selector);
            rb_1_3.setBackgroundResource(R.drawable.radio_empty_red_selector);
            rb_1_2.setBackgroundResource(R.drawable.radio_empty_red_selector);
            rb_all.setBackgroundResource(R.drawable.radio_empty_red_selector);
            btn_submit.setBackgroundResource(R.drawable.btn_red_rect_selector);
        } else if (mTradeType == 2) {
            tb_title.setTitle("买 跌");
            tb_title.setBackgroundResource(R.color.buy_green);
            rb_left.setBackgroundResource(R.drawable.radio_green_selector);
            rb_right.setBackgroundResource(R.drawable.radio_green_selector);
            rb_1_3.setBackgroundResource(R.drawable.radio_empty_green_selector);
            rb_1_2.setBackgroundResource(R.drawable.radio_empty_green_selector);
            rb_all.setBackgroundResource(R.drawable.radio_empty_green_selector);
            btn_submit.setBackgroundResource(R.drawable.btn_green_rect_selector);
        }
        tv_result_limit.setText("波动一个点盈利" + mGroup.getGoods_list().get(0).getK_amount() + "元");
        tv_result_large.setText("波动一个点盈利" + mGroup.getGoods_list().get(1).getK_amount() + "元");
        double unitPrice = mList.get(1).getUnit_price() + mList.get(1).getTrade_free();
        if (Double.parseDouble(UserUtil.getAvailableBalance(this)) >= unitPrice) {
            rb_right.setChecked(true);
            refreshData(1);
        } else {
            rb_left.setChecked(true);
            refreshData(0);
        }
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter(Globparams.TCP_PRICE_CHANGE_ACTION);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected int getStatusBarColor() {
        return statusColor;
    }

    private void refreshData(int currentIndex) {
        mCurrentIndex = currentIndex;
        mCurrentPro = mList.get(currentIndex);
        ncv_amount.setMaxValue(mCurrentPro.getMax_buy());
        tv_available_amount.setText(UserUtil.getAvailableBalance(this));
        tv_now_price.setText(mGroup.getLatest_price() + "");
        rb_left.setText(mList.get(0).getPro_name() + mList.get(0).getRemark() + "/" + (int) mList.get(0).getUnit_price() + "元");
        rb_right.setText(mList.get(1).getPro_name() + mList.get(1).getRemark() + "/" + (int) mList.get(1).getUnit_price() + "元");
        double unit = mCurrentPro.getUnit_price() + mCurrentPro.getTrade_free();
        mAvaliableMaxValue = (int) (Double.parseDouble(UserUtil.getAvailableBalance(this)) / unit);
        if (mAvaliableMaxValue > mCurrentPro.getMax_buy()) {
            mAvaliableMaxValue = mCurrentPro.getMax_buy();
        }
        tv_max_value.setText(mAvaliableMaxValue + "");
        isAutoChange = true;
        onValueChange(ncv_amount.getValue());
        if (rb_1_3.isChecked()) {
            rb_1_3.performClick();
        } else if (rb_1_2.isChecked()) {
            rb_1_2.performClick();
        } else if (rb_all.isChecked()) {
            rb_all.performClick();
        }
        if (currentIndex == 0) {
            cb_ticket.setText("使用10元代金券");
        } else {
            cb_ticket.setText("使用200元代金券");
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_buy;
    }

    @Override
    protected void findViews() {
        tb_title = (TitleBar) findViewById(R.id.tb_title);
        tv_available_amount = (TextView) findViewById(R.id.tv_available_amount);
        tv_now_price = (TextView) findViewById(R.id.tv_now_price);
        tv_order_price = (TextView) findViewById(R.id.tv_order_price);
        tv_service_amount = (TextView) findViewById(R.id.tv_service_amount);
        tv_recharge = (TextView) findViewById(R.id.tv_recharge);
        tv_result_limit = (TextView) findViewById(R.id.tv_result_limit);
        tv_result_large = (TextView) findViewById(R.id.tv_result_large);
        rb_left = (RadioButton) findViewById(R.id.rb_left);
        rb_right = (RadioButton) findViewById(R.id.rb_right);
        rb_1_3 = (RadioButton) findViewById(R.id.rb_1_3);
        rb_1_2 = (RadioButton) findViewById(R.id.rb_1_2);
        rb_all = (RadioButton) findViewById(R.id.rb_all);
        cb_ticket = (CheckBox) findViewById(R.id.cb_ticket);
        ncv_amount = (NumberChooseView) findViewById(R.id.ncv_amount);
        ncv_stop_down = (NumberChooseView) findViewById(R.id.ncv_stop_down);
        ncv_stop_up = (NumberChooseView) findViewById(R.id.ncv_stop_up);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        rg_number = (RadioGroup) findViewById(R.id.rg_number);
        tv_max_value = (TextView) findViewById(R.id.tv_max_value);
        ll_ticket = findViewById(R.id.ll_ticket);
    }

    @Override
    protected void setListensers() {
        ncv_amount.setOnValueChangeListener(this);
        btn_submit.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
        rb_left.setOnClickListener(this);
        rb_right.setOnClickListener(this);
        rb_1_2.setOnClickListener(this);
        rb_1_3.setOnClickListener(this);
        rb_all.setOnClickListener(this);
        cb_ticket.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    @Override
    public void onValueChange(int value) {
        if (mCurrentIndex == 0) {
            cb_ticket.setEnabled((UserUtil.getTicket10(this) > 0));
        } else {
            cb_ticket.setEnabled(UserUtil.getTicket200(this) > 0);
        }
        if (value != 1 || !cb_ticket.isEnabled()) {
            cb_ticket.setChecked(false);
        }
        if (cb_ticket.isChecked() && cb_ticket.isEnabled()) {
            tv_service_amount.setText("0");
        } else {
            tv_service_amount.setText(value * mCurrentPro.getTrade_free() + "");
        }
        tv_order_price.setText(mCurrentPro.getUnit_price() * value + "");
        if (!isAutoChange) {
            rg_number.clearCheck();
        }
        isAutoChange = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                gotoActivity(this, RechargeActivity.class, null);
                break;
            case R.id.btn_submit:
                createOrder();
                break;
            case R.id.rb_left:
                refreshData(0);
                break;
            case R.id.rb_right:
                refreshData(1);
                break;
            case R.id.rb_1_3:
                int value = mAvaliableMaxValue % 3 != 0 ? mAvaliableMaxValue / 3 + 1 : mAvaliableMaxValue / 3;
                setNumber(value);
                break;
            case R.id.rb_1_2:
                int value1 = mAvaliableMaxValue % 2 != 0 ? mAvaliableMaxValue / 2 + 1 : mAvaliableMaxValue / 2;
                setNumber(value1);
                break;
            case R.id.rb_all:
                int value2 = mAvaliableMaxValue;
                setNumber(value2);
                break;
        }
    }

    private void createOrder() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("goods_id", mCurrentPro.getGoods_id());
            jsonObject.put("trade_type", mTradeType);
            jsonObject.put("amount", ncv_amount.getValue());
            jsonObject.put("use_ticket", cb_ticket.isChecked() && cb_ticket.isEnabled() ? 1 : 0);
            jsonObject.put("target_profit", ncv_stop_up.getValue());
            jsonObject.put("stop_loss", ncv_stop_down.getValue());
            new TradeAction(mContext).createPro(jsonObject, new BaseNetCallBack<ResCreateProBean>() {
                @Override
                public void onSuccess(ResCreateProBean paramT) {
                    OrderInfoBean order = new OrderInfoBean();
                    try {
                        order = paramT.getResponse().getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setResult(RESULT_OK);
                    if (order.getUse_ticket() == 1) {
                        if (mCurrentIndex == 0) {
                            UserUtil.setTicekt10(mContext, UserUtil.getTicket10(mContext) - 1);
                        } else {
                            UserUtil.setTicekt200(mContext, UserUtil.getTicket200(mContext) - 1);
                        }
                    }
                    double tradeDeposit = order.getUse_ticket() == 1 ? 0 : order.getTrade_deposit();
                    double price = ArithUtil.add(tradeDeposit, order.getTrade_fee());
                    double balance = ArithUtil.sub(Double.parseDouble(UserUtil.getAvailableBalance(BuyActivity.this)), price);
                    UserUtil.setAvailableBalance(BuyActivity.this, balance + "");
                    Intent intent = new Intent(Globparams.CREATE_ORDER_ACTION);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", order);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                    backActivity();
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            tv_service_amount.setText("0");
            rg_number.clearCheck();
            setNumber(0);
        } else {
            tv_service_amount.setText(ncv_amount.getValue() * mCurrentPro.getTrade_free() + "");
        }
    }

    private void setNumber(int value) {
        isAutoChange = true;
        if (value == 0) {
            value = 1;
        }
        ncv_amount.setValue(value);
        onValueChange(value);
    }

    class MyReceiver extends BroadcastReceiver {
        private String json;

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String data = intent.getStringExtra("data");
                if (!data.equals(json)) {
                    json = data;
                } else {
                    return;
                }
                JSONArray array = new JSONArray(intent.getStringExtra("data"));
                for (int i = 0; i < array.length(); i++) {
                    LatestProPriceBean bean = GsonUtil.json2bean(array.getString(i), LatestProPriceBean.class);
                    if (bean.getPro_code().equals(mGroup.getPro_code())) {
                        mGroup.setLatest_price(bean.getLatest_price());
                        tv_now_price.setText(bean.getLatest_price() + "");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    GuideView guideView;

    boolean isShowing = true;

    private void showGuideView() {
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.icon_guide_use_ticket);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params);

        final ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.mipmap.icon_guide_exit);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast(new Intent("closeGuide"));
                backActivity();
            }
        });

        CustomGuideView view = new CustomGuideView(iv, GuideView.Direction.TOP);
        CustomGuideView view1 = new CustomGuideView(iv1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        guideView = GuideView.Builder
                .newInstance(this)
                .setTargetView(ll_ticket)//设置目标
                .addCustomGuideView(view)
                .addCustomGuideView(view1)
                .setShape(GuideView.MyShape.RECT)   // 设置圆形显示区域，
                .build();
        guideView.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isShowing) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean isExtendsBaseOnKeyDown() {
        return false;
    }
}

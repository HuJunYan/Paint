package com.koudai.operate.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.constant.Globparams;
import com.koudai.operate.model.LatestProPriceBean;
import com.koudai.operate.model.OrderInfoBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.api.TradeAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.ArithUtil;
import com.koudai.operate.utils.FormatUtil;
import com.koudai.operate.view.NumberChooseView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_create_time;
    private TextView tv_pro_name;
    private TextView tv_order_type;
    private TextView tv_build_price;
    private TextView tv_amount;
    private TextView tv_pay_type;
    private TextView tv_buy_charge;
    private TextView tv_poundage;
    private TextView tv_profit;

    private NumberChooseView ncv_stop_down;
    private NumberChooseView ncv_stop_up;

    private Button bt_cancel;
    private Button bt_confirm;
    private int mDefaultUpValue;
    private int mDefaultDownValue;

    private OrderInfoBean mOrder;
    private String mProcode;
    private double mLatestPrice;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mOrder = (OrderInfoBean) bundle.getSerializable("order");
        mProcode = bundle.getString("code");
        mLatestPrice = bundle.getDouble("price");
        setProfit();
        mOrder = (OrderInfoBean) bundle.getSerializable("order");
        tv_create_time.setText(FormatUtil.getDataFromTimestamp(mOrder.getBuild_time()));
        tv_pro_name.setText(mOrder.getPro_name() + mOrder.getPro_amount() + mOrder.getPro_unit());
        tv_order_type.setText(mOrder.getTrade_type() == 1 ? "买涨" : "买跌");
        tv_build_price.setText(mOrder.getBuild_price() + "");
        tv_amount.setText(mOrder.getAmount() + "");
        tv_pay_type.setText(mOrder.getUse_ticket() == 1 ? "代金券" : "现金");
        tv_buy_charge.setText(mOrder.getTrade_deposit() + "");
        tv_poundage.setText(mOrder.getTrade_fee() + "");
        mDefaultDownValue = Integer.parseInt(mOrder.getStop_loss());
        ncv_stop_down.setValue(mDefaultDownValue);
        mDefaultUpValue = Integer.parseInt(mOrder.getTarget_profit());
        ncv_stop_up.setValue(mDefaultUpValue);
        IntentFilter intentFilter = new IntentFilter(Globparams.TCP_PRICE_CHANGE_ACTION);
        myReceiver = new MyReceiver();
        mContext.registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void findViews() {
        tv_create_time = (TextView) findViewById(R.id.tv_create_time);
        tv_pro_name = (TextView) findViewById(R.id.tv_pro_name);
        tv_order_type = (TextView) findViewById(R.id.tv_order_type);
        tv_build_price = (TextView) findViewById(R.id.tv_build_price);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_pay_type = (TextView) findViewById(R.id.tv_pay_type);
        tv_buy_charge = (TextView) findViewById(R.id.tv_buy_charge);
        tv_poundage = (TextView) findViewById(R.id.tv_poundage);
        tv_profit = (TextView) findViewById(R.id.tv_profit);
        ncv_stop_down = (NumberChooseView) findViewById(R.id.ncv_stop_down);
        ncv_stop_up = (NumberChooseView) findViewById(R.id.ncv_stop_up);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
    }

    @Override
    protected void setListensers() {
        bt_cancel.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                backActivity();
                break;
            case R.id.bt_confirm:
                if (ncv_stop_down.getValue() != mDefaultDownValue || ncv_stop_up.getValue() != mDefaultUpValue) {
                    setOrderPrice();
                } else {
                    backActivity();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    private void setOrderPrice() {
        try {
            JSONObject json = new JSONObject();
            json.put("order_id", mOrder.getOrder_id());
            json.put("target_profit", ncv_stop_up.getValue());
            json.put("stop_loss", ncv_stop_down.getValue());
            new TradeAction(this).setOrderPrice(json, new BaseNetCallBack<ResponseBean>() {
                @Override
                public void onSuccess(ResponseBean paramT) {
                    mOrder.setTarget_profit(ncv_stop_up.getValue() + "");
                    mOrder.setStop_loss(ncv_stop_down.getValue() + "");
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", mOrder);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
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
                    if (bean.getPro_code().equals(mProcode)) {
                        mLatestPrice = bean.getLatest_price();
                        setProfit();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setProfit() {
        int flag = mOrder.getTrade_type() == 1 ? 1 : -1;
        double result1 = ArithUtil.sub(mLatestPrice, mOrder.getBuild_price());
        double result2 = ArithUtil.mul(result1, mOrder.getAmount());
        double result3 = ArithUtil.mul(result2, mOrder.getK_amount());
        double result = ArithUtil.mul(result3, flag);
        double stop = Integer.parseInt(mOrder.getStop_loss()) == 0 ? 1 : ArithUtil.div(Double.parseDouble(mOrder.getStop_loss()), 100);
        double target = ArithUtil.div(Double.parseDouble(mOrder.getTarget_profit()), 100);
        if (mOrder.getUse_ticket() == 1) {
            if (result < 0) {
                result = 0;
            } else if (target != 0 && result >= ArithUtil.mul(mOrder.getTrade_deposit(), target)) {
                result = ArithUtil.mul(mOrder.getTrade_deposit(), target);
            }
        } else {
            if (result < 0) {
                if (result <= ArithUtil.mul(mOrder.getTrade_deposit() * -1, stop)) {
                    result = ArithUtil.mul(mOrder.getTrade_deposit() * -1, stop);
                }
            } else if (target != 0 && result >= ArithUtil.mul(mOrder.getTrade_deposit(), target)) {
                result = ArithUtil.mul(mOrder.getTrade_deposit(), target);
            }
        }
        if (result >= 0) {
            tv_profit.setTextColor(Color.parseColor("#FFF54337"));
        } else {
            tv_profit.setTextColor(Color.parseColor("#FF12bc65"));
        }
        tv_profit.setText(result + "");
    }
}

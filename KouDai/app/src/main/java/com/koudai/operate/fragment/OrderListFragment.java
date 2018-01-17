package com.koudai.operate.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.activity.LoginActivity;
import com.koudai.operate.activity.MainActivity;
import com.koudai.operate.activity.OrderDetailsActivity;
import com.koudai.operate.activity.RechargeActivity;
import com.koudai.operate.adapter.OrderListAdapter;
import com.koudai.operate.base.BaseFragment;
import com.koudai.operate.constant.Globparams;
import com.koudai.operate.model.LatestProPriceBean;
import com.koudai.operate.model.OrderInfoBean;
import com.koudai.operate.model.ProGroupBean;
import com.koudai.operate.model.ResAccountBean;
import com.koudai.operate.model.ResNoManualOrderBean;
import com.koudai.operate.model.ResProGroupListBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.api.TradeAction;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.ArithUtil;
import com.koudai.operate.utils.LogUtil;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/20.
 */
public class OrderListFragment extends BaseFragment implements OrderListAdapter.OrderListener, View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView tv_total_balance;
    private TextView tv_profit_loss;
    private TextView tv_avaliable_balance;
    private TextView tv_total_cost;
    private TextView tv_recharge;
    private TextView tv_to_trade;
    private TextView tv_to_login;
    private TextView tv_total;
    private View rl_no_order;
    private View rl_no_login;
    private ListView lv_list;
    private Dialog mDialog;
    private Dialog mPrompDialog;

    private List<OrderInfoBean> mOrderList = new ArrayList<>();
    private List<OrderInfoBean> mOrderListInBackground = new ArrayList<>();
    private Map<String, OrderPrice> mPriceMap = new HashMap<>();
    private OrderListAdapter mAdapter;
    private ResNoManualOrderBean noManualOrderBean;
    private String mPriceJson = "";
    private boolean isOrderChange = true;
    private boolean isInitInfo = false;
    private boolean isGetNoManualOrder = false;//检测未平仓单子
    private boolean isGetAccountInfo = false;//获取最新可用资产
    private boolean isVisible = false;
    private boolean isResume = false;
    private boolean isDetailClickAble = true;
    private boolean isSellClickAble = true;
    private String mCurrentOrderDetailId = "";
    private String mCurrentOrderSellId = "";
    private String mAvaliableBalance = "";
    private MyReceiver myReceiver;

    @Override
    protected void initVariable() {
        IntentFilter intentFilter = new IntentFilter(Globparams.TCP_PRICE_CHANGE_ACTION);
        intentFilter.addAction(Globparams.CREATE_ORDER_ACTION);
        intentFilter.addAction(Globparams.GET_GOODS_LIST_ACTION);
        intentFilter.addAction(Globparams.NEED_REFRESH_ACCOUNT_ACTION);
        myReceiver = new MyReceiver();
        mContext.registerReceiver(myReceiver, intentFilter);
        if (UserUtil.getIsLogin(mContext)) {
            checkOrderAndAccount();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            if (!UserUtil.getIsLogin(mContext)) {
                gotoActivity(mContext, LoginActivity.class, null);
            }
            refreshData();
        } else {
            isVisible = false;
        }
    }

    private void refreshData() {
        if (UserUtil.getIsLogin(mContext) && AccountUtil.isOrderFragmentReLoad()) {
            onLogoutStatus();
            rl_no_login.setVisibility(View.GONE);
            checkOrderAndAccount();
            return;
        }
        if (isVisible && isResume) {
            if (UserUtil.getIsLogin(mContext)) {
                setMapByJson();
                if (mOrderList.size() != mOrderListInBackground.size()) {
                    LogUtil.d("ret", "OrderFragmentrefreshData");
                    mOrderList.clear();
                    mOrderList.addAll(mOrderListInBackground);
                }
                setTotalBalance("refreshData");
                checkOrderAndAccount();
            } else {
                onLogoutStatus();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        refreshData();
    }

    @Override
    public void onPause() {
        super.onPause();
        isResume = false;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void findViews(View rootView) {
        tv_total_balance = (TextView) rootView.findViewById(R.id.tv_total_balance);
        tv_profit_loss = (TextView) rootView.findViewById(R.id.tv_profit_loss);
        tv_avaliable_balance = (TextView) rootView.findViewById(R.id.tv_avaliable_balance);
        tv_total_cost = (TextView) rootView.findViewById(R.id.tv_total_cost);
        tv_recharge = (TextView) rootView.findViewById(R.id.tv_recharge);
        tv_to_trade = (TextView) rootView.findViewById(R.id.tv_to_trade);
        tv_to_login = (TextView) rootView.findViewById(R.id.tv_to_login);
        tv_total = (TextView) rootView.findViewById(R.id.tv_total);
        rl_no_order = rootView.findViewById(R.id.rl_no_order);
        rl_no_login = rootView.findViewById(R.id.rl_no_login);
        lv_list = (ListView) rootView.findViewById(R.id.lv_list);
    }

    @Override
    protected void setListensers() {
        tv_recharge.setOnClickListener(this);
        tv_to_login.setOnClickListener(this);
        tv_to_trade.setOnClickListener(this);
        tv_total.setOnClickListener(this);
        tv_total_balance.setOnClickListener(this);
        lv_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                gotoActivity(mContext, RechargeActivity.class, null);
                break;
            case R.id.tv_to_login:
                gotoActivity(mContext, LoginActivity.class, null);
                break;
            case R.id.tv_to_trade:
                ((MainActivity) getActivity()).turnToPage(0, true);
                break;
            case R.id.bt_dialog_confirm:
                sell();
                mDialog.cancel();
                break;
            case R.id.bt_dialog_cancel:
                mDialog.cancel();
                break;
            case R.id.bt_dialog_know:
                mPrompDialog.cancel();
                break;
            case R.id.tv_total:
            case R.id.tv_total_balance:
                initPrompDialog();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) {
            mContext.unregisterReceiver(myReceiver);
        }
    }

    private void setMapByJson() {
        try {
            if (!mPriceJson.equals("")) {
                JSONArray array = new JSONArray(mPriceJson);
                for (int i = 0, len = array.length(); i < len; i++) {
                    LatestProPriceBean bean = GsonUtil.json2bean(array.getString(i), LatestProPriceBean.class);
                    mPriceMap.put(bean.getPro_code(), new OrderPrice(bean.getLatest_price(), bean.getRise()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNoManualOrderList() {
        new TradeAction(mContext).getNoManualOrderList(new JSONObject(), new BaseNetCallBack<ResNoManualOrderBean>() {
            @Override
            public void onSuccess(ResNoManualOrderBean paramT) {
                noManualOrderBean = paramT;
                isGetNoManualOrder = true;
                refreshOrderList();
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
            }

            @Override
            public void onLogout() {
                onLogoutStatus();
            }
        });
    }

    private void getAccountInfo() {
        new UserAction(mContext).getAccountInfo(new JSONObject(), new BaseNetCallBack<ResAccountBean>() {
            @Override
            public void onSuccess(ResAccountBean paramT) {
                isGetAccountInfo = true;
                mAvaliableBalance = paramT.getResponse().getData().getAvailable_balance();
                UserUtil.setUserInfo(mContext, paramT);
                if (paramT.getResponse().getData().getUse_ticket_count() <= 0) {
                    AccountUtil.setIsNeedGuideTicket(true);
                }
                refreshOrderList();
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
            }

            @Override
            public void onLogout() {
                onLogoutStatus();
            }
        });
    }

    private void refreshOrderList() {
        if (isGetAccountInfo && isGetNoManualOrder) {
            AccountUtil.setIsOrderFragmentReLoad(false);
            AccountUtil.setIsGetAccountInfo(true);
            isInitInfo = true;
            mOrderListInBackground.clear();
            mOrderListInBackground.addAll(noManualOrderBean.getResponse().getData().getList());
            UserUtil.setAvailableBalance(mContext, mAvaliableBalance);
            setTotalBalance("getAccountInfo");
            if (mOrderListInBackground.size() > 0) {
                lv_list.setVisibility(View.VISIBLE);
                rl_no_order.setVisibility(View.GONE);
            } else {
                lv_list.setVisibility(View.GONE);
                rl_no_order.setVisibility(View.VISIBLE);
            }
            if (isVisible && isResume) {
                mOrderList.clear();
                mOrderList.addAll(mOrderListInBackground);
                setMapByJson();
                setTotalBalance("");
            }
            mContext.sendBroadcast(new Intent(Globparams.REFRESH_ACCOUNT_SUCCESS_ACTION));
        }
    }

    private void onLogoutStatus() {
        isInitInfo = false;
        isGetNoManualOrder = false;
        isGetAccountInfo = false;
        AccountUtil.setIsGetAccountInfo(false);
        rl_no_order.setVisibility(View.GONE);
        rl_no_login.setVisibility(View.VISIBLE);
        lv_list.setVisibility(View.GONE);
        tv_total_balance.setText("_.__");
        tv_avaliable_balance.setText("_.__");
        tv_profit_loss.setText("_.__");
        tv_total_cost.setText("_.__");
        mOrderListInBackground.clear();
        mOrderList.clear();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            mAdapter = null;
        }
    }

    private void setProfitAndLoss(double latestPrice, OrderInfoBean order) {
        int flag = order.getTrade_type() == 1 ? 1 : -1;
        double result1 = ArithUtil.sub(latestPrice, order.getBuild_price());
        double result2 = ArithUtil.mul(result1, order.getAmount());
        double result3 = ArithUtil.mul(result2, order.getK_amount());
        double result = ArithUtil.mul(result3, flag);
        double stop = Integer.parseInt(order.getStop_loss()) == 0 ? 1 : ArithUtil.div(Double.parseDouble(order.getStop_loss()), 100);
        double target = ArithUtil.div(Double.parseDouble(order.getTarget_profit()), 100);
        if (order.getUse_ticket() == 1) {
            if (result < 0) {
                result = 0;
                if (result <= ArithUtil.mul(order.getTrade_deposit() * -1, stop)) {
                    if (isOrderChange) {
                        isOrderChange = false;
                        checkOrderAndAccount();
                    }
                }
            } else if (target != 0 && result >= ArithUtil.mul(order.getTrade_deposit(), target)) {
                result = ArithUtil.mul(order.getTrade_deposit(), target);
                if (isOrderChange) {
                    isOrderChange = false;
                    checkOrderAndAccount();
                }
            }
        } else {
            if (result < 0) {
                if (result <= ArithUtil.mul(order.getTrade_deposit() * -1, stop)) {
                    result = ArithUtil.mul(order.getTrade_deposit() * -1, stop);
                    if (isOrderChange) {
                        isOrderChange = false;
                        checkOrderAndAccount();
                    }
                }
            } else if (target != 0 && result >= ArithUtil.mul(order.getTrade_deposit(), target)) {
                result = ArithUtil.mul(order.getTrade_deposit(), target);
                if (isOrderChange) {
                    isOrderChange = false;
                    checkOrderAndAccount();
                }
            }
        }
        order.setProfitAndLoss(result);
        if (mDialog != null && mDialog.isShowing() && order.getOrder_id().equals(mCurrentOrderSellId)) {
            if (order.getProfitAndLoss() >= 0) {
                tv_dialog_pro_result.setTextColor(Color.parseColor("#FFF54337"));
            } else {
                tv_dialog_pro_result.setTextColor(Color.parseColor("#FF12bc65"));
            }
            tv_dialog_pro_result.setText(order.getProfitAndLoss() + "");
        }
    }


    private void checkOrderAndAccount() {
        isGetAccountInfo = false;
        isGetNoManualOrder = false;
        getAccountInfo();
        getNoManualOrderList();
    }

    private void setTotalBalance(String action) {
        LogUtil.d("ret", "OrderFragment====setTotalBalance======" + action);
        if (mPriceMap.size() > 0 && isInitInfo) {
            double cost = 0;
            double profit = 0;
            for (OrderInfoBean order : mOrderList) {
                if (order != null) {
                    setProfitAndLoss(mPriceMap.get(order.getPro_code()).getNewPrice(), order);
                    order.setNew_price(mPriceMap.get(order.getPro_code()).getNewPrice());
                    order.setRise(mPriceMap.get(order.getPro_code()).getRise());
                    double tradeDeposit = order.getUse_ticket() == 1 ? 0 : order.getTrade_deposit();
                    cost = ArithUtil.add(cost, tradeDeposit);
                    profit = ArithUtil.add(profit, order.getProfitAndLoss());
                }
            }
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter = new OrderListAdapter(mContext, mOrderList, this);
                lv_list.setAdapter(mAdapter);
            }
            double totalBalance = ArithUtil.add(Double.parseDouble(UserUtil.getAvailableBalance(mContext)), ArithUtil.add(cost, profit));
            if (totalBalance < 0) {
                totalBalance = 0;
            }
            tv_total_balance.setText(totalBalance + "");
            tv_avaliable_balance.setText(UserUtil.getAvailableBalance(mContext));
            tv_profit_loss.setText(profit + "");
            tv_total_cost.setText(cost + "");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (isDetailClickAble) {
            isDetailClickAble = false;
            OrderInfoBean order = mOrderList.get(position);
            Intent intent = new Intent(mContext, OrderDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", order);
            bundle.putString("code", order.getPro_code());
            bundle.putDouble("price", order.getNew_price());
            intent.putExtras(bundle);
            startActivityForResult(intent, 10);
            mCurrentOrderDetailId = order.getOrder_id();
        }
    }

    @Override
    public void onClickDetail(int position) {

    }

    @Override
    public void onClickSell(int position) {
        if (isSellClickAble) {
            isSellClickAble = false;
            initDialog();
            OrderInfoBean order = mOrderList.get(position);
            tv_dialog_pro_amount.setText(order.getAmount() + "");
            tv_dialog_pro_name.setText(order.getPro_name() + order.getPro_amount() + order.getPro_unit());
            if (order.getProfitAndLoss() >= 0) {
                tv_dialog_pro_result.setTextColor(Color.parseColor("#FFF54337"));
            } else {
                tv_dialog_pro_result.setTextColor(Color.parseColor("#FF12bc65"));
            }
            tv_dialog_pro_result.setText(order.getProfitAndLoss() + "");
            mCurrentOrderSellId = order.getOrder_id();
            mDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == getActivity().RESULT_OK) {
            OrderInfoBean bean = (OrderInfoBean) data.getExtras().getSerializable("order");
            for (OrderInfoBean order : mOrderList) {
                if (order.getOrder_id().equals(mCurrentOrderDetailId)) {
                    order.setStop_loss(bean.getStop_loss());
                    order.setTarget_profit(bean.getTarget_profit());
                    setTotalBalance("setTarget_profit");
                    break;
                }
            }
        }
        isDetailClickAble = true;
    }

    TextView tv_dialog_pro_name;
    TextView tv_dialog_pro_amount;
    TextView tv_dialog_pro_result;
    Button bt_dialog_cancel;
    Button bt_dialog_confirm;

    private void initDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(mContext, R.style.dialog);
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_confirm_sell, null);
            mDialog.setContentView(view);
            mDialog.setCanceledOnTouchOutside(false);
            Window window = mDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            tv_dialog_pro_name = (TextView) view.findViewById(R.id.tv_dialog_pro_name);
            tv_dialog_pro_amount = (TextView) view.findViewById(R.id.tv_dialog_pro_amount);
            tv_dialog_pro_result = (TextView) view.findViewById(R.id.tv_dialog_pro_result);
            bt_dialog_cancel = (Button) view.findViewById(R.id.bt_dialog_cancel);
            bt_dialog_confirm = (Button) view.findViewById(R.id.bt_dialog_confirm);
            bt_dialog_cancel.setOnClickListener(this);
            bt_dialog_confirm.setOnClickListener(this);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    isSellClickAble = true;
                }
            });
        }
    }

    TextView bt_dialog_know;

    private void initPrompDialog() {
        if (isInitInfo && !tv_total_balance.getText().toString().contains("__")) {
            if (mPrompDialog == null) {
                mPrompDialog = new Dialog(mContext, R.style.dialog);
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_total_balance, null);
                mPrompDialog.setContentView(view);
                mPrompDialog.setCanceledOnTouchOutside(false);
                Window window = mPrompDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                bt_dialog_know = (TextView) view.findViewById(R.id.bt_dialog_know);
                bt_dialog_know.setOnClickListener(this);
            }
            mPrompDialog.show();
        }
    }


    private void sell() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", mCurrentOrderSellId);
            new TradeAction(mContext).manualLiqui(jsonObject, new BaseNetCallBack<ResponseBean>() {
                @Override
                public void onSuccess(ResponseBean paramT) {
                    for (OrderInfoBean order : mOrderList) {
                        if (order.getOrder_id().equals(mCurrentOrderSellId)) {
                            double tradeDeposit = order.getUse_ticket() == 1 ? 0 : order.getTrade_deposit();
                            double balance = ArithUtil.add(Double.parseDouble(UserUtil.getAvailableBalance(mContext)), tradeDeposit);
                            balance = ArithUtil.add(order.getProfitAndLoss(), balance);
                            UserUtil.setAvailableBalance(mContext, balance + "");
                            mOrderList.remove(order);
                            setTotalBalance("sell");
                            break;
                        }
                    }
                    if (mOrderList.size() == 0) {
                        lv_list.setVisibility(View.GONE);
                        rl_no_order.setVisibility(View.VISIBLE);
                    }
                    for (OrderInfoBean order : mOrderListInBackground) {
                        if (order.getOrder_id().equals(mCurrentOrderSellId)) {
                            mOrderListInBackground.remove(order);
                            break;
                        }
                    }
                    checkOrderAndAccount();
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
                    checkOrderAndAccount();
                }

                @Override
                public void onLogout() {
                    onLogoutStatus();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final class OrderPrice {
        private double newPrice;
        private double rise;

        public OrderPrice(double newPrice, double rise) {
            this.newPrice = newPrice;
            this.rise = rise;
        }

        public double getNewPrice() {
            return newPrice;
        }

        public void setNewPrice(double newPrice) {
            this.newPrice = newPrice;
        }

        public double getRise() {
            return rise;
        }

        public void setRise(double rise) {
            this.rise = rise;
        }
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Globparams.TCP_PRICE_CHANGE_ACTION:
                    String data = intent.getStringExtra("data");
                    if (!data.equals(mPriceJson)) {
                        mPriceJson = data;
                        if (isVisible && isResume) {
                            isOrderChange = true;
                            setMapByJson();
                            setTotalBalance("TCP_PRICE_CHANGE_ACTION");
                        }
                    }
                    break;
                case Globparams.CREATE_ORDER_ACTION:
                    LogUtil.d("ret", "onReceiveCREATE_ORDER_ACTION");
                    if (isInitInfo) {
                        OrderInfoBean bean = (OrderInfoBean) intent.getExtras().getSerializable("order");
                        mOrderListInBackground.add(bean);
                        lv_list.setVisibility(View.VISIBLE);
                        rl_no_order.setVisibility(View.GONE);
                    }
                    break;
                case Globparams.NEED_REFRESH_ACCOUNT_ACTION:
                    if (UserUtil.getIsLogin(mContext)) {
                        checkOrderAndAccount();
                    }
                    break;
                case Globparams.GET_GOODS_LIST_ACTION:
                    if (mPriceJson.equals("")) {
                        ResProGroupListBean resProGroupListBean = GsonUtil.json2bean(intent.getStringExtra("json"), ResProGroupListBean.class);
                        List<ProGroupBean> proList = resProGroupListBean.getResponse().getData().getList();
                        for (ProGroupBean pro : proList) {
                            mPriceMap.put(pro.getPro_code(), new OrderPrice(pro.getLatest_price(), pro.getRise()));
                        }
                    }
                    break;
            }
        }
    }
}

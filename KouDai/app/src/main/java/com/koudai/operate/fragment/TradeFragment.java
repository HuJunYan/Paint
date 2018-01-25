package com.koudai.operate.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.koudai.operate.R;
import com.koudai.operate.activity.BuyActivity;
import com.koudai.operate.activity.LoginActivity;
import com.koudai.operate.activity.MainActivity;
import com.koudai.operate.activity.WebViewActivity;
import com.koudai.operate.base.BaseFragment;
import com.koudai.operate.constant.Globparams;
import com.koudai.operate.model.ADInfo;
import com.koudai.operate.model.KLineItemBean;
import com.koudai.operate.model.KLineListBean;
import com.koudai.operate.model.LatestProPriceBean;
import com.koudai.operate.model.ProGroupBean;
import com.koudai.operate.model.ResAccessTokenBean;
import com.koudai.operate.model.ResImageListBean;
import com.koudai.operate.model.ResProGroupListBean;
import com.koudai.operate.model.ResUpAndDownBean;
import com.koudai.operate.model.ResUserMoneyBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.mychart.MyKChart;
import com.koudai.operate.mychart.MyKChartMarkerView;
import com.koudai.operate.mychart.MyLeftMarkerView;
import com.koudai.operate.mychart.MyLineChart;
import com.koudai.operate.mychart.MyXAxis;
import com.koudai.operate.mychart.MyYAxis;
import com.koudai.operate.net.api.AccessToken;
import com.koudai.operate.net.api.GetKLineData;
import com.koudai.operate.net.api.GoodsAction;
import com.koudai.operate.net.api.TradeAction;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.net.base.XUtilsManager;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.KLineDataUtil;
import com.koudai.operate.utils.LogUtil;
import com.koudai.operate.utils.TimeCount;
import com.koudai.operate.utils.UserUtil;
import com.koudai.operate.view.CustomGuideView;
import com.koudai.operate.view.GuideView;
import com.koudai.operate.view.ImageCycleView;
import com.koudai.operate.view.ProGroupView;
import com.koudai.operate.view.RollingUpTextView;
import com.koudai.operate.view.ScaleView;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;


/**
 * Created by Administrator on 2016/8/3.
 */
public class TradeFragment extends BaseFragment implements View.OnClickListener, ProGroupView.OnCheckedChangedListener, RadioGroup.OnCheckedChangeListener {
    private ProGroupView pro_group;
    private ScaleView sv_scale;
    private Button bt_buy_up;
    private Button bt_buy_down;
    private View ll_buy;
    private View fl_k_line;
    private View ll_ticket_top;
    private View rl_trade;
    private TextView tv_high;
    private TextView tv_low;
    private TextView tv_yesterday_end;
    private TextView tv_beginning;
    private TextView tv_balance;
    private TextView tv_ticket;
    private TextView tv_trade_time;
    private ImageView iv_head;
    private ImageView iv_k_line_bg;
    private RollingUpTextView tv_message;
    private ImageCycleView icv_image;
    private BitmapUtils mBitmaputils;

    private final int REQUEST_CREATE_ORDER = 20;
    private double mPrice;
    private boolean isResume = false;
    private boolean isVisivle = false;
    private boolean isGetImage = false;
    private boolean isIndexLogin = false;
    private boolean isStartMessage = false;
    private List<ProGroupBean> mProList = new ArrayList<>();
    private MyReceiver myReceiver;

    private RadioGroup rg_k_line;
    Handler mHandler = new Handler();
    //K-line
    private final int MAX_VISIBLE_RANGE = 100;
    private final int MIN_VISIBLE_RANGE = 15;
    private final float ZOOM_NUM_X = 10f;
    private final float ZOOM_NUM_X_DAY = 2f;
    private final String TAG = "TradeFragment";
    private MyKChart k_chart;
    private MyKChart k_day_chart;
    private CandleData candleData;
    private CombinedData combinedData;
    private ArrayList<String> xVals;
    private int colorHomeBg;
    private int colorLine;
    private int colorText;
    // min-line
//    private MyBarChart barChart;
    private MyLineChart lineChart;
    private Subscription subscriptionMinute;
    private LineDataSet d1,d2;
    MyXAxis xAxisLine;
    //    MyYAxis axisRightLine;
    MyYAxis axisLeftLine;

    SparseArray<String> stringSparseArray;

    private Map<String, KLineListBean> kLineMap = new HashMap<String, KLineListBean>();
    private int mKLineId = 1;
    private int mCurrentKtype = 99;
    private long mKLineTime = 30 * 1000;
    private TimeCount mTimeCount;
    private ProgressBar pb_loading;
    private TextView tv_failed;
    private String mcurrentChose ;



    @Override
    protected void initVariable() {
        onLogoutState();
        if (UserUtil.getIsLogin(mContext)) {
            bt_buy_up.setEnabled(false);
            bt_buy_down.setEnabled(false);
        }
        mBitmaputils = XUtilsManager.getInstance(mContext).getBitmapUtils();
        sv_scale.setUpScale(50);
        IntentFilter intentFilter = new IntentFilter(Globparams.TCP_PRICE_CHANGE_ACTION);
        intentFilter.addAction(Globparams.REFRESH_ACCOUNT_SUCCESS_ACTION);
        intentFilter.addAction("closeGuide");
        myReceiver = new MyReceiver();
        mContext.registerReceiver(myReceiver, intentFilter);
        initMinChart();
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//                barChart.highlightValues(new Highlight[]{h});
                Log.d("MyLineChart", "=========== h = " + h);

                lineChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
            }
        });
        initChart(k_chart);
        initChart(k_day_chart);
        showKView();
    }

    /**
     * 三个fragment切换触发
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisivle = true;
            if (isResume) {
                refreshViewState();
            }
        } else {
            isVisivle = false;
            if (mTimeCount != null) {
                mTimeCount.finishTimeCount();
                mTimeCount = null;
            }
        }
    }

    /**
     * 进入其他Activity返回后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        refreshViewState();
    }

    @Override
    public void onPause() {
        super.onPause();
        isResume = false;
        if (mTimeCount != null) {
            mTimeCount.finishTimeCount();
            mTimeCount = null;
        }
    }

    @Override
    public void onDestroy() {
        if (myReceiver != null) {
            mContext.unregisterReceiver(myReceiver);
        }
        super.onDestroy();
    }

    private void refreshViewState() {
        if (UserUtil.getIsLogin(mContext)) {
            if (AccountUtil.isGetAccountInfo()) {
                tv_balance.setText(UserUtil.getAvailableBalance(mContext));
                tv_ticket.setText(UserUtil.getTotalTicket(mContext) + "");
            }
            showGuideView();
        }
        if (AccountUtil.isNeedAccess()) {
            getAccessToken();
        } else {
            repeatPullKData();
            getBuyUpAmount("1");
            if (!isIndexLogin) {
                indexLogin();
            }
            if (!isStartMessage) {
                getUserMoneyList();
            }
            if (!isGetImage) {
                getImageList();
            }
            if (mProList.size() == 0) {
                getGoodsList();
            }
        }
        if (AccountUtil.isTradeRefresh()) {
            onLogoutState();
            if (UserUtil.getIsLogin(mContext)) {
                bt_buy_up.setEnabled(false);
                bt_buy_down.setEnabled(false);
                ProTabChanged();
                AccountUtil.setIsTradeRefresh(false);
            } else {
                AccountUtil.setIsTradeRefresh(false);
            }
        }
    }

    private void onLogoutState() {
        tv_balance.setText("--");
        tv_ticket.setText("--");
        bt_buy_up.setEnabled(true);
        bt_buy_down.setEnabled(true);
        ll_buy.setVisibility(View.VISIBLE);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_trade;
    }

    @Override
    protected void findViews(View rootView) {
        pro_group = (ProGroupView) rootView.findViewById(R.id.pro_group);
        sv_scale = (ScaleView) rootView.findViewById(R.id.sv_scale);
        bt_buy_up = (Button) rootView.findViewById(R.id.bt_buy_up);
        bt_buy_down = (Button) rootView.findViewById(R.id.bt_buy_down);
        tv_high = (TextView) rootView.findViewById(R.id.tv_high);
        tv_low = (TextView) rootView.findViewById(R.id.tv_low);
        tv_yesterday_end = (TextView) rootView.findViewById(R.id.tv_yesterday_end);
        tv_beginning = (TextView) rootView.findViewById(R.id.tv_beginning);
        tv_balance = (TextView) rootView.findViewById(R.id.tv_balance);
        tv_ticket = (TextView) rootView.findViewById(R.id.tv_ticket);
        tv_trade_time = (TextView) rootView.findViewById(R.id.tv_trade_time);
        ll_buy = rootView.findViewById(R.id.ll_buy);
        fl_k_line = rootView.findViewById(R.id.fl_k_line);
        ll_ticket_top = rootView.findViewById(R.id.ll_ticket_top);
        rl_trade = rootView.findViewById(R.id.rl_trade);
        tv_message = (RollingUpTextView) rootView.findViewById(R.id.tv_message);
        icv_image = (ImageCycleView) rootView.findViewById(R.id.icv_image);
        iv_head = (ImageView) rootView.findViewById(R.id.iv_head);
        iv_k_line_bg = (ImageView) rootView.findViewById(R.id.iv_k_line_bg);
        rg_k_line = (RadioGroup) rootView.findViewById(R.id.rg_k_line);

        k_chart = (MyKChart) rootView.findViewById(R.id.k_chart);
        k_day_chart = (MyKChart) rootView.findViewById(R.id.k_day_chart);
        lineChart = (MyLineChart) rootView.findViewById(R.id.line_chart);
        pb_loading = (ProgressBar) rootView.findViewById(R.id.pb_loading);
        tv_failed = (TextView) rootView.findViewById(R.id.tv_failed);
    }

    @Override
    protected void setListensers() {
        bt_buy_up.setOnClickListener(this);
        bt_buy_down.setOnClickListener(this);
        pro_group.setOnCheckedChangedListener(this);
        rg_k_line.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_buy_up:
                if (UserUtil.getIsLogin(mContext)) {
                    createOrder(1);
                } else {
                    gotoActivity(mContext, LoginActivity.class, null);
                }
                break;
            case R.id.bt_buy_down:
                if (UserUtil.getIsLogin(mContext)) {
                    createOrder(2);
                } else {
                    gotoActivity(mContext, LoginActivity.class, null);
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        pro_group.setChangeAble(true);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE_ORDER:
                    LogUtil.d("ret", "onActivityResult==REQUEST_CREATE_ORDER");
                    ((MainActivity) getActivity()).turnToPage(1, false);
                    break;
            }
        }
    }

    private void indexLogin() {
        JSONObject jsonObject = new JSONObject();
        new UserAction(mContext).indexLogin(jsonObject, new BaseNetCallBack<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean paramT) {
                isIndexLogin = true;
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
            }
        });
    }

    private void getGoodsList() {
        JSONObject jsonObject = new JSONObject();
        new GoodsAction(mContext).getGoodsList(jsonObject, new BaseNetCallBack<ResProGroupListBean>() {
            @Override
            public void onSuccess(ResProGroupListBean paramT) {
                mProList.clear();
                mProList.addAll(paramT.getResponse().getData().getList());
                pro_group.setProList(mProList);
                ProTabChanged();
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
            }
        });
    }


    private void getUserMoneyList() {
        new GoodsAction(mContext).getUserMoneyList(new JSONObject(), new BaseNetCallBack<ResUserMoneyBean>() {
            @Override
            public void onSuccess(ResUserMoneyBean paramT) {
                tv_message.setData(paramT.getResponse().getData().getList());
                isStartMessage = true;
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

            }
        });
    }

    private void getImageList() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "static_img_list");
            new UserAction(mContext).getImageList(jsonObject, new BaseNetCallBack<ResImageListBean>() {
                @Override
                public void onSuccess(ResImageListBean paramT) {
                    isGetImage = true;
                    final ArrayList<ADInfo> list = paramT.getResponse().getData().getList();
                    if (list.size() > 1) {
                        icv_image.setVisibility(View.VISIBLE);
                        iv_head.setVisibility(View.GONE);
                        icv_image.setImageResources(list, mAdCycleViewListener);
                    } else if (list.size() == 1) {
                        iv_head.setVisibility(View.VISIBLE);
                        icv_image.setVisibility(View.GONE);
                        mBitmaputils.display(iv_head, list.get(0).getImg());// 使用ImageLoader对图片进行加装！
                        iv_head.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle = new Bundle();
                                bundle.putString("url", list.get(0).getUrl());
                                bundle.putString("title", "活动");
                                gotoActivity(mContext, WebViewActivity.class, bundle);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            Bundle bundle = new Bundle();
            bundle.putString("url", info.getUrl());
            bundle.putString("title", "活动");
            gotoActivity(mContext, WebViewActivity.class, bundle);
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            mBitmaputils.display(imageView, imageURL);// 使用ImageLoader对图片进行加装！
        }
    };

    private void createOrder(int trade_type) {
        createOrder(trade_type, false);
    }

    private void createOrder(int trade_type, boolean showGuide) {
        if (mProList.size() > 0) {
            pro_group.setChangeAble(false);
            Intent intent = new Intent(mContext, BuyActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("price", mPrice);
            bundle.putBoolean("showGuide", showGuide);
            bundle.putInt("type", trade_type);
            bundle.putSerializable("pro", mProList.get(pro_group.getCheckedIndex()));
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CREATE_ORDER);
        }
    }


    @Override
    public void onCheckedChanged(int index) {
        mKLineId = pro_group.getCheckedIndex() * 2 + 1;
        showKView();
        repeatPullKData();
        ProTabChanged();
    }

    private void ProTabChanged() {
        try {
            setHighLowPrice();
            getBuyUpAmount(mProList.get(pro_group.getCheckedIndex()).getGoods_list().get(0).getGoods_id());
            if (mProList.get(pro_group.getCheckedIndex()).getDuring_type() == 1) {
                tv_trade_time.setTextColor(Color.parseColor("#CC777777"));
            } else {
                tv_trade_time.setTextColor(Color.parseColor("#FFF54337"));
            }
            boolean enable = mProList.size() > 0 && mProList.get(pro_group.getCheckedIndex()).getDuring_type() == 1;
            if (!UserUtil.getIsLogin(mContext) || enable) {
                bt_buy_up.setEnabled(true);
                bt_buy_down.setEnabled(true);
            } else {
                bt_buy_up.setEnabled(false);
                bt_buy_down.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHighLowPrice() {
        ProGroupBean group = mProList.get(pro_group.getCheckedIndex());
        tv_high.setText("最高" + group.getHigh_price());
        tv_low.setText("最低" + group.getLowwest_price());
        tv_yesterday_end.setText("昨收" + group.getPrice_end_lastday());
        tv_beginning.setText("开盘" + group.getPrice_beginning());
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {

        switch (id) {
            case R.id.rb_real_time:
                KLineDataUtil.getInstance().setNeedDrawByHandFirstTime(false);
                mCurrentKtype = 99;
                mKLineTime = 30 * 1000;
                showKView();
//                repeatPullKData();
                dealfive();

                break;
            case R.id.rb_5_min:
                mcurrentChose = "5";
                KLineDataUtil.getInstance().setNeedDrawByHandFirstTime(true);
                mCurrentKtype = 2;
                mKLineTime = 3 * 60 * 1000;
                showKView();
//                repeatPullKData();
                dealfive();


                break;
            case R.id.rb_15_min:
                KLineDataUtil.getInstance().setNeedDrawByHandFirstTime(true);
                mCurrentKtype = 3;
                mKLineTime = 8 * 60 * 1000;
                showKView();
//                repeatPullKData();
                dealfive();
                break;
            case R.id.rb_30_min:
                KLineDataUtil.getInstance().setNeedDrawByHandFirstTime(true);
                mKLineTime = 15 * 60 * 1000;
                mCurrentKtype = 4;
                showKView();
                repeatPullKData();
                break;
            case R.id.rb_60_min:
                KLineDataUtil.getInstance().setNeedDrawByHandFirstTime(true);
                mKLineTime = 30 * 60 * 1000;
                mCurrentKtype = 5;
                showKView();
                repeatPullKData();
                break;
            case R.id.rb_day:
                KLineDataUtil.getInstance().setNeedDrawByHandFirstTime(true);
                mKLineTime = 10 * 60 * 60 * 1000;
                mCurrentKtype = 8;
                showKView();
                repeatPullKData();
                break;
            default:
                break;
        }
    }

    private void dealfive() {
        if (mTimeCount != null) {
            mTimeCount.finishTimeCount();
            mTimeCount = null;
        }
        mTimeCount = new TimeCount(Long.MAX_VALUE, mKLineTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        readJson();
                    }
                });
            }
        };
        mTimeCount.start();
    }

    class MyReceiver extends BroadcastReceiver {
        private String json = "";

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Globparams.TCP_PRICE_CHANGE_ACTION:
                    proPriceChanged(intent);
                    break;
                case Globparams.REFRESH_ACCOUNT_SUCCESS_ACTION:
                    tv_ticket.setText(UserUtil.getTotalTicket(mContext) + "");
                    tv_balance.setText(UserUtil.getAvailableBalance(mContext));
                    showGuideView();
                    break;
                case "closeGuide":
                    if (guideView3 != null) {
                        guideView3.hide();
                    }
                    break;
            }
        }

        private void proPriceChanged(Intent intent) {
            try {
                LogUtil.d("ret", "TCP===" + intent.getStringExtra("data"));
                String data = intent.getStringExtra("data");
                if (!data.equals(json)) {
                    json = data;
                } else {
                    return;
                }
                JSONArray array = new JSONArray(intent.getStringExtra("data"));
                if (mProList.size() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        LatestProPriceBean bean = GsonUtil.json2bean(array.getString(i), LatestProPriceBean.class);
                        for (ProGroupBean group : mProList) {
                            if (bean.getPro_code().equals(group.getPro_code())) {
                                group.setPrice_beginning(bean.getPrice_beginning());
                                group.setDuring_type(bean.getDuring_type());
                                group.setShowUpOrDown(bean.getDuring_type() == 1);
                                group.setPrice_end_lastday(bean.getPrice_end_lastday());
                                if (bean.getLatest_price() >= group.getLatest_price()) {
                                    group.setUp(true);
                                } else if (bean.getLatest_price() < group.getLatest_price()) {
                                    group.setUp(false);
                                }
                                group.setLatest_price(bean.getLatest_price());
                                group.setHigh_price(bean.getHigh_price());
                                group.setLowwest_price(bean.getLowwest_price());
                                group.setRise(bean.getRise());
                                break;
                            }
                        }
                    }
                    pro_group.setProList(mProList);
                    setHighLowPrice();
                    if (mProList.get(pro_group.getCheckedIndex()).getDuring_type() == 1) {
                        tv_trade_time.setTextColor(Color.parseColor("#CC777777"));
                    } else {
                        tv_trade_time.setTextColor(Color.parseColor("#FFF54337"));
                    }
                    boolean enable = mProList.size() > 0 && mProList.get(pro_group.getCheckedIndex()).getDuring_type() == 1;
                    if (!UserUtil.getIsLogin(mContext) || enable) {
                        bt_buy_up.setEnabled(true);
                        bt_buy_down.setEnabled(true);
                    } else {
                        bt_buy_up.setEnabled(false);
                        bt_buy_down.setEnabled(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void getAccessToken() {
        AccountUtil.setIsNeedAccess(true);
        new AccessToken(mContext).getAccessToken(new BaseNetCallBack<ResAccessTokenBean>() {
            @Override
            public void onSuccess(ResAccessTokenBean paramT) {
                UserUtil.setUserInfo(mContext, paramT);
                getImageList();
                getGoodsList();
                getKLineData();
                repeatPullKData();
                if (!isStartMessage) {
                    getUserMoneyList();
                }
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
            }
        });
    }

    private void getBuyUpAmount(String id) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("goods_id", id);
            new TradeAction(mContext).getUpAndDown(jsonObject, new BaseNetCallBack<ResUpAndDownBean>() {
                @Override
                public void onSuccess(ResUpAndDownBean paramT) {
                    sv_scale.setUpScale(paramT.getResponse().getData().getType1());
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 分时图

    private void initMinChart() {

        lineChart.setNoDataText("");
        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(false);
//        lineChart.setBorderWidth(1);
        lineChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        lineChart.setDescription("");
        lineChart.setMinOffset(1f);
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);



        //x轴
        xAxisLine = lineChart.getXAxis();
        xAxisLine.setDrawLabels(true);
        xAxisLine.setDrawGridLines(false);
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);


        //左边y
        axisLeftLine = lineChart.getAxisLeft();
        /*折线图y轴左没有basevalue，调用系统的*/
        axisLeftLine.setLabelCount(5, false);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawGridLines(false);
        /*轴不显示 避免和border冲突*/
        axisLeftLine.setDrawAxisLine(true);
        axisLeftLine.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);


//        //右边y
//        axisRightLine = lineChart.getAxisRight();
//        axisRightLine.setLabelCount(2, true);
//        axisRightLine.setDrawLabels(false);
//        axisRightLine.setValueFormatter(new YAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, YAxis yAxis) {
//                DecimalFormat mFormat = new DecimalFormat("#0.00%");
//                return mFormat.format(value);
//            }
//        });
//
//        axisRightLine.setStartAtZero(false);
//        axisRightLine.setDrawGridLines(false);
//        axisRightLine.setDrawAxisLine(false);
        //背景线
        xAxisLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
//        axisRightLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
//        axisRightLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));

//        //y轴样式
        this.axisLeftLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0");
                return mFormat.format(value);
            }
        });
    }

    // k线图
    private void initChart(final MyKChart k_chart) {
        colorHomeBg = getResources().getColor(R.color.common_white);
        colorLine = getResources().getColor(R.color.common_divider);
        colorText = getResources().getColor(R.color.text_grey_light);
//        colorMa5 = getResources().getColor(R.color.ma5);
//        colorMa10 = getResources().getColor(R.color.ma10);
//        colorMa20 = getResources().getColor(R.color.ma20);

        k_chart.setDescription("");
        k_chart.setDrawGridBackground(false);
        k_chart.setBackgroundColor(colorHomeBg);
        k_chart.setGridBackgroundColor(colorHomeBg);
        k_chart.setScaleYEnabled(false);
        k_chart.setScaleXEnabled(true);
        k_chart.setPinchZoom(true);
        k_chart.setDrawValueAboveBar(false);
        k_chart.setNoDataText("");
        k_chart.setAutoScaleMinMaxEnabled(true);
        k_chart.setDragEnabled(true);
        k_chart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});

        k_chart.setVisibleXRangeMaximum(MAX_VISIBLE_RANGE);
        k_chart.setVisibleXRangeMinimum(MIN_VISIBLE_RANGE);
        k_chart.zoom(ZOOM_NUM_X, 1.0f, 1.0f, 1.0f);
        k_chart.setMinOffset(5f);

        XAxis xAxis = k_chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
//        xAxis.setGridColor(Color.WHITE);
        xAxis.setTextColor(colorText);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setSpaceBetweenLabels(4);

        YAxis leftAxis = k_chart.getAxisLeft();
        leftAxis.setLabelCount(5, true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(true);
//        leftAxis.setGridColor(Color.WHITE);
        leftAxis.setTextColor(colorText);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

//        YAxis rightAxis = k_chart.getAxisRight();
//        rightAxis.setEnabled(false);

//        int[] colors = {colorMa5, colorMa10, colorMa20};
//        String[] labels = {"MA5", "MA10", "MA20"};
        Legend legend = k_chart.getLegend();
//        legend.setCustom(colors, labels);
        legend.setEnabled(false);
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
//        legend.setTextColor(Color.WHITE);

        k_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                CandleEntry candleEntry = (CandleEntry) entry;
                float change = (candleEntry.getOpen() - candleEntry.getClose()) / candleEntry.getOpen();
                NumberFormat nf = NumberFormat.getPercentInstance();
                nf.setMaximumFractionDigits(2);
                String changePercentage = nf.format(Double.valueOf(String.valueOf(change)));

                k_chart.setHighlightValue(highlight);
                Log.d("ret", "最高" + candleEntry.getHigh() + " 最低" + candleEntry.getLow() +
                        " 开盘" + candleEntry.getOpen() + " 收盘" + candleEntry.getClose() +
                        " 涨跌幅" + changePercentage);
                PointF pointF = k_chart.getPosition(entry, YAxis.AxisDependency.LEFT);
                Log.d("ret", "x = " + pointF.x + " ; y = " + pointF.y);
                Log.d("ret", "i = " + i);
                String key = "k_" + mKLineId + "_" + mCurrentKtype;
//                List<KLineItemBean> list = kLineMap.get(key).getResponse().getData().getList();
//                String formatTime = list.get(entry.getXIndex()).getTime();
                k_chart.setPositionData(k_chart.getMeasuredHeight(), k_chart.getMeasuredWidth(), pointF.x, pointF.y);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setKLineMarkerView(MyKChart k_chart, List<KLineItemBean> kLineListBeanList) {
        MyKChartMarkerView myKChartMarkerView = new MyKChartMarkerView(getActivity(), R.layout.my_k_chart_marker_view);
        k_chart.setMarker(myKChartMarkerView, null, kLineListBeanList);
    }

    private void setData(KLineListBean kLineListBean_realTime) {
        List<KLineItemBean> kList = kLineListBean_realTime.getResponse().getData().getList();
        setMarkerView(kLineListBean_realTime);
        stringSparseArray = kLineListBean_realTime.getResponse().getData().getTimeList();
        setShowLabels(stringSparseArray);
//        Log.e("###", mData.getDatas().size() + "ee");
        if (kList.size() == 0) {
            lineChart.setNoDataText("暂无数据");
            return;
        }
        //设置y左右两轴最大最小值
        float min = kLineListBean_realTime.getResponse().getData().getMinPrice();
        float max = kLineListBean_realTime.getResponse().getData().getMaxPrice();
        axisLeftLine.setAxisMinValue(min);
        axisLeftLine.setAxisMaxValue(max);

        //基准线
        LimitLine ll = new LimitLine(0);
        ll.setLineColor(getResources().getColor(R.color.minute_jizhun));
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLineWidth(1f);

        ArrayList<Entry> lineCJEntries = new ArrayList<>();
        ArrayList<Entry> lineAVEntries = new ArrayList<>();
        for (int i = 0, j = 0; i < kList.size(); i++, j++) {
           /* //避免数据重复，skip也能正常显示
            if (mData.getDatas().get(i).time.equals("13:30")) {
                continue;
            }*/
            KLineItemBean t = kList.get(j);

            if (t == null) {
                lineCJEntries.add(new Entry(Float.NaN, i));
                lineAVEntries.add(new Entry(Float.NaN,i));
                continue;
            }
            if (!TextUtils.isEmpty(stringSparseArray.get(i)) &&
                    stringSparseArray.get(i).contains("/")) {
                i++;
            }
            String Now = kList.get(i).getNow();
            String Avg = kList.get(i).getAvge();
            if (Now == null || "".equals(Now)) {
                Now = "0";
            }
            lineCJEntries.add(new Entry(Float.parseFloat(Now), i));
            lineAVEntries.add(new Entry(Float.parseFloat(Avg),i));
        }
        d1 = new LineDataSet(lineCJEntries, "成交价");
        d2 = new LineDataSet(lineAVEntries, "均价");
        d1.setDrawValues(false);
        d2.setDrawValues(false);

        d1.setCircleRadius(0);
        d2.setCircleRadius(0);
        d1.setColor(getResources().getColor(R.color.minute_blue));
        d2.setColor(getResources().getColor(R.color.minute_yellow));
        d1.setHighLightColor(Color.DKGRAY);
        d2.setHighlightEnabled(false);
        d1.setDrawFilled(true);

        //谁为基准
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        d2.setAxisDependency(YAxis.AxisDependency.LEFT);
        ArrayList<ILineDataSet> sets = new ArrayList<>();


        sets.add(d1);
        sets.add(d2);
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData cd = new LineData(kLineListBean_realTime.getResponse().getData().getTimeStrList(), sets);
        lineChart.setData(cd);

        setOffset();
        lineChart.invalidate();//刷新图
    }

    private void setMarkerView(KLineListBean kLineListBean_realTime) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.mymarkerview);
////        MyRightMarkerView rightMarkerView = new MyRightMarkerView(getActivity(), R.layout.mymarkerview);
        lineChart.setMarker(leftMarkerView, null, kLineListBean_realTime);
    }

    public void setShowLabels(SparseArray<String> labels) {
        xAxisLine.setXLabels(labels);
    }

    /*设置量表对齐*/
    private void setOffset() {
//
    }

    private void getKLineData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("goods_id", mKLineId);
            jsonObject.put("type", mCurrentKtype);
            GetKLineData mGetKLineData = new GetKLineData(mContext);
            final int kid = mKLineId;
            final int kType = mCurrentKtype;
            if (kLineMap.get("k_" + kid + "_" + kType) == null) {
                pb_loading.setVisibility(View.VISIBLE);
            }
            mGetKLineData.getKLineData(jsonObject, false, new BaseNetCallBack<KLineListBean>() {
                @Override
                public void onSuccess(KLineListBean paramT) {

                    successDeal(paramT);
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

                    failureDeal(kid, kType);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void failureDeal(int kid, int kType) {
        pb_loading.setVisibility(View.GONE);
        if (kid == mKLineId && kType == mCurrentKtype && kLineMap.get("k_" + kid + "_" + kType) == null) {
            tv_failed.setVisibility(View.VISIBLE);
        }
    }

    private void successDeal(KLineListBean paramT) {
        pb_loading.setVisibility(View.GONE);
        int id = paramT.getResponse().getData().getData().getGoods_id();
        int type = paramT.getResponse().getData().getData().getType();
        String key = "k_" + id + "_" + type;
        if (type == 99) {
            formatRealTimeDataBean(paramT);
        } else if (type == 8) {
            formatDayKLineDataBean(paramT);
        } else {
            format15or60KLineDataBean(paramT);
        }
        kLineMap.put(key, paramT);
        if (id == mKLineId && type == mCurrentKtype) {
            showKView();
        }
    }

    private CandleData generateCandleData(List<CandleEntry> candleEntries) {

        CandleDataSet set = new CandleDataSet(candleEntries, "");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(0.7f);
        set.setDecreasingColor(Color.rgb(18, 188, 101));
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(Color.rgb(245, 67, 55));
        set.setIncreasingPaintStyle(Paint.Style.FILL);
//        set.setIncreasingPaintStyle(Paint.Style.STROKE);
        set.setNeutralColor(Color.RED);
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(0.5f);
        set.setHighLightColor(Color.DKGRAY);
        set.setDrawValues(false);

        CandleData candleData = new CandleData(xVals);
        candleData.addDataSet(set);

        return candleData;
    }

    private void adjustVisibleRegion(CombinedChart chart, float xMovePos) {
        chart.moveViewToX(xMovePos);
        chart.setVisibleXRangeMaximum(MAX_VISIBLE_RANGE);
        chart.setVisibleXRangeMinimum(MIN_VISIBLE_RANGE);
    }

    private void showKView() {
        tv_failed.setVisibility(View.GONE);
        String key = "k_" + mKLineId + "_" + mCurrentKtype;
        if (kLineMap.get(key) != null) {
            if (mCurrentKtype == 99) {
                lineChart.resetTracking();
                KLineListBean bean = kLineMap.get(key);
//                formatRealTimeDataBean(bean);
                setData(bean);
                iv_k_line_bg.setVisibility(View.INVISIBLE);
                KLineDataUtil.getInstance().setmKChartHeight(iv_k_line_bg.getHeight());
//                iv_k_line_bg.setBackgroundResource(R.color.backgroud);
                lineChart.setVisibility(View.VISIBLE);
                k_chart.setVisibility(View.GONE);
                k_day_chart.setVisibility(View.GONE);
            } else {
                k_chart.resetTracking();
                List<KLineItemBean> list = kLineMap.get(key).getResponse().getData().getList();
                List<CandleEntry> candleEntries = kLineMap.get(key).getCandleEntries(mContext, list);
                int itemcount = list.size();
                xVals = new ArrayList<>();
                for (int i = 0; i < itemcount; i++) {
                    xVals.add(list.get(i).getTime());
                }
                combinedData = new CombinedData(xVals);
                candleData = generateCandleData(candleEntries);
                combinedData.setData(candleData);
                iv_k_line_bg.setVisibility(View.VISIBLE);
                if (mCurrentKtype == 8) {
                    k_day_chart.resetTracking();
//                    formatDayKLineDataBean(kLineMap.get(key));
                    k_day_chart.setData(combinedData);//当前屏幕会显示所有的数据
                    adjustVisibleRegion(k_day_chart, (float) (list.size()));
                    setKLineMarkerView(k_day_chart, list);
                    k_day_chart.invalidate();
                    k_chart.setVisibility(View.GONE);
                    k_day_chart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.GONE);
                } else {
                    setKLineMarkerView(k_chart, list);
                    k_chart.setData(combinedData);//当前屏幕会显示所有的数据
                    adjustVisibleRegion(k_chart, (float) (list.size()));
                    k_chart.invalidate();
                    k_chart.setVisibility(View.VISIBLE);
                    k_day_chart.setVisibility(View.GONE);
                    lineChart.setVisibility(View.GONE);
                }
            }
        } else {
            lineChart.setVisibility(View.GONE);
            k_chart.setVisibility(View.GONE);
            k_day_chart.setVisibility(View.GONE);
        }
    }

    /**
     * 格式化处理服务器拉取的15分钟和60分钟k线图数据
     *
     * @param bean
     */
    private void format15or60KLineDataBean(KLineListBean bean) {
        if (!bean.isFormat) {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
            for (int i = 0; i < bean.getResponse().getData().getList().size(); i++) {
                KLineItemBean item = bean.getResponse().getData().getList().get(i);
                Date date = new Date(Long.parseLong(item.getTime(), 10));
                bean.getResponse().getData().getList().get(i).setTime(format.format(date));
            }
            bean.isFormat = true;
        }
    }

    /**
     * 格式化处理服务器拉取的riki线图数据
     *
     * @param bean
     */
    private void formatDayKLineDataBean(KLineListBean bean) {
        if (!bean.isFormat) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
            for (int i = 0; i < bean.getResponse().getData().getList().size(); i++) {
                KLineItemBean item = bean.getResponse().getData().getList().get(i);
                Date date = new Date(Long.parseLong(item.getTime(), 10));
                bean.getResponse().getData().getList().get(i).setTime(format.format(date).substring(2));
            }
            bean.isFormat = true;
        }
    }

    private void repeatPullKData() {
        if (mTimeCount != null) {
            mTimeCount.finishTimeCount();
            mTimeCount = null;
        }
        mTimeCount = new TimeCount(Long.MAX_VALUE, mKLineTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getKLineData();
                    }
                });
            }
        };
        mTimeCount.start();
    }

    /**
     * 格式化处理服务器拉取的分时数据
     *
     * @param bean
     */
    private void formatRealTimeDataBean(KLineListBean bean) {
        for (int i = 0; i < bean.getResponse().getData().getList().size(); i++) {
            KLineItemBean item = bean.getResponse().getData().getList().get(i);
            if ("".equals(item.getNow())) {
                bean.getResponse().getData().getList().get(i).setNow("0");
            }
        }
    }

    GuideView guideView1 = null;
    GuideView guideView2 = null;
    GuideView guideView3 = null;

    private void showGuideView() {
        if (UserUtil.getIsGuideTicket(mContext)) {
            return;
        }
        if (isVisivle && isResume && AccountUtil.isNeedGuideTicket() && UserUtil.getTicket10(mContext) > 0) {
            final ImageView iv_ticket = new ImageView(mContext);
            iv_ticket.setImageResource(R.mipmap.icon_guide_ticket);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv_ticket.setLayoutParams(params);

            final ImageView iv_k_line = new ImageView(mContext);
            iv_k_line.setImageResource(R.mipmap.icon_guide_k_line);
            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv_k_line.setLayoutParams(params1);

            final ImageView iv_buy = new ImageView(mContext);
            iv_buy.setImageResource(R.mipmap.icon_guide_buy);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv_buy.setLayoutParams(params2);


            CustomGuideView view1 = new CustomGuideView(iv_ticket, GuideView.Direction.BOTTOM);
            guideView1 = GuideView.Builder
                    .newInstance(mContext)
                    .setTargetView(ll_ticket_top)//设置目标
                    .addCustomGuideView(view1)
                    .setShape(GuideView.MyShape.CIRCULAR)   // 设置圆形显示区域，
                    .setOnclickListener(new GuideView.OnClickCallback() {
                        @Override
                        public void onClickedGuideView() {
                            guideView1.hide();
                            guideView2.show();
                        }
                    })
                    .build();

            CustomGuideView view2 = new CustomGuideView(iv_k_line, GuideView.Direction.TOP);
            guideView2 = GuideView.Builder
                    .newInstance(mContext)
                    .setTargetView(fl_k_line)//设置目标
                    .addCustomGuideView(view2)
                    .setShape(GuideView.MyShape.RECT)
                    .setOnclickListener(new GuideView.OnClickCallback() {
                        @Override
                        public void onClickedGuideView() {
                            guideView2.hide();
                            guideView3.show();
                        }
                    })
                    .build();

            CustomGuideView view3 = new CustomGuideView(iv_buy, GuideView.Direction.TOP);
            guideView3 = GuideView.Builder
                    .newInstance(mContext)
                    .setTargetView(rl_trade)//设置目标
                    .addCustomGuideView(view3)
                    .setShape(GuideView.MyShape.RECT)   // 设置圆形显示区域，
                    .setOnclickListener(new GuideView.OnClickCallback() {
                        @Override
                        public void onClickedGuideView() {
                            createOrder(1, true);
                        }
                    })
                    .build();

            guideView1.show();
            UserUtil.setIsGuideTicket(mContext, true);
        }
    }

    public void readJson (){
        try {
        final int kid = mKLineId;
        final int kType = mCurrentKtype;
        if (kLineMap.get("k_" + kid + "_" + kType) == null) {
            pb_loading.setVisibility(View.VISIBLE);
        }
            if (mCurrentKtype==3){
                KLineListBean bean = GsonUtil.json2bean(com.koudai.operate.data.LineData.getLine15(), KLineListBean.class);
                successDeal(bean);
            }else  if (mCurrentKtype ==99){
                BufferedReader br = new BufferedReader(new InputStreamReader(getContext().getAssets().open("KLineAvBean.txt")));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                KLineListBean bean = GsonUtil.json2bean(sb.toString(), KLineListBean.class);
                successDeal(bean);
            }

            else {
                BufferedReader br = new BufferedReader(new InputStreamReader(getContext().getAssets().open("KLineListBeanJson.txt")));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                KLineListBean bean = GsonUtil.json2bean(sb.toString(), KLineListBean.class);
                successDeal(bean);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

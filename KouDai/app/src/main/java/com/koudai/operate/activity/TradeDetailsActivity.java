package com.koudai.operate.activity;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.koudai.operate.R;
import com.koudai.operate.adapter.TradeDetailsAdapter;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.model.OrderListItemBean;
import com.koudai.operate.model.ResHistoryOrderListBean;
import com.koudai.operate.net.api.TradeAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TradeDetailsActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    private List<OrderListItemBean> mList = new ArrayList<OrderListItemBean>();
    private TradeDetailsAdapter mAdapter;
    private PullToRefreshExpandableListView lv_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mList.size()==0){
            getOrderList();
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_trade_details;
    }

    @Override
    protected void findViews() {
        lv_details = (PullToRefreshExpandableListView) findViewById(R.id.lv_details);
    }

    @Override
    protected void setListensers() {
        lv_details.setOnRefreshListener(this);
    }

    private void init() {
        mAdapter = new TradeDetailsAdapter(mList, this);
        lv_details.getRefreshableView().setAdapter(mAdapter);
    }

    private void getOrderList() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page", 1);
            jsonObject.put("page_count", 10000);
            new TradeAction(this).getOrderList(jsonObject, new BaseNetCallBack<ResHistoryOrderListBean>() {
                @Override
                public void onSuccess(ResHistoryOrderListBean paramT) {
                    mList.clear();
                    mList.addAll(paramT.getResponse().getData().getList());
                    mAdapter.notifyDataSetChanged();
                    if (lv_details.isRefreshing()) {
                        lv_details.onRefreshComplete();
                    }
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
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getOrderList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
}

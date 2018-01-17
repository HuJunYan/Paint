package com.koudai.operate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.koudai.operate.R;
import com.koudai.operate.adapter.RechargeHistoryAdapter;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.model.MoneyLogBean;
import com.koudai.operate.model.ResMoneyLogListBean;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RechargeHistoryActivity extends BaseActivity {
    private ListView lv_money_log;
    private boolean isInit=false;

    @Override
    protected void onResume() {
        super.onResume();
        if(!isInit){
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("page", 1);
                new UserAction(this).getUserMoneyLog(jsonObject, new BaseNetCallBack<ResMoneyLogListBean>() {
                    @Override
                    public void onSuccess(ResMoneyLogListBean paramT) {
                        if( paramT.getResponse().getData()!=null){
                            RechargeHistoryAdapter adapter = new RechargeHistoryAdapter(RechargeHistoryActivity.this, paramT.getResponse().getData().getList());
                            lv_money_log.setAdapter(adapter);
                            isInit=true;
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
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_recharge_history;
    }

    @Override
    protected void findViews() {
        lv_money_log = (ListView) findViewById(R.id.lv_money_log);
    }

    @Override
    protected void setListensers() {
    }
}

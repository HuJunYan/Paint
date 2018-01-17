package com.koudai.operate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.model.ResBankListBean;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;

import org.json.JSONObject;

import java.util.List;

public class BankListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView lv_bank_list;
    private List<ResBankListBean.Response.Data.Bank> mBankList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new UserAction(this).getBankList(new JSONObject(), new BaseNetCallBack<ResBankListBean>() {
            @Override
            public void onSuccess(ResBankListBean paramT) {
                mBankList = paramT.getResponse().getData().getList();
                ArrayAdapter<ResBankListBean.Response.Data.Bank> adapter = new ArrayAdapter<ResBankListBean.Response.Data.Bank>(BankListActivity.this,
                        R.layout.item_bank, R.id.tv_bank_name, paramT.getResponse().getData().getList());
                lv_bank_list.setAdapter(adapter);
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_bank_list;
    }

    @Override
    protected void findViews() {
        lv_bank_list = (ListView) findViewById(R.id.lv_bank_list);
    }

    @Override
    protected void setListensers() {
        lv_bank_list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra("name", mBankList.get(i).getBank_name());
        setResult(RESULT_OK, intent);
        backActivity();
    }
}
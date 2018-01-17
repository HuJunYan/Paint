package com.koudai.operate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.constant.Globparams;
import com.koudai.operate.model.ProvinceBean;
import com.koudai.operate.model.ResCityListBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.ToastUtil;
import com.koudai.operate.utils.UserUtil;
import com.lidroid.xutils.DbUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WithDrawalActivity extends BaseActivity implements View.OnClickListener {

    private View rl_province;
    private View rl_city;
    private View rl_bank;
    private TextView tv_province;
    private TextView tv_city;
    private TextView tv_account_balance;
    private TextView tv_bank_name;
    private EditText et_amount;
    private EditText et_card_no;
    private EditText et_name;
    private EditText et_branch;
    private Button btn_submit;
    private List<ProvinceBean> mProvinceList = new ArrayList<>();
    private int mCurrentProvince = -1;
    private ArrayList<String> mProList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_account_balance.setText(UserUtil.getAvailableBalance(this));
        new UserAction(this).getCityList(new JSONObject(), new BaseNetCallBack<ResCityListBean>() {
            @Override
            public void onSuccess(ResCityListBean paramT) {
                mProvinceList = paramT.getResponse().getData().getList();
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

            }
        });
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.dark_blue;
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_with_drawal;
    }

    @Override
    protected void findViews() {
        rl_province = findViewById(R.id.rl_province);
        rl_city = findViewById(R.id.rl_city);
        rl_bank = findViewById(R.id.rl_bank);
        tv_bank_name = (TextView) findViewById(R.id.tv_bank_name);
        tv_province = (TextView) findViewById(R.id.tv_province);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_account_balance = (TextView) findViewById(R.id.tv_account_balance);
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_card_no = (EditText) findViewById(R.id.et_card_no);
        et_name = (EditText) findViewById(R.id.et_name);
        et_branch = (EditText) findViewById(R.id.et_branch);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    protected void setListensers() {
        rl_province.setOnClickListener(this);
        rl_city.setOnClickListener(this);
        rl_bank.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_province:
                showProvince();
                break;
            case R.id.rl_city:
                showCitys();
                break;
            case R.id.rl_bank:
                startActivityForResult(new Intent(this, BankListActivity.class), 20);
                break;
            case R.id.btn_submit:
                withDraw();
                break;
        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        if (arg2 != null && arg1 == RESULT_OK) {
            tv_bank_name.setText(arg2.getStringExtra("name"));
        }
    }

    private void showProvince() {
        try {
            if (mProvinceList == null || mProvinceList.size() == 0) {
                return;
            }
            this.hideKeyboard();
            OptionsPickerView localOptionsPickerView = new OptionsPickerView(this);
            localOptionsPickerView.setPicker(getProvinces());
            localOptionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                public void onOptionsSelect(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                    String str = (String) WithDrawalActivity.this.getProvinces().get(paramAnonymousInt1);
                    if (!WithDrawalActivity.this.tv_province.getText().toString().equals(str)) {
                        mCurrentProvince = paramAnonymousInt1;
                        WithDrawalActivity.this.tv_province.setText(str);
                        tv_city.setText(mProvinceList.get(mCurrentProvince).getCity().get(0).getName());
                    }
                }
            });
            localOptionsPickerView.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCitys() {
        try {
            if (mCurrentProvince != -1) {
                this.hideKeyboard();
                OptionsPickerView localOptionsPickerView = new OptionsPickerView(this);
                localOptionsPickerView.setPicker((ArrayList) mProvinceList.get(mCurrentProvince).getCity());
                localOptionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    public void onOptionsSelect(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                        String str = mProvinceList.get(mCurrentProvince).getCity().get(paramAnonymousInt1).getName();
                        if (!WithDrawalActivity.this.tv_city.getText().toString().equals(str)) {
                            WithDrawalActivity.this.tv_city.setText(str);
                        }
                    }
                });
                localOptionsPickerView.show();
            } else {
                ToastUtil.showToast(this, "请选择开卡省份");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void withDraw() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bank_name", tv_bank_name.getText().toString());
            jsonObject.put("branch_name", et_branch.getText().toString());
            jsonObject.put("card_user_name", et_name.getText().toString());
            jsonObject.put("card_no", et_card_no.getText().toString());
            jsonObject.put("amount", et_amount.getText().toString());
            jsonObject.put("province", tv_province.getText().toString());
            jsonObject.put("city", tv_city.getText().toString());
            new UserAction(this).withdraw(jsonObject, new BaseNetCallBack<ResponseBean>() {
                @Override
                public void onSuccess(ResponseBean paramT) {
                    setResult(RESULT_OK);
                    sendBroadcast(new Intent(Globparams.NEED_REFRESH_ACCOUNT_ACTION));
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

    private ArrayList<String> getProvinces() {
        if (mProList.size() == 0) {
            for (int i = 0; i < mProvinceList.size(); i++) {
                mProList.add(mProvinceList.get(i).getName());
            }
        }
        return mProList;
    }

}

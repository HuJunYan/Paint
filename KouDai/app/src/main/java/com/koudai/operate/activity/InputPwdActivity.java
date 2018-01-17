package com.koudai.operate.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.model.ResLoginBean;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class InputPwdActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private EditText et_password;
    private View tv_forget_password;
    private Button btn_submit;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mobile = getIntent().getStringExtra("mobile");
        if (mobile == null || mobile.equals("") || mobile.equals("0")) {
            mobile = UserUtil.getMoblie(this);
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_input_pwd;
    }

    @Override
    protected void findViews() {
        et_password = (EditText) findViewById(R.id.et_password);
        tv_forget_password = findViewById(R.id.tv_forget_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    protected void setListensers() {
        et_password.addTextChangedListener(this);
        tv_forget_password.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                login();
                break;
            case R.id.tv_forget_password:
                Bundle bundle = new Bundle();
                bundle.putString("mobile", mobile);
                gotoActivity(this, FindBackPwdActivity.class, bundle);
                break;
        }
    }

    private void login() {
        String regId = "";
        try {
            regId = JPushInterface.getRegistrationID(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobile);
            jsonObject.put("password", et_password.getText().toString().trim());
            jsonObject.put("type", 1);
            jsonObject.put("push_id", regId);
            new UserAction(this).login(jsonObject, new BaseNetCallBack<ResLoginBean>() {
                @Override
                public void onSuccess(ResLoginBean paramT) {
                    UserUtil.setIsGuideReg(mContext, true);
                    UserUtil.setIsGuideRegBig(mContext, true);
                    UserUtil.setMobile(InputPwdActivity.this, mobile);
                    setResult(RESULT_OK);
                    backActivity();
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 5) {
            btn_submit.setEnabled(true);
        } else {
            btn_submit.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

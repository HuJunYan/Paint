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
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.TimeCount;
import com.koudai.operate.utils.ToastUtil;
import com.koudai.operate.utils.UserUtil;
import com.koudai.operate.view.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, TitleBar.TitleBarListener {
    private EditText et_mobile;
    private EditText et_password;
    private EditText et_enter_password;
    private EditText et_code;
    private TextView tv_get_code;
    private Button btn_submit;
    private TitleBar tb_title;

    private boolean isPhoneInput;
    private boolean isPwdInput;
    private boolean isEnterPwdInput;
    private boolean isCodeInput;
    private UserAction mUserAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserAction = new UserAction(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void findViews() {
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);
        et_enter_password = (EditText) findViewById(R.id.et_enter_password);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tb_title = (TitleBar) findViewById(R.id.tb_title);
    }

    @Override
    protected void setListensers() {
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isPhoneInput = (charSequence.length() == 11);
                setViewEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isPwdInput = (charSequence.length() > 5);
                setViewEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_enter_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isEnterPwdInput = (charSequence.length() > 5);
                setViewEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isCodeInput = (charSequence.length() == 6);
                setViewEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tv_get_code.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tb_title.setTieleBarListener(this);
    }

    private void setViewEnabled() {
        if (isPhoneInput && isPwdInput && isEnterPwdInput) {
            tv_get_code.setEnabled(true);
            if (isCodeInput) {
                btn_submit.setEnabled(true);
            } else {
                btn_submit.setEnabled(false);
            }
        } else {
            tv_get_code.setEnabled(false);
            btn_submit.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_submit:
                userRegister();
                break;
        }
    }

    private void getCode() {
        new TimeCount(tv_get_code, 60000, 1000, "再次获取").start();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", et_mobile.getText().toString());
            mUserAction.getCode(jsonObject, new BaseNetCallBack<ResponseBean>() {
                @Override
                public void onSuccess(ResponseBean paramT) {
                    ToastUtil.showToast(RegisterActivity.this, "验证码发送成功");
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void userRegister() {
        String regId = "";
        try {
            regId = JPushInterface.getRegistrationID(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!et_enter_password.getText().toString().trim().equals(et_password.getText().toString().trim())) {
                ToastUtil.showToast(mContext, "两次输入的密码不一致");
                return;
            }
            final String mobile = et_mobile.getText().toString().trim();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobile);
            jsonObject.put("code", et_code.getText().toString().trim());
            jsonObject.put("trade_password", et_password.getText().toString().trim());
            JSONObject body = new JSONObject();
            body.put("address", "a");
            jsonObject.put("oauth_data", body);
            jsonObject.put("push_id", regId);
            mUserAction.register(jsonObject, new BaseNetCallBack<ResLoginBean>() {
                @Override
                public void onSuccess(ResLoginBean paramT) {
                    UserUtil.setIsGuideReg(mContext, true);
                    UserUtil.setIsGuideRegBig(mContext, true);
                    UserUtil.setMobile(RegisterActivity.this, mobile);
                    setResult(RESULT_OK);
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
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {
        backActivity();
    }
}

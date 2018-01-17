package com.koudai.operate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.model.ResAccessTokenBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.api.AccessToken;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.ToastUtil;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_password;
    private EditText et_enter_password;
    private Button btn_submit;
    private boolean isPwdInput;
    private boolean isEnterPwdInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void findViews() {
        et_password = (EditText) findViewById(R.id.et_password);
        et_enter_password = (EditText) findViewById(R.id.et_enter_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    protected void setListensers() {
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isPwdInput = (charSequence.length() > 5);
                btn_submit.setEnabled(isPwdInput && isEnterPwdInput);
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
                btn_submit.setEnabled(isPwdInput && isEnterPwdInput);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            if (!et_enter_password.getText().toString().trim().equals(et_password.getText().toString().trim())) {
                ToastUtil.showToast(mContext, "两次输入的密码不一致");
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("password", et_password.getText().toString().trim());
            new UserAction(this).updatePwd(jsonObject, new BaseNetCallBack<ResponseBean>() {
                @Override
                public void onSuccess(ResponseBean paramT) {
                    new AccessToken(UpdatePasswordActivity.this).getAccessToken(new BaseNetCallBack<ResAccessTokenBean>() {
                        @Override
                        public void onSuccess(ResAccessTokenBean paramT) {
                            UserUtil.setUserInfo(UpdatePasswordActivity.this, paramT);
                            setResult(RESULT_OK);
                            ToastUtil.showToast(mContext, "密码修改成功，请重新登录");
                            backActivity();
                        }

                        @Override
                        public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
                            setResult(RESULT_OK);
                            backActivity();
                        }
                    });

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

package com.koudai.operate.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateNickNameActivity extends BaseActivity implements TextWatcher, View.OnClickListener {
    private EditText et_nickname;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_update_nick_name;
    }

    @Override
    protected void findViews() {
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    protected void setListensers() {
        et_nickname.addTextChangedListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        btn_submit.setEnabled(et_nickname.length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        try {
            JSONObject jsonObject = new JSONObject();
            final String name = et_nickname.getText().toString().trim();
            jsonObject.put("nickname", name);
            new UserAction(this).updateNickName(jsonObject, new BaseNetCallBack<ResponseBean>() {
                @Override
                public void onSuccess(ResponseBean paramT) {
                    UserUtil.setNickName(UpdateNickNameActivity.this, name);
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
}

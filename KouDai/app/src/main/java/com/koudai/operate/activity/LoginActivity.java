package com.koudai.operate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.utils.UserUtil;
import com.koudai.operate.view.TitleBar;

public class LoginActivity extends BaseActivity implements TextWatcher, View.OnClickListener, TitleBar.TitleBarListener {

    private EditText et_mobile;
    private Button btn_submit;
    private TitleBar tb_login_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!UserUtil.getMoblie(this).equals("0")) {
            Intent intent = new Intent(this, InputPwdActivity.class);
            intent.putExtra("mobile", "0");
            startActivityForResult(intent, 20);
            et_mobile.setText(UserUtil.getMoblie(this));
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tb_login_title = (TitleBar) findViewById(R.id.tb_login_title);
    }

    @Override
    protected void setListensers() {
        et_mobile.addTextChangedListener(this);
        btn_submit.setOnClickListener(this);
        tb_login_title.setTieleBarListener(this);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        if (arg1 == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() == 11) {
            btn_submit.setEnabled(true);
        } else {
            btn_submit.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, InputPwdActivity.class);
        intent.putExtra("mobile", et_mobile.getText().toString().trim());
        startActivityForResult(intent, 20);
    }

    @Override
    public void onLeftClick() {
        startActivityForResult(new Intent(this, RegisterActivity.class), 21);
    }

    @Override
    public void onRightClick() {
        backActivity();
    }
}

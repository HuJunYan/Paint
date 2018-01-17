package com.koudai.operate.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.model.ResAccessTokenBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.api.AccessToken;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONObject;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private View rl_head;
    private View rl_logout;
    private View rl_nickname;
    private View rl_password;
    private TextView tv_version;
    private final int REQ_UPDATE_NICK_NAME = 10;
    private final int REQ_RESET_PASSWORD = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_version.setText(getVersion() + " (" + NetConstantValue.COMMONURI.substring(7, 8) + "-" + NetConstantValue.CLIENTID+")");
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void findViews() {
        rl_head = findViewById(R.id.rl_head);
        rl_logout = findViewById(R.id.rl_logout);
        rl_nickname = findViewById(R.id.rl_nickname);
        rl_password = findViewById(R.id.rl_password);
        tv_version = (TextView) findViewById(R.id.tv_version);
    }

    @Override
    protected void setListensers() {
        rl_logout.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_logout:
                logout();
                break;
            case R.id.rl_nickname:
                gotoTarget(REQ_UPDATE_NICK_NAME, UpdateNickNameActivity.class);
                break;
            case R.id.rl_password:
                gotoTarget(REQ_RESET_PASSWORD, UpdatePasswordActivity.class);
                break;
        }
    }

    private void gotoTarget(int reqCode, Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivityForResult(intent, reqCode);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent arg2) {
        if (resCode == RESULT_OK) {
            switch (reqCode) {
                case REQ_UPDATE_NICK_NAME:
                    setResult(RESULT_OK);
                    backActivity();
                    break;
                case REQ_RESET_PASSWORD:
                    gotoActivity(this, LoginActivity.class, null);
                    finish();
            }
        }
    }

    private void logout() {
        new UserAction(this).logout(new JSONObject(), new BaseNetCallBack<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean paramT) {
                UserUtil.setMobile(SettingActivity.this, "0");
                new AccessToken(SettingActivity.this).getAccessToken(new BaseNetCallBack<ResAccessTokenBean>() {
                    @Override
                    public void onSuccess(ResAccessTokenBean paramT) {
                        UserUtil.setUserInfo(SettingActivity.this, paramT);
                        backActivity();
                    }

                    @Override
                    public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
                        backActivity();
                    }
                });
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
            }

            @Override
            public void onLogout() {
                backActivity();
            }
        });
    }

    private String getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

//    private void setPhoto() {
//        new AlertView("上传头像", null, "取消", null, new String[]{"拍照", "从相册中选择"}, this, AlertView.Style.ActionSheet, new OnItemClickListener() {
//            public void onItemClick(Object paramAnonymous2Object, int paramAnonymous2Int) {
//                if (paramAnonymous2Int == 0)
//                    SettingActivity.this.imageCropHelper.takePicture();
//                while (paramAnonymous2Int != 1)
//                    return;
//                SettingActivity.this.imageCropHelper.openAlbum();
//            }
//        }).show();
//    }
}

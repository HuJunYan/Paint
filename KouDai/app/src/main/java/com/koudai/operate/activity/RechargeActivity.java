package com.koudai.operate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.constant.Globparams;
import com.koudai.operate.model.ResRechargeBean;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONObject;

public class RechargeActivity extends BaseActivity {
    private WebView wv_recharge;
    private String mUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebview();
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.dark_blue;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUrl.equals("")) {
            getRechargeUrl();
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void findViews() {
        wv_recharge = (WebView) findViewById(R.id.wv_recharge);
    }

    @Override
    protected void setListensers() {

    }

    private void getRechargeUrl() {
        new UserAction(this).getRechargeUrl(new JSONObject(), new BaseNetCallBack<ResRechargeBean>() {
            @Override
            public void onSuccess(ResRechargeBean paramT) {
                mUrl = paramT.getResponse().getData().getUrl();
                wv_recharge.loadUrl(mUrl);
            }

            @Override
            public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

            }
        });
    }

    private void initWebview() {
        WebSettings webSettings = wv_recharge.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
//        wv_recharge.loadUrl("http://www.baidu.com");
        //设置Web视图
        wv_recharge.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(mUrl);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv_recharge.canGoBack()) {
                wv_recharge.goBack();//返回上一页面
                return true;
            } else {
                backActivity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean isExtendsBaseOnKeyDown() {
        return false;
    }

    @Override
    protected void onDestroy() {
        sendBroadcast(new Intent(Globparams.NEED_REFRESH_ACCOUNT_ACTION));
        super.onDestroy();
    }
}

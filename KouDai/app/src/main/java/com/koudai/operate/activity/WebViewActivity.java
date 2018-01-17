package com.koudai.operate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.utils.LogUtil;
import com.koudai.operate.view.TitleBar;

public class WebViewActivity extends BaseActivity {
    private WebView wv_web;
    private TitleBar tb_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tb_title.setTitle(getIntent().getExtras().getString("title"));
        WebSettings settings = wv_web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv_web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                LogUtil.d("ret", url);
                view.loadUrl(url);
                return true;
            }
        });
        wv_web.loadUrl(getIntent().getExtras().getString("url"));
        LogUtil.d("ret", "WebViewActivity--" + getIntent().getExtras().getString("url"));
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void findViews() {
        wv_web = (WebView) findViewById(R.id.wv_web);
        tb_title = (TitleBar) findViewById(R.id.tb_title);
    }

    @Override
    protected void setListensers() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv_web.canGoBack()) {
                wv_web.goBack();//返回上一页面
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
}

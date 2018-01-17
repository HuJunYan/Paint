package com.koudai.operate.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.koudai.operate.R;
import com.koudai.operate.base.BaseFragment;
import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.utils.FormatUtil;
import com.koudai.operate.utils.LogUtil;

/**
 * Created by Administrator on 2016/8/3.
 */
public class InviteFragment extends BaseFragment {
    private WebView wv_web;
    private boolean isVisible = false;
    private boolean isResume = false;

    @Override
    protected void initVariable() {
        WebSettings settings = wv_web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv_web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        wv_web.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && wv_web.canGoBack()) {  //表示按返回键 时的操作
                        wv_web.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
        wv_web.loadUrl(NetConstantValue.getFindUrl() + "?time=" + FormatUtil.getDataFromTimestamp(System.currentTimeMillis()));
        LogUtil.d("ret", "fax_url===" + NetConstantValue.getFindUrl() + "?time=" + FormatUtil.getDataFromTimestamp(System.currentTimeMillis()));
    }

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        if (isVisible && isResume) {
            wv_web.loadUrl(NetConstantValue.getFindUrl() + "?time=" + FormatUtil.getDataFromTimestamp(System.currentTimeMillis()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isResume = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            if (isResume) {
                wv_web.loadUrl(NetConstantValue.getFindUrl() + "?time=" + FormatUtil.getDataFromTimestamp(System.currentTimeMillis()));
            }
        } else {
            isVisible = false;
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_invite;
    }

    @Override
    protected void findViews(View rootView) {
        wv_web = (WebView) rootView.findViewById(R.id.wv_web);
    }

    @Override
    protected void setListensers() {
    }
}

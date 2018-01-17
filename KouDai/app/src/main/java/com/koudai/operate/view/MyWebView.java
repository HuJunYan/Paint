package com.koudai.operate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.koudai.operate.net.base.NetCheck;
import com.koudai.operate.utils.LogUtil;
import com.koudai.operate.utils.TimeCount;

/**
 * Created by Administrator on 2016/9/17.
 */
public class MyWebView extends WebView {
    private boolean isFailed = false;
    private String mUrl;
    private Context mContext;
    private boolean isFirstLoad = true;
    private boolean isSuccess = false;
    private MyWebViewListener mListener;

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new MyWebChromeClient());
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void load() {
        if (isFirstLoad || isFailed) {
            isFirstLoad = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            loadUrl(mUrl);
                        }
                    });
                }
            }).start();
        }
    }

    public boolean isFailed() {
        return isFailed;
    }

    public void setFailed(boolean failed) {
        isFailed = failed;
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            MyWebView.this.loadUrl(url);
            return true;
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            LogUtil.d("ret", "webview onReceivedTitle========title=" + title);
            if (title == null || !title.contains("图") || !NetCheck.isNetConnected(mContext)) {
                String data = "";
                MyWebView.this.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
                isFailed = true;
            } else {
                isFailed = false;
                new TimeCount(2000, 2000) {
                    @Override
                    public void onFinish() {
                        isSuccess = true;
                        if (mListener != null) {
                            mListener.onSuccess(MyWebView.this);
                        }
                    }
                }.start();
            }
        }
    }

    public void setMyWebViewListener(MyWebViewListener listener) {
        this.mListener = listener;
    }

    public interface MyWebViewListener {
        void onSuccess(MyWebView view);
    }
}

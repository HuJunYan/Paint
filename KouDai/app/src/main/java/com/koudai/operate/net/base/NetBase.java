package com.koudai.operate.net.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.koudai.operate.R;
import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.model.ResAccessTokenBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.api.AccessToken;
import com.koudai.operate.utils.LogUtil;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.ToastUtil;
import com.koudai.operate.utils.UserUtil;
import com.koudai.operate.utils.Utils;
import com.koudai.operate.utils.ViewUtil;
import com.koudai.operate.view.LogoutDialog;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public abstract class NetBase {
    protected Context mContext;
    private HttpUtils mHttpUtils;
    private BitmapUtils mBitmapUtils;

    public NetBase(Context context) {
        this.mContext = context;
        init();
    }

    public enum ErrorType {
        ERROR, NO_NETWORK, FAILED
    }

    private void init() {
        XUtilsManager mXUtilsManager = XUtilsManager.getInstance(this.mContext);
        this.mHttpUtils = mXUtilsManager.getHttpUtils();
        this.mBitmapUtils = mXUtilsManager.getBitmapUtils();
    }

    private JSONObject getRequestJson(JSONObject data) {
        JSONObject json = new JSONObject();
        try {
            json.put("client_id", NetConstantValue.CLIENTID);
            json.put("token", UserUtil.getToken(mContext));
            json.put("user_id", UserUtil.getUid(mContext));
            json.put("uuid", Utils.getUUID(mContext));
            json.put("app_id", UserUtil.getAppid(mContext));
            json.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    protected void getDataFromServerByPost(String url, JSONObject object, CallBack callBack) {
        getDataFromServerByPost(url, object, null, false, callBack);
    }

    protected void getDataFromServerByPost(String url, JSONObject object, boolean isShowDialog, CallBack callBack) {
        getDataFromServerByPost(url, object, null, isShowDialog, callBack);
    }

    protected void getDataFromServerByPost(final String url, JSONObject reqjson, final View view, boolean isShowDialog, final CallBack callBack) {
        if (!NetCheck.isNetConnected(this.mContext)) {
            ToastUtil.showToast(this.mContext, R.string.check_network);
            callBack.onFailure("", ErrorType.NO_NETWORK, -1000);
            if (view != null) {
                view.setEnabled(true);
            }
            return;
        }

        if (isShowDialog) {
            String loadText = this.mContext.getResources().getText(R.string.loading).toString();
            ViewUtil.createLoadingDialog((Activity) this.mContext, loadText, false);
        }
        StringEntity mStringEntity = null;
        JSONObject json = null;
        if (url.equals(NetConstantValue.getAccessUrl())) {
            json = reqjson;
        } else {
            json = getRequestJson(reqjson);
        }
        try {
            LogUtil.d("ret", "url = " + url + ";  json = " + json);
            mStringEntity = new StringEntity(json.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            MobclickAgent.reportError(this.mContext, LogUtil.getException(e1));
            e1.printStackTrace();
        }
        RequestParams mRequestParams = new RequestParams();
        mRequestParams.setBodyEntity(mStringEntity);
        this.mHttpUtils.send(HttpRequest.HttpMethod.POST, url, mRequestParams, new RequestCallBack<String>() {
            public void onStart() {
                if (view != null)
                    view.setEnabled(false);
            }

            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    if (view != null) {
                        view.setEnabled(true);
                    }
                    String result = (String) responseInfo.result;
                    LogUtil.d("ret", "url = " + url + ";  result = " + result);
                    if (GsonUtil.isSuccess(result)) {
                        callBack.onSuccess(result, url);
                    } else {
                        ResponseBean responseBean = GsonUtil.json2bean(result, ResponseBean.class);
                        int errorCode = GsonUtil.getErrorCode(result);
                        if (errorCode == 401 || errorCode == 402 || errorCode == 403) {
                            UserUtil.setUid(mContext, "0");
                            UserUtil.setUUID(mContext, "");
                            UserUtil.setIsLogin(mContext, false);
                            AccountUtil.setIsMyFragmentRefresh(true);
                            AccountUtil.setIsTradeRefresh(true);
                            if (url.equals(NetConstantValue.getLogoutUrl())) {
                                UserUtil.setMobile(mContext, "0");
                            }
                            getAccessToken(mContext, !url.equals(NetConstantValue.getLogoutUrl()), callBack, result, url);
                        } else if (errorCode == 400) {
                            getAccessToken(mContext, false, callBack, result, url);
                        } else {
                            DealWithErrorUtils.dealWithErrorCode(NetBase.this.mContext, result);
                            callBack.onFailure(result, ErrorType.ERROR, GsonUtil.getErrorCode(result));
                        }
                    }
                } catch (Exception e) {
                    String g = (String) responseInfo.result;
                    callBack.onFailure(g, ErrorType.ERROR, GsonUtil.getErrorCode(g));
                    DealWithErrorUtils.dealWithErrorCode(NetBase.this.mContext, "");
                    MobclickAgent.reportError(NetBase.this.mContext, LogUtil.getException(e));
                    e.printStackTrace();
                }

                ViewUtil.cancelLoadingDialog();
            }

            public void onFailure(HttpException e, String m) {
                ToastUtil.showToast(NetBase.this.mContext, R.string.servicefaile);
                if (view != null) {
                    view.setEnabled(true);
                }
                LogUtil.d("ret", "failed: url = " + url + " ; m = " + m);
                callBack.onFailure("", ErrorType.FAILED, -1000);
                DealWithErrorUtils.dealWithErrorCode(NetBase.this.mContext, "");
                ViewUtil.cancelLoadingDialog();
            }
        });
    }

    protected BitmapUtils getBitmapUtils() {
        return this.mBitmapUtils;
    }

    private void getAccessToken(final Context context, final boolean isNeedLogin, final CallBack callBack, final String result, final String url) {
        if (url.equals(NetConstantValue.getLogoutUrl())) {
            callBack.onLogout();
        }
        if (isNeedLogin) {
            if (!AccountUtil.isLogoutDialogShowing()) {
                AccountUtil.setIsLogoutDialogShowing(true);
                new LogoutDialog(mContext, R.style.dialog, new LogoutDialog.LogoutDialogListener() {
                    @Override
                    public void onClick() {
                        callBack.onLogoutClick();
                    }
                }).show();
            }
            callBack.onLogout();
        }
        if (!AccountUtil.isGettingAccessToken()) {
            AccountUtil.setIsGettingAccessToken(true);
            new AccessToken(context).getAccessToken(new BaseNetCallBack<ResAccessTokenBean>() {
                @Override
                public void onSuccess(ResAccessTokenBean paramT) {
                    AccountUtil.setIsGettingAccessToken(false);
                    UserUtil.setUserInfo(context, paramT);
                    if (!isNeedLogin && !url.equals(NetConstantValue.getLogoutUrl())) {
                        callBack.onFailure(result, ErrorType.ERROR, GsonUtil.getErrorCode(result));
                    }
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {
                    AccountUtil.setIsGettingAccessToken(false);
                }
            });
        } else if (!isNeedLogin && !url.equals(NetConstantValue.getLogoutUrl())) {
            callBack.onFailure(result, ErrorType.ERROR, GsonUtil.getErrorCode(result));
        }
    }
}

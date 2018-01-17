package com.koudai.operate.net.api;

import android.content.Context;
import android.view.View;

import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.model.KLineListBean;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.CallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/23.
 */
public class GetKLineData extends NetBase {
    private boolean isRelease = true;
    private String mUrl;
    private Context mContext;
    private JSONObject mJSONObject;

    public GetKLineData(Context context) {
        super(context);
        mContext = context;
        mUrl = NetConstantValue.getKLineDataUrl();
    }

    public void getKLineData(JSONObject jsonObject, boolean showDialog, final BaseNetCallBack<KLineListBean> mKLineListCallBack) {
        try {
            mJSONObject = jsonObject;
            if (mJSONObject == null) {
                return;
            }
            getDataFromServerByPost(mUrl, mJSONObject, null, showDialog, new CallBack() {
                @Override
                public void onSuccess(String result, String url) {
                    successHandle(result, url, mKLineListCallBack);
                }

                @Override
                public void onFailure(String result, ErrorType errorType, int errorCode) {
                    failureHandle(result, errorType, errorCode, mKLineListCallBack);
                }
            });
        } catch (Exception e) {
            MobclickAgent.reportError(mContext, LogUtil.getException(e));
            e.printStackTrace();
        }
    }

    public void getKLineData(JSONObject jsonObject, View view, boolean isShowDialog, final BaseNetCallBack<KLineListBean> mKLineListCallBack) {
        try {
            mJSONObject = jsonObject;
            if (mJSONObject == null) {
                return;
            }
            getDataFromServerByPost(mUrl, mJSONObject, view, isShowDialog, new CallBack() {
                @Override
                public void onSuccess(String result, String url) {
                    successHandle(result, url, mKLineListCallBack);
                }

                @Override
                public void onFailure(String result, ErrorType errorType, int errorCode) {
                    failureHandle(result, errorType, errorCode, mKLineListCallBack);
                }
            });
        } catch (Exception e) {
            MobclickAgent.reportError(mContext, LogUtil.getException(e));
            e.printStackTrace();
        }
    }

    private void successHandle(String result, String url, BaseNetCallBack<KLineListBean> mKLineListCallBack) {
        KLineListBean mResponseBean;
        if (isRelease) {
            mResponseBean = (KLineListBean) GsonUtil.json2bean(result, KLineListBean.class);
        } else {
            mResponseBean = test();
        }
        mKLineListCallBack.onSuccess(mResponseBean);
    }

    private void failureHandle(String result, ErrorType errorType, int errorCode, BaseNetCallBack<KLineListBean> mKLineListCallBack) {
        if (isRelease) {
            mKLineListCallBack.onFailure(result, errorType, errorCode);
        } else {
            mKLineListCallBack.onSuccess(test());
        }
    }

    private KLineListBean test() {
        if (mJSONObject == null) {
            throw new RuntimeException("jsonObject is null");
        }
//        String merchant_id = "";
//        try {
//            merchant_id = mJSONObject.getString("merchant_id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (merchant_id == null || "".equals(merchant_id)) {
//            throw new RuntimeException("merchant_id is null");
//        }

        KLineListBean mResponseBean = new KLineListBean();
//        mResponseBean.setCode(0);
//        mResponseBean.setMsg("unbindBankCard success");

        return mResponseBean;
    }
}
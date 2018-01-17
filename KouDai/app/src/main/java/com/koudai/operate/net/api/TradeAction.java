package com.koudai.operate.net.api;

import android.content.Context;
import android.view.View;

import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.model.ResCreateProBean;
import com.koudai.operate.model.ResHistoryOrderListBean;
import com.koudai.operate.model.ResNoManualOrderBean;
import com.koudai.operate.model.ResUpAndDownBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.CallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/10.
 */
public class TradeAction extends NetBase {

    public TradeAction(Context context) {
        super(context);
    }

    public void createPro(JSONObject jsonObject, final BaseNetCallBack<ResCreateProBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getCreateProUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResCreateProBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getCreateProUrl(), errorType, errorCode);
            }
        });
    }

    public void manualLiqui(JSONObject jsonObject,final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getManualLiquiUrl(), jsonObject,true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResponseBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getManualLiquiUrl(), errorType, errorCode);
            }

            @Override
            public void onLogout() {
                callBack.onLogout();
            }
        });
    }

    public void getOrderList(JSONObject jsonObject, final BaseNetCallBack<ResHistoryOrderListBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getOrderListUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResHistoryOrderListBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getOrderListUrl(), errorType, errorCode);
            }

            @Override
            public void onLogoutClick() {
                callBack.onLogoutClick();
            }
        });
    }

    public void getNoManualOrderList(JSONObject jsonObject, final BaseNetCallBack<ResNoManualOrderBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getNoManualOrderUrl(), jsonObject, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResNoManualOrderBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getNoManualOrderUrl(), errorType, errorCode);
            }

            @Override
            public void onLogout() {
                callBack.onLogout();
            }
        });
    }

    public void setOrderPrice(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getSetOrderPriceUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResponseBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getSetOrderPriceUrl(), errorType, errorCode);
            }
        });
    }

    public void getUpAndDown(JSONObject jsonObject, final BaseNetCallBack<ResUpAndDownBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getUpAndDownUrl(), jsonObject, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResUpAndDownBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getUpAndDownUrl(), errorType, errorCode);
            }
        });
    }
}

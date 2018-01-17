package com.koudai.operate.net.api;

import android.content.Context;
import android.content.Intent;

import com.koudai.operate.constant.Globparams;
import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.model.ResGoodPriceBean;
import com.koudai.operate.model.ResProGroupListBean;
import com.koudai.operate.model.ResUserMoneyBean;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.CallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/11.
 */
public class GoodsAction extends NetBase {
    public GoodsAction(Context context) {
        super(context);
    }

    public void getGoodsPrice(JSONObject jsonObject, final BaseNetCallBack<ResGoodPriceBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getGoodsPriceUrl(), jsonObject, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResGoodPriceBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getGoodsPriceUrl(), errorType, errorCode);
            }
        });
    }

    public void getGoodsList(JSONObject jsonObject, final BaseNetCallBack<ResProGroupListBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getGoodsListUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResProGroupListBean.class));
                Intent intent = new Intent(Globparams.GET_GOODS_LIST_ACTION);
                intent.putExtra("json",result);
                mContext.sendBroadcast(intent);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getGoodsListUrl(), errorType, errorCode);
            }
        });
    }

    public void getUserMoneyList(JSONObject jsonObject, final BaseNetCallBack<ResUserMoneyBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getUserMoneyUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResUserMoneyBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getUserMoneyUrl(), errorType, errorCode);
            }
        });
    }


}

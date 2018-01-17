package com.koudai.operate.net.api;

import android.content.Context;

import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.model.ResAccountBean;
import com.koudai.operate.model.ResBankListBean;
import com.koudai.operate.model.ResCityListBean;
import com.koudai.operate.model.ResImageListBean;
import com.koudai.operate.model.ResLoginBean;
import com.koudai.operate.model.ResMoneyLogListBean;
import com.koudai.operate.model.ResRechargeBean;
import com.koudai.operate.model.ResUserTicketListBean;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.CallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/10.
 */
public class UserAction extends NetBase {

    public UserAction(Context context) {
        super(context);
    }

    /**
     * 首页进入统计
     * @param jsonObject
     * @param callBack
     */
    public void indexLogin(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getIndexLoginUrl(), jsonObject, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResponseBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getIndexLoginUrl(), errorType, errorCode);
            }
        });
    }


    /**
     * 注册验证码
     *
     * @param jsonObject
     * @param callBack
     */
    public void getCode(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getCodeUrl(), jsonObject, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResponseBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getCodeUrl(), errorType, errorCode);
            }
        });
    }

    /**
     * 注册
     *
     * @param jsonObject
     * @param callBack
     */
    public void register(JSONObject jsonObject, final BaseNetCallBack<ResLoginBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getRegisterUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                ResLoginBean bean = GsonUtil.json2bean(result, ResLoginBean.class);
                UserUtil.setIsLogin(mContext, true);
                UserUtil.setUserInfo(mContext, bean);
                AccountUtil.setIsMyFragmentRefresh(true);
                AccountUtil.setIsTradeRefresh(true);
                callBack.onSuccess(bean);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getRegisterUrl(), errorType, errorCode);
            }
        });
    }

    /**
     * 登录
     *
     * @param jsonObject
     * @param callBack
     */
    public void login(JSONObject jsonObject, final BaseNetCallBack<ResLoginBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getLoginUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                ResLoginBean bean = GsonUtil.json2bean(result, ResLoginBean.class);
                UserUtil.setIsLogin(mContext, true);
                UserUtil.setUserInfo(mContext, bean);
                AccountUtil.setIsMyFragmentRefresh(true);
                AccountUtil.setIsTradeRefresh(true);
                AccountUtil.setIsOrderFragmentReLoad(true);
                callBack.onSuccess(bean);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getLoginUrl(), errorType, errorCode);
            }
        });
    }

    public void logout(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getLogoutUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                ResponseBean bean = GsonUtil.json2bean(result, ResponseBean.class);
                UserUtil.setUid(mContext, "0");
                UserUtil.setUUID(mContext, "");
                UserUtil.setIsLogin(mContext, false);
                AccountUtil.setIsMyFragmentRefresh(true);
                AccountUtil.setIsTradeRefresh(true);
                callBack.onSuccess(bean);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getLogoutUrl(), errorType, errorCode);
            }

            @Override
            public void onLogout() {
                callBack.onLogout();
            }
        });
    }

    public void updateNickName(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getNickNameUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                ResponseBean bean = GsonUtil.json2bean(result, ResponseBean.class);
                callBack.onSuccess(bean);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getNickNameUrl(), errorType, errorCode);
            }
        });
    }

    public void updatePwd(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getUpdatePwdUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                ResponseBean bean = GsonUtil.json2bean(result, ResponseBean.class);
                UserUtil.setUid(mContext, "0");
                UserUtil.setUUID(mContext, "");
                UserUtil.setIsLogin(mContext, false);
                AccountUtil.setIsMyFragmentRefresh(true);
                AccountUtil.setIsTradeRefresh(true);
                callBack.onSuccess(bean);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getUpdatePwdUrl(), errorType, errorCode);
            }

            @Override
            public void onLogout() {
                callBack.onLogout();
            }
        });
    }

    public void resetPwd(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getResetPwdUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                ResponseBean bean = GsonUtil.json2bean(result, ResponseBean.class);
                callBack.onSuccess(bean);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getResetPwdUrl(), errorType, errorCode);
            }
        });
    }

    public void getResetPwdCode(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getResetPwdCodeUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                ResponseBean bean = GsonUtil.json2bean(result, ResponseBean.class);
                callBack.onSuccess(bean);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getResetPwdCodeUrl(), errorType, errorCode);
            }
        });
    }

    /**
     * 获取账户信息
     *
     * @param jsonObject
     * @param callBack
     */
    public void getAccountInfo(JSONObject jsonObject, final BaseNetCallBack<ResAccountBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getAccountUrl(), jsonObject, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResAccountBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getAccountUrl(), errorType, errorCode);
            }
        });
    }

    public void getUserTicket(JSONObject jsonObject, final BaseNetCallBack<ResUserTicketListBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getUserTicketUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResUserTicketListBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getUserTicketUrl(), errorType, errorCode);
            }
        });
    }

    public void getUserMoneyLog(JSONObject jsonObject, final BaseNetCallBack<ResMoneyLogListBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getUserMoneyLogUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResMoneyLogListBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getUserMoneyLogUrl(), errorType, errorCode);
            }
        });
    }

    /**
     * 获取充值页面Url
     *
     * @param jsonObject
     * @param callBack
     */
    public void getRechargeUrl(JSONObject jsonObject, final BaseNetCallBack<ResRechargeBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getUserRechargeUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResRechargeBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getUserRechargeUrl(), errorType, errorCode);
            }
        });
    }

    /**
     * 提现
     *
     * @param jsonObject
     * @param callBack
     */
    public void withdraw(JSONObject jsonObject, final BaseNetCallBack<ResponseBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getUserWithDrawUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResponseBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getUserWithDrawUrl(), errorType, errorCode);
            }
        });
    }

    public void getBankList(JSONObject jsonObject, final BaseNetCallBack<ResBankListBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getBankListUrl(), jsonObject, null, true, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResBankListBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getBankListUrl(), errorType, errorCode);
            }
        });
    }

    public void getImageList(JSONObject jsonObject, final BaseNetCallBack<ResImageListBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getImageListUrl(), jsonObject, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResImageListBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getImageListUrl(), errorType, errorCode);
            }
        });
    }

    public void getCityList(JSONObject jsonObject, final BaseNetCallBack<ResCityListBean> callBack) {
        getDataFromServerByPost(NetConstantValue.getCityListUrl(), jsonObject, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                callBack.onSuccess(GsonUtil.json2bean(result, ResCityListBean.class));
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getCityListUrl(), errorType, errorCode);
            }
        });
    }


}

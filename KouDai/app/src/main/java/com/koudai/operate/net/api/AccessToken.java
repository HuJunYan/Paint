package com.koudai.operate.net.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.koudai.operate.constant.NetConstantValue;
import com.koudai.operate.model.ResAccessTokenBean;
import com.koudai.operate.model.ResVersionBean;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.CallBack;
import com.koudai.operate.net.base.GsonUtil;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.AccountUtil;
import com.koudai.operate.utils.UserUtil;
import com.koudai.operate.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/8.
 */
public class AccessToken extends NetBase {
    private Context mContext;

    public AccessToken(Context context) {
        super(context);
        mContext = context;
    }

    public void getAccessToken(final BaseNetCallBack<ResAccessTokenBean> callBack) {
        JSONObject json = new JSONObject();
        try {
            String uuid = Utils.getUUID(mContext);
            json.put("client_id", NetConstantValue.CLIENTID);
            json.put("code", System.currentTimeMillis() + "");
            json.put("grant_type", "authorization_code");
            json.put("uuid", uuid);
            json.put("sign", getSign(uuid));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getDataFromServerByPost(NetConstantValue.getAccessUrl(), json, new CallBack() {
            @Override
            public void onSuccess(String result, String url) {
                ResAccessTokenBean bean = GsonUtil.json2bean(result, ResAccessTokenBean.class);
                callBack.onSuccess(bean);
                UserUtil.setIsEnable(mContext, true);
                AccountUtil.setIsNeedAccess(false);
            }

            @Override
            public void onFailure(String result, ErrorType errorType, int errorCode) {
                callBack.onFailure(NetConstantValue.getAccessUrl(), errorType, errorCode);
            }
        });
    }

    public void checkUpdateVersion(final BaseNetCallBack<ResVersionBean> callBack) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("version", getVersion());
            getDataFromServerByPost(NetConstantValue.getUpdateVersionUrl(), jsonObject, new CallBack() {
                @Override
                public void onSuccess(String result, String url) {
                    callBack.onSuccess(GsonUtil.json2bean(result, ResVersionBean.class));
                }

                @Override
                public void onFailure(String result, ErrorType errorType, int errorCode) {
                    callBack.onFailure(NetConstantValue.getUpdateVersionUrl(), errorType, errorCode);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getSign(String uuid) {
        String str = "client_id=" + NetConstantValue.CLIENTID + "&code=" + System.currentTimeMillis() + "&grant_type=authorization_code&uuid=" + uuid + NetConstantValue.SIGNKEY;
        return Utils.md5(str).toLowerCase();
    }

    private String getVersion() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }
}
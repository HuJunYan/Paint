package com.koudai.operate.net.base;

import android.content.Context;

import com.koudai.operate.R;
import com.koudai.operate.model.ResponseBean;
import com.koudai.operate.utils.ToastUtil;

public class DealWithErrorUtils {

    public static void dealWithErrorCode(Context context, String result) {
        ResponseBean mResponseBean = null;
        String errorMsg = "";
        try {
            mResponseBean = GsonUtil.json2bean(result, ResponseBean.class);
            errorMsg = mResponseBean.getResponse().getMessage();
        } catch (Exception e) {
            errorMsg = context.getResources().getString(R.string.servicefaile);
        }
        if (mResponseBean != null) {
            showErrorToast(context, mResponseBean.getRet(), errorMsg);
        }
    }

    private static void showErrorToast(Context context, int err_code, String err_msg) {
        switch (err_code) {
            case 118:
                break;
            default:
                ToastUtil.showToast(context, err_msg);
                break;
        }
    }
}

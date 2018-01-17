package com.koudai.operate.utils;

import android.content.ContentValues;
import android.content.Context;

import com.koudai.operate.model.ResAccessTokenBean;
import com.koudai.operate.model.ResAccountBean;
import com.koudai.operate.model.ResLoginBean;

/**
 * Created by Administrator on 2016/8/10.
 */
public class UserUtil {
    public static void setIsGuideReg(Context context, boolean isGuideReg) {
        SharedPreferencesUtil.getInstance(context).putBoolean("isGuideReg", isGuideReg);
    }

    public static void setIsGuideRegBig(Context context, boolean isGuideRegBig) {
        SharedPreferencesUtil.getInstance(context).putBoolean("isGuideRegBig", isGuideRegBig);
    }

    public static void setIsGuideTicket(Context context, boolean isGuideTicket) {
        SharedPreferencesUtil.getInstance(context).putBoolean("isGuideTicket", isGuideTicket);
    }

    public static void setIsOldUser(Context context, boolean isOldUser) {
        SharedPreferencesUtil.getInstance(context).putBoolean("isOldUser", isOldUser);
    }

    public static void setIsEnable(Context context, boolean enable) {
        SharedPreferencesUtil.getInstance(context).putBoolean("enable", enable);
    }

    public static void setIsLogin(Context context, boolean login) {
        SharedPreferencesUtil.getInstance(context).putBoolean("login", login);
    }

    public static void setAppid(Context context, int appid) {
        SharedPreferencesUtil.getInstance(context).putInt("appid", appid);
    }

    public static void setToken(Context context, String token) {
        SharedPreferencesUtil.getInstance(context).putString("token", token);
    }

    public static void setUid(Context context, String uid) {
        SharedPreferencesUtil.getInstance(context).putString("uid", uid);
    }

    public static void setUUID(Context context, String uuid) {
        SharedPreferencesUtil.getInstance(context).putString("uuid", uuid);
    }

    public static void setMobile(Context context, String mobile) {
        SharedPreferencesUtil.getInstance(context).putString("mobile", mobile);
    }

    public static void setNickName(Context context, String nickname) {
        SharedPreferencesUtil.getInstance(context).putString("nickname", nickname);
    }


    public static void setValidtime(Context context, String validtime) {
        SharedPreferencesUtil.getInstance(context).putString("validtime", validtime);
    }

    public static void setAvailableBalance(Context context, String available_balance) {
        SharedPreferencesUtil.getInstance(context).putString("available_balance", available_balance);
    }

    public static void setOrderCost(Context context, String cost) {
        SharedPreferencesUtil.getInstance(context).putString("cost", cost);
    }

    public static void setOrderProfit(Context context, String profit) {
        SharedPreferencesUtil.getInstance(context).putString("profit", profit);
    }

    public static void setTicekt10(Context context, int ticket) {
        SharedPreferencesUtil.getInstance(context).putInt("ticket10", ticket);
    }

    public static void setTicekt200(Context context, int ticket) {
        SharedPreferencesUtil.getInstance(context).putInt("ticket200", ticket);
    }

    public static void setTotalTicekt(Context context, int ticket) {
        SharedPreferencesUtil.getInstance(context).putInt("totalticket", ticket);
    }


    //Getters
    public static boolean getIsGuideReg(Context context) {
        return SharedPreferencesUtil.getInstance(context).getBoolean("isGuideReg");
    }

    public static boolean getIsGuideRegBig(Context context) {
        return SharedPreferencesUtil.getInstance(context).getBoolean("isGuideRegBig");
    }

    public static boolean getIsGuideTicket(Context context) {
        return SharedPreferencesUtil.getInstance(context).getBoolean("isGuideTicket");
    }

    public static boolean getIsOldUser(Context context) {
        return SharedPreferencesUtil.getInstance(context).getBoolean("isOldUser");
    }

    public static boolean getIsEnable(Context context) {
        return SharedPreferencesUtil.getInstance(context).getBoolean("enable");
    }

    public static boolean getIsLogin(Context context) {
        return SharedPreferencesUtil.getInstance(context).getBoolean("login");
    }

    public static int getAppid(Context context) {
        int appid = SharedPreferencesUtil.getInstance(context).getInt("appid");
        return appid == 0 ? 1 : appid;
    }

    public static String getToken(Context context) {
        return SharedPreferencesUtil.getInstance(context).getString("token");
    }

    public static String getUid(Context context) {
        String uid = SharedPreferencesUtil.getInstance(context).getString("uid");
        return uid.equals("") ? "0" : uid;
    }

    public static String getUUID(Context context) {
        return SharedPreferencesUtil.getInstance(context).getString("uuid");
    }

    public static String getMoblie(Context context) {
        String mobile = SharedPreferencesUtil.getInstance(context).getString("mobile");
        return mobile.equals("") ? "0" : mobile;
    }

    public static String getNickName(Context context) {
        String uid = SharedPreferencesUtil.getInstance(context).getString("nickname");
        return uid.equals("") ? "0" : uid;
    }

    public static String getValidtime(Context context) {
        return SharedPreferencesUtil.getInstance(context).getString("validtime");
    }

    public static String getAvailableBalance(Context context) {
        String balance = SharedPreferencesUtil.getInstance(context).getString("available_balance");
        return "".equals(balance) ? "0" : balance;
    }

    public static String getOrderCost(Context context) {
        String cost = SharedPreferencesUtil.getInstance(context).getString("cost");
        return "".equals(cost) ? "0" : cost;
    }

    public static String getOrderProfit(Context context) {
        String profit = SharedPreferencesUtil.getInstance(context).getString("profit");
        return "".equals(profit) ? "0" : profit;
    }

    public static int getTicket10(Context context) {
        return SharedPreferencesUtil.getInstance(context).getInt("ticket10");
    }

    public static int getTicket200(Context context) {
        return SharedPreferencesUtil.getInstance(context).getInt("ticket200");
    }

    public static int getTotalTicket(Context context) {
        return getTicket10(context) + getTicket200(context);
    }

    public static void setUserInfo(Context context, ResAccessTokenBean bean) {
        setToken(context, bean.getResponse().getToken());
        setUid(context, bean.getResponse().getUid());
        setValidtime(context, bean.getResponse().getValidtime() + "");
    }

    public static void setUserInfo(Context context, ResLoginBean bean) {
        setToken(context, bean.getResponse().getData().getToken());
        setUid(context, bean.getResponse().getData().getUser_id());
    }

    public static void setUserInfo(Context context, ResAccountBean bean) {
        setNickName(context, bean.getResponse().getData().getNickname());
        setMobile(context, bean.getResponse().getData().getMobile());
        setTicekt10(context, 0);
        setTicekt200(context, 0);
        for (ResAccountBean.Response.Data.Ticket ticket :
                bean.getResponse().getData().getTicket()) {
            if (ticket.getName().contains("10")) {
                setTicekt10(context, ticket.getCount());
            } else if (ticket.getName().contains("200")) {
                setTicekt200(context, ticket.getCount());
            }
        }
    }

}

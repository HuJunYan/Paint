package com.koudai.operate.utils;

/**
 * Created by Administrator on 2016/8/23.
 */
public class AccountUtil {
    private static boolean isMyFragmentRefresh = false;
    private static boolean isTradeFragmentRefresh = false;
    private static boolean isOrderFragmentReLoad  = false;
    private static boolean isLogoutDialogShowing = false;
    private static boolean isGettingAccessToken = false;
    private static boolean isNeedAccess = false;
    private static boolean isGetAccountInfo = false;
    private static boolean isNeedGuideTicket = false;

    public static boolean isMyFragmentRefresh() {
        return isMyFragmentRefresh;
    }

    public static void setIsMyFragmentRefresh(boolean isMyFragmentRefresh) {
        AccountUtil.isMyFragmentRefresh = isMyFragmentRefresh;
    }

    public static boolean isTradeRefresh() {
        return isTradeFragmentRefresh;
    }

    public static void setIsTradeRefresh(boolean isNoManualOrderRefresh) {
        AccountUtil.isTradeFragmentRefresh = isNoManualOrderRefresh;
    }

    public static boolean isLogoutDialogShowing() {
        return isLogoutDialogShowing;
    }

    public static void setIsLogoutDialogShowing(boolean isLogoutDialogShowing) {
        AccountUtil.isLogoutDialogShowing = isLogoutDialogShowing;
    }

    public static boolean isGettingAccessToken() {
        return isGettingAccessToken;
    }

    public static void setIsGettingAccessToken(boolean isGettingAccessToken) {
        AccountUtil.isGettingAccessToken = isGettingAccessToken;
    }

    public static boolean isNeedAccess() {
        return isNeedAccess;
    }

    public static void setIsNeedAccess(boolean isNeedAccess) {
        AccountUtil.isNeedAccess = isNeedAccess;
    }

    public static boolean isGetAccountInfo() {
        return isGetAccountInfo;
    }

    public static void setIsGetAccountInfo(boolean isGetAccountInfo) {
        AccountUtil.isGetAccountInfo = isGetAccountInfo;
    }

    public static boolean isNeedGuideTicket() {
        return isNeedGuideTicket;
    }

    public static void setIsNeedGuideTicket(boolean isNeedGuideTicket) {
        AccountUtil.isNeedGuideTicket = isNeedGuideTicket;
    }

    public static boolean isOrderFragmentReLoad() {
        return isOrderFragmentReLoad;
    }

    public static void setIsOrderFragmentReLoad(boolean isOrderFragmentReLoad) {
        AccountUtil.isOrderFragmentReLoad = isOrderFragmentReLoad;
    }
}

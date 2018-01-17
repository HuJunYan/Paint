package com.koudai.operate.constant;

/**
 * Created by zhangchi on 2016/6/14.
 */
public class NetConstantValue {
//    public static String COMMONURI = "http://dev.api.heshuibar.com/";
//    public static String COMMONURI = "http://pro.api.heshuibar.com/";


    public static String COMMONURI = "http://api.kdcaopan.com/";

    //
//    public static String CLIENTID = "1";
//    public static String SIGNKEY = "aaabbbccc";    // 测试服

//    public static String CLIENTID = "4";
//    public static String SIGNKEY = "0bcd9280553059e22739ff9286457809";    // Vivo


//    public static String CLIENTID = "5";
//    public static String SIGNKEY = "904ba5e84e414de70257767500a3d4ef";    // 金立


//    public static String CLIENTID = "6";
//    public static String SIGNKEY = "92fd7024274ef7647eae91e313ff0830";    // 联想乐商店

//
//    public static String CLIENTID = "7";
//    public static String SIGNKEY = "c1e8e7e7170ee9d9d7cc4e06af7f79d1";    //Oppo


//    public static String CLIENTID = "8";
//    public static String SIGNKEY = "d981d882189da914640b5b328fdd7bc5";//安智


//    public static String CLIENTID = "9";
//    public static String SIGNKEY = "2d673912328262fa81968b83c5badcb0";// 应用宝


//    public static String CLIENTID = "10";
//    public static String SIGNKEY = "2be446577762bb6cec8681f5e2d67d18";//小米


//    public static String CLIENTID = "11";
//    public static String SIGNKEY = "39ac01185e5f189fa45e138cfd5c0e4b";//豌豆荚


//    public static String CLIENTID = "12";
//    public static String SIGNKEY = "d63e7ea059dc68e97e2ef16531e47e6c";//华为

//    public static String CLIENTID = "13";
//    public static String SIGNKEY = "e7b24b112a44fdd9ee93bdf998c6ca0e";//360


//    public static String CLIENTID = "14";
//    public static String SIGNKEY = "a3f4a5b080e2a4ef4a708b9c9f5ad003";//百度


//    public static String CLIENTID = "15";
//    public static String SIGNKEY = "44a38beae0a806b3165119e6a9e875dc";//微信公众号

//    public static String CLIENTID = "17";
//    public static String SIGNKEY = "4f96b12810aee1fb2de95c200d1efb57";//口袋短信

//    public static String CLIENTID = "18";
//    public static String SIGNKEY = "bcf22988ed76e363358da7694b10e012";//外部运营黄冲

//    public static String CLIENTID = "19";
//    public static String SIGNKEY = "726c4ebdae2e1c3594985e427501552c";//服务器

//    public static String CLIENTID = "20";
//    public static String SIGNKEY = "894481ede625aea816be9de7149181eb";//外部渠道_张紫杨

//    public static String CLIENTID = "23";
//    public static String SIGNKEY = "b6545d884b50d077dd2f4ce51778fe1d";//深圳南平通

//    public static String CLIENTID = "24";
//    public static String SIGNKEY = "43dfa7dc680daf6f82cc3df570779b57";//kuaiya

//    public static String CLIENTID = "27";
//    public static String SIGNKEY = "48a65b8e07bfc275afd20960c995b375";//永丰渠道罗总

    public static String CLIENTID = "28";
    public static String SIGNKEY = "b70b66b8371f817e7cf11363e6a33bee";//旺彩


//    public static String CLIENTID = "16";
//    public static String SIGNKEY = "a55f8e41f1b4850e5cc00c36464acd5c";//彩票站1

    /**
     * 获取令牌接口
     * api: AccessToken
     *
     * @return
     */
    public static String getAccessUrl() {
        return COMMONURI + "oauth/access_token";
    }

    public static String getUpdateVersionUrl() {
        return COMMONURI + "oauth/version";
    }

    public static String getIndexLoginUrl() {
        return COMMONURI + "goods/index_login";
    }


    //用户相关
    public static String getCodeUrl() {
        return COMMONURI + "user/existing";
    }

    public static String getRegisterUrl() {
        return COMMONURI + "user/user_reg";
    }

    public static String getLoginUrl() {
        return COMMONURI + "user/login";
    }

    public static String getLogoutUrl() {
        return COMMONURI + "user/logout";
    }

    public static String getNickNameUrl() {
        return COMMONURI + "user/edit_user_nickname";
    }

    public static String getUpdatePwdUrl() {
        return COMMONURI + "user/edit_password";
    }

    public static String getResetPwdUrl() {
        return COMMONURI + "user/reset_password";
    }

    public static String getResetPwdCodeUrl() {
        return COMMONURI + "user/reset_password_code";
    }

    public static String getAccountUrl() {
        return COMMONURI + "user/get_account";
    }

    public static String getTicketUrl() {
        return COMMONURI + "user/user_ticket";
    }

    public static String getUserRechargeUrl() {
        return COMMONURI + "user/user_saving";
    }

    public static String getUserWithDrawUrl() {
        return COMMONURI + "user/user_withdraw";
    }

    public static String getBankListUrl() {
        return COMMONURI + "user/bank_list";
    }

    public static String getUserTicketUrl() {
        return COMMONURI + "user/user_ticket";
    }

    public static String getUserMoneyLogUrl() {
        return COMMONURI + "user/money_log";
    }

    public static String getImageListUrl() {
        return COMMONURI + "index/static_html";
    }

    public static String getCityListUrl() {
        return COMMONURI + "goods/city_list";
    }

    //产品相关
    public static String getGoodsPriceUrl() {
        return COMMONURI + "goods/goods_now";
    }

    public static String getGoodsListUrl() {
        return COMMONURI + "goods/goods_all_now";
    }

    //交易相关
    public static String getCreateProUrl() {
        return COMMONURI + "order/create_pro";
    }

    public static String getManualLiquiUrl() {
        return COMMONURI + "order/manual_liqui";
    }

    public static String getOrderListUrl() {
        return COMMONURI + "order/order_list";
    }

    public static String getNoManualOrderUrl() {
        return COMMONURI + "order/user_no_manual_order";
    }

    public static String getSetOrderPriceUrl() {
        return COMMONURI + "order/set_order_price";
    }

    public static String getKLineDataUrl() {
        return COMMONURI + "goods/k_map";
    }

    public static String getUpAndDownUrl() {
        return COMMONURI + "goods/goods_trend";
    }

    public static String getUserMoneyUrl() {
        return COMMONURI + "goods/user_money";
    }

    //静态页面
    public static String getFindUrl() {
//        return "http://h5.kdcaopan.com/news.html";
        return COMMONURI + "index/news_list2";
    }

    public static String getAboutUrl() {
        return COMMONURI + "index/static_html1?type=guanyuwomen";
    }

    public static String getFAQUrl() {
        return COMMONURI + "index/static_html1?type=static_faq";
    }
}
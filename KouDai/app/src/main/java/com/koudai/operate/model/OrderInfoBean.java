package com.koudai.operate.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22.
 */
public class OrderInfoBean implements Serializable {
    private String app_id;
    private String user_id;
    private String order_id;
    private String goods_id;
    private double k_amount;
    private double build_price;
    private double new_price;
    private double rise;
    private String liqui_price;
    private double trade_fee;
    private int trade_type;
    private double trade_deposit;
    private String broken_price;
    private int use_ticket;
    private String target_profit;
    private String stop_loss;
    private String pro_type;
    private String build_time;
    private String pro_name;
    private String pro_code;
    private int pro_amount;
    private String pro_unit;
    private int amount;
    private String guaranteed;
    private double profitAndLoss;
    private boolean isNeedRefresh = true;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public double getK_amount() {
        return k_amount;
    }

    public void setK_amount(double k_amount) {
        this.k_amount = k_amount;
    }

    public double getBuild_price() {
        return build_price;
    }

    public void setBuild_price(double build_price) {
        this.build_price = build_price;
    }

    public double getNew_price() {
        return new_price;
    }

    public double getRise() {
        return rise;
    }

    public void setRise(double rise) {
        this.rise = rise;
    }

    public void setNew_price(double new_price) {
        this.new_price = new_price;
    }

    public String getLiqui_price() {
        return liqui_price;
    }

    public void setLiqui_price(String liqui_price) {
        this.liqui_price = liqui_price;
    }


    public int getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(int trade_type) {
        this.trade_type = trade_type;
    }

    public String getBroken_price() {
        return broken_price;
    }

    public void setBroken_price(String broken_price) {
        this.broken_price = broken_price;
    }


    public String getTarget_profit() {
        return target_profit;
    }

    public void setTarget_profit(String target_profit) {
        this.target_profit = target_profit;
    }

    public String getStop_loss() {
        return stop_loss;
    }

    public void setStop_loss(String stop_loss) {
        this.stop_loss = stop_loss;
    }

    public String getPro_type() {
        return pro_type;
    }

    public void setPro_type(String pro_type) {
        this.pro_type = pro_type;
    }

    public String getBuild_time() {
        return build_time;
    }

    public void setBuild_time(String build_time) {
        this.build_time = build_time;
    }

    public String getPro_name() {
        return pro_name;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public int getPro_amount() {
        return pro_amount;
    }

    public void setPro_amount(int pro_amount) {
        this.pro_amount = pro_amount;
    }

    public String getPro_unit() {
        return pro_unit;
    }

    public void setPro_unit(String pro_unit) {
        this.pro_unit = pro_unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUse_ticket() {
        return use_ticket;
    }

    public void setUse_ticket(int use_ticket) {
        this.use_ticket = use_ticket;
    }

    public String getGuaranteed() {
        return guaranteed;
    }

    public void setGuaranteed(String guaranteed) {
        this.guaranteed = guaranteed;
    }

    public double getTrade_fee() {
        return trade_fee;
    }

    public void setTrade_fee(double trade_fee) {
        this.trade_fee = trade_fee;
    }

    public double getTrade_deposit() {
        return trade_deposit;
    }

    public void setTrade_deposit(double trade_deposit) {
        this.trade_deposit = trade_deposit;
    }

    public double getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(double profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        isNeedRefresh = needRefresh;
    }
}

package com.koudai.operate.model;

import java.io.Serializable;

/**
 * Created by admin on 2016/8/21.
 */
public class ProInfoItemBean implements Serializable {
    private String id;
    private String app_id;
    private String goods_id;
    private String pro_code;
    private String pro_name;
    private int amount;
    private String unit;
    private double k_amount;
    private double trade_fee;
    private double unit_price;
    private String float_profit;
    private int max_buy;
    private String remark;

    public ProInfoItemBean() {
    }

    public ProInfoItemBean(String id, String app_id, String goods_id, String pro_code, String pro_name, int amount,
                           String unit, double trade_free, double unit_price, String float_profit, int max_buy, String remark) {
        this.id = id;
        this.app_id = app_id;
        this.goods_id = goods_id;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.amount = amount;
        this.unit = unit;
        this.trade_fee = trade_free;
        this.unit_price = unit_price;
        this.float_profit = float_profit;
        this.max_buy = max_buy;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getTrade_free() {
        return trade_fee;
    }

    public void setTrade_free(double trade_free) {
        this.trade_fee = trade_free;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public String getFloat_profit() {
        return float_profit;
    }

    public void setFloat_profit(String float_profit) {
        this.float_profit = float_profit;
    }

    public int getMax_buy() {
        return max_buy;
    }

    public void setMax_buy(int max_buy) {
        this.max_buy = max_buy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getK_amount() {
        return k_amount;
    }

    public void setK_amount(double k_amount) {
        this.k_amount = k_amount;
    }
}

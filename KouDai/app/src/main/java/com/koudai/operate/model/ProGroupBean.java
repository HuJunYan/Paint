package com.koudai.operate.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/8/21.
 */
public class ProGroupBean implements Serializable {
    private String app_id;
    private String pro_code;
    private String pro_name;
    private double latest_price;
    private double price_beginning;
    private double price_end_lastday;
    private double high_price;
    private double lowwest_price;
    private String update_time;
    private double rise;
    private int during_type;
    private List<ProInfoItemBean> goods_list;
    private boolean isUp;
    private boolean isShowUpOrDown=false;

    public ProGroupBean() {
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
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

    public double getLatest_price() {
        return latest_price;
    }

    public void setLatest_price(double latest_price) {
        this.latest_price = latest_price;
    }

    public double getPrice_beginning() {
        return price_beginning;
    }

    public void setPrice_beginning(double price_beginning) {
        this.price_beginning = price_beginning;
    }

    public double getPrice_end_lastday() {
        return price_end_lastday;
    }

    public void setPrice_end_lastday(double price_end_lastday) {
        this.price_end_lastday = price_end_lastday;
    }

    public double getHigh_price() {
        return high_price;
    }

    public void setHigh_price(double high_price) {
        this.high_price = high_price;
    }

    public double getLowwest_price() {
        return lowwest_price;
    }

    public void setLowwest_price(double lowwest_price) {
        this.lowwest_price = lowwest_price;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<ProInfoItemBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<ProInfoItemBean> goods_list) {
        this.goods_list = goods_list;
    }

    public double getRise() {
        return rise;
    }

    public void setRise(double rise) {
        this.rise = rise;
    }

    public int getDuring_type() {
        return during_type;
    }

    public void setDuring_type(int during_type) {
        this.during_type = during_type;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public boolean isShowUpOrDown() {
        return isShowUpOrDown;
    }

    public void setShowUpOrDown(boolean showUpOrDown) {
        isShowUpOrDown = showUpOrDown;
    }
}

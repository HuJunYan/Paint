package com.koudai.operate.model;

/**
 * Created by Administrator on 2016/8/29.
 */
public class LatestProPriceBean {
    private double Latest_price;
    private double Price_beginning;
    private double Price_end_lastday;
    private double High_price;
    private double Lowwest_price;
    private double Rise;
    private int During_type;
    private String Update_time;
    private String Proname;
    private String Pro_code;

    public double getLatest_price() {
        return Latest_price;
    }

    public void setLatest_price(double latest_price) {
        Latest_price = latest_price;
    }

    public double getPrice_beginning() {
        return Price_beginning;
    }

    public void setPrice_beginning(double price_beginning) {
        Price_beginning = price_beginning;
    }

    public double getPrice_end_lastday() {
        return Price_end_lastday;
    }

    public void setPrice_end_lastday(double price_end_lastday) {
        Price_end_lastday = price_end_lastday;
    }

    public double getHigh_price() {
        return High_price;
    }

    public void setHigh_price(double high_price) {
        High_price = high_price;
    }

    public double getLowwest_price() {
        return Lowwest_price;
    }

    public void setLowwest_price(double lowwest_price) {
        Lowwest_price = lowwest_price;
    }

    public double getRise() {
        return Rise;
    }

    public void setRise(double rise) {
        Rise = rise;
    }

    public int getDuring_type() {
        return During_type;
    }

    public void setDuring_type(int during_type) {
        During_type = during_type;
    }

    public String getUpdate_time() {
        return Update_time;
    }

    public void setUpdate_time(String update_time) {
        Update_time = update_time;
    }

    public String getProname() {
        return Proname;
    }

    public void setProname(String proname) {
        Proname = proname;
    }

    public String getPro_code() {
        return Pro_code;
    }

    public void setPro_code(String pro_code) {
        Pro_code = pro_code;
    }
}

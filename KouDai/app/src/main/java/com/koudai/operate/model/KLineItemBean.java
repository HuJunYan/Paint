package com.koudai.operate.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/23.
 */
public class KLineItemBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String time;
    private String open = "0";
    private String high = "0";
    private String low = "0";
    private String close = "0";
    private String Volume = "0";
    private String Adj = "0";
    private String now = "0";
    private String ma10 = "0";
    private String ma20 = "0";
    private String avge = "0";

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public String getAvge() {
        return avge;
    }

    public void setAvge(String avge) {
        this.avge = avge;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getAdj() {
        return Adj;
    }

    public void setAdj(String adj) {
        Adj = adj;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getMa10() {
        return ma10;
    }

    public void setMa10(String ma10) {
        this.ma10 = ma10;
    }

    public String getMa20() {
        return ma20;
    }

    public void setMa20(String ma20) {
        this.ma20 = ma20;
    }
}

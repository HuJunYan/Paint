package com.koudai.operate.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/17.
 */
public class StockListBean {
    /**
     * error : 0
     * message :
     * content :
     */
    private int error;
    private String message;
    private List<StockBean> content;

    public void setError(int error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContent(List<StockBean> content) {
        this.content = content;
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<StockBean> getContent() {
        return content;
    }
}


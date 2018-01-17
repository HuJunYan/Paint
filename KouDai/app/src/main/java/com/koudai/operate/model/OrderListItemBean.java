package com.koudai.operate.model;

/**
 * Created by Administrator on 2016/8/12.
 */
public class OrderListItemBean {
    private String order_id;
    private String order_no;
    private String pro_name;
    private double build_price;
    private double liqui_price;
    private int amount;
    private int trade_type;
    private String liqui_time;
    private String build_time;
    private String pro_loss;
    private String actual_pro_loss;
    private String actual_pro_loss_percent;
    private int liqui_type;
    private double trade_fee;
    private int use_ticket;
    private String trade_deposit;
    private String liqui_income;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public double getBuild_price() {
        return build_price;
    }

    public void setBuild_price(double build_price) {
        this.build_price = build_price;
    }

    public double getLiqui_price() {
        return liqui_price;
    }

    public void setLiqui_price(double liqui_price) {
        this.liqui_price = liqui_price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(int trade_type) {
        this.trade_type = trade_type;
    }

    public String getLiqui_time() {
        return liqui_time;
    }

    public void setLiqui_time(String liqui_time) {
        this.liqui_time = liqui_time;
    }

    public String getBuild_time() {
        return build_time;
    }

    public void setBuild_time(String build_time) {
        this.build_time = build_time;
    }

    public String getPro_loss() {
        return pro_loss;
    }

    public void setPro_loss(String pro_loss) {
        this.pro_loss = pro_loss;
    }

    public String getActual_pro_loss() {
        return actual_pro_loss;
    }

    public void setActual_pro_loss(String actual_pro_loss) {
        this.actual_pro_loss = actual_pro_loss;
    }

    public String getActual_pro_loss_percent() {
        return actual_pro_loss_percent;
    }

    public void setActual_pro_loss_percent(String actual_pro_loss_percent) {
        this.actual_pro_loss_percent = actual_pro_loss_percent;
    }

    public int getLiqui_type() {
        return liqui_type;
    }

    public void setLiqui_type(int liqui_type) {
        this.liqui_type = liqui_type;
    }

    public double getTrade_fee() {
        return trade_fee;
    }

    public void setTrade_fee(double trade_fee) {
        this.trade_fee = trade_fee;
    }

    public int getUse_ticket() {
        return use_ticket;
    }

    public void setUse_ticket(int use_ticket) {
        this.use_ticket = use_ticket;
    }

    public String getTrade_deposit() {
        return trade_deposit;
    }

    public void setTrade_deposit(String trade_deposit) {
        this.trade_deposit = trade_deposit;
    }

    public String getLiqui_income() {
        return liqui_income;
    }

    public void setLiqui_income(String liqui_income) {
        this.liqui_income = liqui_income;
    }
}

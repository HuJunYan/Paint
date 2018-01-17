package com.koudai.operate.model;

/**
 * Created by Administrator on 2016/8/5.
 */
public class TradeDetailItemBean {
    private String date;                //交易日期
    private String product;             //产品类型(黄金、白银...）
    private double weight;              //买入重量
    private String buyType;             //买涨、买跌
    private double result;              //盈亏结果
    private double buyUnitPrice;        //建仓单价
    private double sellUnitPrice;       //平仓单价
    private double buyTotalPrice;       //建仓总价
    private double poundage;            //手续费
    private String moneyType;           //购买方式(现金、银圆券)
    private String butTime;             //建仓时间
    private String sellTime;            //平仓时间
    private String sellType;            //平仓类型（手动、爆仓)

    public TradeDetailItemBean() {
    }

    public TradeDetailItemBean(String date, String product, double weight, String buyType, double result, double buyUnitPrice,
                               double sellUnitPrice, double buyTotalPrice, double poundage, String moneyType, String butTime, String sellTime, String sellType) {
        this.date = date;
        this.product = product;
        this.weight = weight;
        this.buyType = buyType;
        this.result = result;
        this.buyUnitPrice = buyUnitPrice;
        this.sellUnitPrice = sellUnitPrice;
        this.buyTotalPrice = buyTotalPrice;
        this.poundage = poundage;
        this.moneyType = moneyType;
        this.butTime = butTime;
        this.sellTime = sellTime;
        this.sellType = sellType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getBuyUnitPrice() {
        return buyUnitPrice;
    }

    public void setBuyUnitPrice(double buyUnitPrice) {
        this.buyUnitPrice = buyUnitPrice;
    }

    public double getSellUnitPrice() {
        return sellUnitPrice;
    }

    public void setSellUnitPrice(double sellUnitPrice) {
        this.sellUnitPrice = sellUnitPrice;
    }

    public double getBuyTotalPrice() {
        return buyTotalPrice;
    }

    public void setBuyTotalPrice(double buyTotalPrice) {
        this.buyTotalPrice = buyTotalPrice;
    }

    public double getPoundage() {
        return poundage;
    }

    public void setPoundage(double poundage) {
        this.poundage = poundage;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getButTime() {
        return butTime;
    }

    public void setButTime(String butTime) {
        this.butTime = butTime;
    }

    public String getSellTime() {
        return sellTime;
    }

    public void setSellTime(String sellTime) {
        this.sellTime = sellTime;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }
}

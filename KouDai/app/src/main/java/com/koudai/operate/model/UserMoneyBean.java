package com.koudai.operate.model;

/**
 * Created by admin on 2016/10/12.
 */
public class UserMoneyBean {
    private String pro_loss;
    private String mobile;

    public String getPro_loss() {
        return pro_loss;
    }

    public void setPro_loss(String pro_loss) {
        this.pro_loss = pro_loss;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        String str = "最新盈利: 尾号为" + mobile.substring(mobile.length() - 4) + "的用户盈利" + pro_loss + "元";
        return str;
    }
}

package com.koudai.operate.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class ResNoManualOrderBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            private List<OrderInfoBean> list;
            private PriceInfo user_data;
            public List<OrderInfoBean> getList() {
                return list;
            }

            public void setList(List<OrderInfoBean> list) {
                this.list = list;
            }

            public PriceInfo getUser_data() {
                return user_data;
            }

            public void setUser_data(PriceInfo user_data) {
                this.user_data = user_data;
            }

            public class PriceInfo{
                private double available_balance;

                public double getAvailable_balance() {
                    return available_balance;
                }

                public void setAvailable_balance(double available_balance) {
                    this.available_balance = available_balance;
                }
            }
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}

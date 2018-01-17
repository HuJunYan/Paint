package com.koudai.operate.model;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ResCreateProBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private OrderInfoBean data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public OrderInfoBean getData() {
            return data;
        }

        public void setData(OrderInfoBean data) {
            this.data = data;
        }
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}

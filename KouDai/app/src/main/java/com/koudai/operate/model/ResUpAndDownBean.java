package com.koudai.operate.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ResUpAndDownBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            private int type1;

            public int getType1() {
                return type1;
            }

            public void setType1(int type1) {
                this.type1 = type1;
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

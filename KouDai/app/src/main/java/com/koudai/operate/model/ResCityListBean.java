package com.koudai.operate.model;

import java.util.List;

/**
 * Created by admin on 2016/9/14.
 */
public class ResCityListBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            List<ProvinceBean> list;

            public List<ProvinceBean> getList() {
                return list;
            }

            public void setList(List<ProvinceBean> list) {
                this.list = list;
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

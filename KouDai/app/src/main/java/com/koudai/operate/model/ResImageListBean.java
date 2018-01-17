package com.koudai.operate.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/12.
 */
public class ResImageListBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            private ArrayList<ADInfo> list;

            public ArrayList<ADInfo> getList() {
                return list;
            }

            public void setList(ArrayList<ADInfo> list) {
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

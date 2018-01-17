package com.koudai.operate.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class ResUserTicketListBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            private List<TicketInfoBean> list;

            public List<TicketInfoBean> getList() {
                return list;
            }

            public void setList(List<TicketInfoBean> list) {
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

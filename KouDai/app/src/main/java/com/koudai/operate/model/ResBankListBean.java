package com.koudai.operate.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ResBankListBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            List<Bank> list;

            public class Bank {
                private String bank_name;

                public String getBank_name() {
                    return bank_name;
                }

                public void setBank_name(String bank_name) {
                    this.bank_name = bank_name;
                }

                @Override
                public String toString() {
                    return bank_name;
                }
            }

            public List<Bank> getList() {
                return list;
            }

            public void setList(List<Bank> list) {
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

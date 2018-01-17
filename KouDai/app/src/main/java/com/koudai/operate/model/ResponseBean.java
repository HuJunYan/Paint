package com.koudai.operate.model;

import java.io.Serializable;

public class ResponseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 0; //成功
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            private int status_code;
            private String token;

            public int getStatus_code() {
                return status_code;
            }

            public void setStatus_code(int status_code) {
                this.status_code = status_code;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
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

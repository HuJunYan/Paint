package com.koudai.operate.model;

/**
 * Created by Administrator on 2016/8/8.
 */
public class ResAccessTokenBean {
    private int ret;
    public Response response = new Response();
    private int benchmark;

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

    public int getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(int benchmark) {
        this.benchmark = benchmark;
    }

    public class Response {
        private String token;
        private String uid;
        private long validtime;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public long getValidtime() {
            return validtime;
        }

        public void setValidtime(long validtime) {
            this.validtime = validtime;
        }
    }
}

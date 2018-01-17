package com.koudai.operate.model;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ResGoodPriceBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            private String pro_code;
            private String latest_price;
            private String price_beginning;
            private String price_end_lastday;
            private String high_price;
            private String lowwest_price;
            private String update_time;

            public String getPro_code() {
                return pro_code;
            }

            public void setPro_code(String pro_code) {
                this.pro_code = pro_code;
            }

            public String getLatest_price() {
                return latest_price;
            }

            public void setLatest_price(String latest_price) {
                this.latest_price = latest_price;
            }

            public String getPrice_beginning() {
                return price_beginning;
            }

            public void setPrice_beginning(String price_beginning) {
                this.price_beginning = price_beginning;
            }

            public String getPrice_end_lastday() {
                return price_end_lastday;
            }

            public void setPrice_end_lastday(String price_end_lastday) {
                this.price_end_lastday = price_end_lastday;
            }

            public String getHigh_price() {
                return high_price;
            }

            public void setHigh_price(String high_price) {
                this.high_price = high_price;
            }

            public String getLowwest_price() {
                return lowwest_price;
            }

            public void setLowwest_price(String lowwest_price) {
                this.lowwest_price = lowwest_price;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
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
